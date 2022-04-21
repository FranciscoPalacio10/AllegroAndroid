package com.example.allegrod.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.allegrod.R;
import com.example.allegrod.models.rol.Rol;
import com.example.allegrod.models.user.User;
import com.example.allegrod.models.user.get.UserGetResponse;
import com.example.allegrod.repository.UserRepository;
import com.example.allegrod.services.DateService;
import com.example.allegrod.services.FireBaseLoginService;
import com.example.allegrod.services.StringService;
import com.example.allegrod.ui.start.Autenticacion;
import com.example.allegrod.ui.start.LoadUser;
import com.example.allegrod.ui.utils.TimePickerFragment;
import com.example.allegrod.viewmodel.TokenViewModel;
import com.example.allegrod.viewmodel.UserViewModel;
import com.google.firebase.auth.FirebaseUser;

import java.text.ParseException;

public class CompleteProfileActivity extends AppCompatActivity {
    private FireBaseLoginService fireBaseLoginService;
    private EditText dateOfBirth,phone,lastName,firstName;
    private DateService obtenerFecha;
    private FirebaseUser userF;
    private UserViewModel userViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_profile);
        init();

    }

    private void init() {
        fireBaseLoginService = new FireBaseLoginService(getString(R.string.default_web_client_id), this);
        phone = findViewById(R.id.editTextPhone);
        dateOfBirth = findViewById(R.id.editTextDate);
        lastName = findViewById(R.id.editTextTextPersonLastName);
        firstName = findViewById(R.id.editTextTextPersonName);
        obtenerFecha = DateService.getInstance();
        userF = fireBaseLoginService.getCurrentUser();
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
    }


    public void createUser(View v) throws ParseException {
       User user = new User(userF.getEmail(), StringService.upperFirstLetter(firstName.getText().toString()),
                StringService.upperFirstLetter(lastName.getText().toString()),
                obtenerFecha.convertStringToDate(dateOfBirth.getText().toString()), Rol.ROL_ALUMNO);

        userViewModel.createUser(user).observe(this, new Observer<UserGetResponse>() {
            @Override
            public void onChanged(UserGetResponse userGetResponse) {
                if(userGetResponse != null){
                    Intent intent = new Intent(CompleteProfileActivity.this, LoadUser.class);
                    startActivity(intent);
                }
            }
        });

    }


    public void showTimePickerDialog(View v) {
        TimePickerFragment newFragment = TimePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                dateOfBirth.setText(obtenerFecha.parseDate(year,month,day));
            }
        });
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

}
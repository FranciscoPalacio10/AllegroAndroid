package com.example.allegrobackend.ui.start;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.allegrobackend.R;
import com.example.allegrobackend.models.rol.Rol;
import com.example.allegrobackend.models.user.UserRequest;
import com.example.allegrobackend.models.user.get.UserResponse;
import com.example.allegrobackend.services.DateService;
import com.example.allegrobackend.services.FireBaseLoginService;
import com.example.allegrobackend.services.StringService;
import com.example.allegrobackend.ui.utils.TimePickerFragment;
import com.example.allegrobackend.ui.validaciones.ValidatorUiService;
import com.example.allegrobackend.viewmodel.UserViewModel;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class CompleteProfileActivity extends AppCompatActivity {
    private FireBaseLoginService fireBaseLoginService;
    private EditText dateOfBirth,phone,lastName,firstName;
    private DateService obtenerFecha;
    private FirebaseUser userF;
    private UserViewModel userViewModel;
    private Toast toast;
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


    public void createUser(View v){
        try{
            validateInputs();
            UserRequest user = new UserRequest(userF.getEmail(), StringService.upperFirstLetter(firstName.getText().toString()),
                    StringService.upperFirstLetter(lastName.getText().toString()),
                    obtenerFecha.convertStringToDateString(dateOfBirth.getText().toString()), Rol.ROL_ALUMNO);
            userViewModel.createUser(user).observe(this, new Observer<UserResponse>() {
                @Override
                public void onChanged(UserResponse userGetResponse) {
                    if(userGetResponse != null){
                        Intent intent = new Intent(CompleteProfileActivity.this, LoadUserActivity.class);
                        startActivity(intent);
                    }
                }
            });
        }catch (Exception e){
            toast = Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void validateInputs() throws Exception {
        ArrayList<EditText> editTexts = new ArrayList<>();
        editTexts.add(phone);
        editTexts.add(lastName);
        editTexts.add(firstName);
        editTexts.add(dateOfBirth);
        ValidatorUiService.validateEditText(editTexts);

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
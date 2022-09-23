package com.example.allegroandroid.ui.start;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.allegroandroid.utils.AppExecutors;
import com.example.allegroandroid.utils.AppModule;
import com.example.allegroandroid.R;
import com.example.allegroandroid.constants.AppConstant;
import com.example.allegroandroid.models.InitActitvy;
import com.example.allegroandroid.models.rol.Rol;
import com.example.allegroandroid.models.user.UserRequest;
import com.example.allegroandroid.models.user.create.UserCreateResponse;
import com.example.allegroandroid.repository.resource.Resource;
import com.example.allegroandroid.repository.resource.Status;
import com.example.allegroandroid.repository.UserRepository;
import com.example.allegroandroid.services.ActivitiesInitiator;
import com.example.allegroandroid.services.DateService;
import com.example.allegroandroid.services.FireBaseLoginService;
import com.example.allegroandroid.services.StringService;
import com.example.allegroandroid.services.token.SessionService;
import com.example.allegroandroid.ui.MyViewModelFactory;
import com.example.allegroandroid.ui.utils.TimePickerFragment;
import com.example.allegroandroid.ui.validaciones.ValidatorUiService;
import com.example.allegroandroid.viewmodel.UserViewModel;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class CompleteProfileActivity extends AppCompatActivity {
    private static final String TAG = "CompleteProfileActivity.class";
    private FireBaseLoginService fireBaseLoginService;
    private EditText dateOfBirth,phone,lastName,firstName;
    private Button btnCreateUser;
    private DateService obtenerFecha;
    private FirebaseUser userF;
    private Toast toast;
    UserViewModel userViewModelNew;
    AppExecutors appExecutors;
    AppModule appModule;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_profile);
        init();
        btnCreateUser = findViewById(R.id.btnCreateUser);

        btnCreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser(view);
            }
        });
    }


    private void init() {
        fireBaseLoginService = new FireBaseLoginService(getString(R.string.default_web_client_id), this);
        phone = findViewById(R.id.editTextPhone);
        dateOfBirth = findViewById(R.id.editTextDate);
        lastName = findViewById(R.id.editTextTextPersonLastName);
        firstName = findViewById(R.id.editTextTextPersonName);
        obtenerFecha = DateService.getInstance();
        userF = fireBaseLoginService.getCurrentUser();
        appExecutors = new AppExecutors();
        appModule = new AppModule(getApplicationContext());
        userViewModelNew = ViewModelProviders.of(this, new MyViewModelFactory<>(this.getApplication(),
                new UserRepository(appExecutors, appModule.provideUserRetrofit(),appModule.provideDb(),
                        new SessionService(this.getApplicationContext())
                )))
                .get(UserViewModel.class);
        progress = new ProgressDialog(CompleteProfileActivity.this);
    }


    public void createUser(View v){
        try{
            validateInputs();
            UserRequest user = new UserRequest(userF.getEmail(), StringService.upperFirstLetter(firstName.getText().toString()),
                    StringService.upperFirstLetter(lastName.getText().toString()),
                    obtenerFecha.convertStringToDateString(dateOfBirth.getText().toString()), Rol.ROL_ALUMNO);

            userViewModelNew.setUserRequest(user);

            userViewModelNew.postUser().observe(this, new Observer<Resource<UserCreateResponse>>() {
                @Override
                public void onChanged(Resource<UserCreateResponse> userResponseResource) {
                    if (userResponseResource.status == Status.ERROR || userResponseResource.status == Status.ERRORFROMAPI) {
                        ActivitiesInitiator.initErrorActivity(new InitActitvy(v.getContext(), getBundle(userResponseResource.message)));
                    } else if (userResponseResource.status == Status.SUCCESS) {
                        ActivitiesInitiator.initAutenticationActivity(new InitActitvy(v.getContext(), null));
                        progress.dismiss();
                    } else {
                        progress.setTitle("Loading");
                        progress.setMessage("Registrando...");
                        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
                        progress.show();
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

    @NonNull
    private Bundle getBundle(String t) {
        Bundle pBundle = new Bundle();
        pBundle.putString(AppConstant.ERROR_MESSAGE, t);
        pBundle.putString(AppConstant.ACTIVITY_WITH_ERROR, this.TAG);
        return pBundle;
    }
}
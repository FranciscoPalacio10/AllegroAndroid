package com.example.allegrobackend.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import com.example.allegrobackend.models.InitActitvy;
import com.example.allegrobackend.models.user.UserRequest;
import com.example.allegrobackend.models.user.get.UserResponse;
import com.example.allegrobackend.repository.UserRepository;
import com.example.allegrobackend.services.DateService;
import com.example.allegrobackend.services.token.SessionService;

import java.util.Date;

public class UserViewModel extends AndroidViewModel{
    private UserRepository userRepository;
    private SessionService sessionService;

    public UserViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(new InitActitvy(application.getApplicationContext(),null));
        sessionService = new SessionService(application.getApplicationContext());
    }

    public LiveData<UserResponse> getUser(String email){
        return userRepository.get(email,sessionService.GetBearerToken(email).token);
    }

    public LiveData<UserResponse> createUser(UserRequest user) throws Exception {
         userCreateValidate(user);

        return userRepository.createUser(user);
    }

    private void userCreateValidate(UserRequest user) throws Exception {
        DateService dateService = DateService.getInstance();
        Date dateOfBirthUser= dateService.convertStringToDate(user.dateOfBirth);

        if(user.firstName == null){
            throw new Exception("Nombre no puede estar vacio");
        }

        if(user.lastName == null){
            throw new Exception("Apellido no puede estar vacio");
        }

        if(dateOfBirthUser.after(dateService.getDateNow())){
            throw new Exception("Fecha de nacimiento no puede ser posterior a hoy");
        }

        int diffYear = dateService.getYearsBetweenTwoDates(dateOfBirthUser,dateService.getDateNow());
        if (diffYear < 4){
            throw new Exception("Debes tener mas de cuatro años");
        }
    }

}

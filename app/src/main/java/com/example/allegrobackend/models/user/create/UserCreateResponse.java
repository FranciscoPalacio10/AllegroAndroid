package com.example.allegrobackend.models.user.create;

import com.example.allegrobackend.models.user.UserRequest;

import java.util.ArrayList;

public class UserCreateResponse extends UserRequest {
        public String userName;
        public Result result;

    public UserCreateResponse(String email, String firstName, String lastName, String dateOfBirth, String rol, String userName) {
        super(email, firstName, lastName, dateOfBirth, rol);
        this.userName = userName;
    }

    public class Result{
        public boolean succeeded;
        public ArrayList<Object> errors;
    }
}

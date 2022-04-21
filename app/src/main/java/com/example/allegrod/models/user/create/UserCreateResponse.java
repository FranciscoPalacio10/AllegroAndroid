package com.example.allegrod.models.user.create;

import com.example.allegrod.models.user.User;

import java.util.ArrayList;
import java.util.List;

public class UserCreateResponse extends User {
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

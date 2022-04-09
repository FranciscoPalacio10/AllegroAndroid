package com.example.allegrod.models.user.create;

import java.util.ArrayList;

public class UserCreateResponse {
        public String firstName;
        public String fastName;
        public String email;
        public String userName;
        public Result result;

    public class Result{
        public boolean succeeded;
        public ArrayList<Object> errors;
    }
}

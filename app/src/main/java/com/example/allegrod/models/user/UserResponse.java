package com.example.allegrod.models.user;

import java.util.ArrayList;

public class UserResponse {
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

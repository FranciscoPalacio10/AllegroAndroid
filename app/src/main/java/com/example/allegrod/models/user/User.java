package com.example.allegrod.models.user;

import java.util.Date;

public class User {
    public String email;
    public String firstName;
    public String lastName;
    public String dateOfBirth;
    public String rol;

    public User(String email, String firstName, String lastName, String dateOfBirth,String rol) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.rol = rol;
    }

    public User(String email, String firstName, String lastName, String rol) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.rol = rol;
    }

    public User(String email) {
        this.email = email;
    }
}

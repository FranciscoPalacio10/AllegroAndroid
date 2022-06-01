package com.example.allegrobackend.models.user;

import androidx.room.ColumnInfo;

public class UserRequest {
    public String email;
    @ColumnInfo(name = "first_name")
    public String firstName;
    @ColumnInfo(name = "last_name")
    public String lastName;
    public String dateOfBirth;
    @ColumnInfo(name = "rol")
    public String role;

    public UserRequest(String email, String firstName, String lastName, String dateOfBirth, String rol) {
        this.email = email.trim();
        this.firstName = firstName.trim();
        this.lastName = lastName.trim();
        this.dateOfBirth = dateOfBirth.trim();
        this.role = rol.trim();
    }

    public UserRequest(String email, String firstName, String lastName,String rol) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = rol;
    }

    public UserRequest(String email) {
        this.email = email;
    }
}

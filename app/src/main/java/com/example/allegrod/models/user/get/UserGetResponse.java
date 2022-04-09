package com.example.allegrod.models.user.get;

public class UserGetResponse {
    public String firstName;
    public String lastName;
    public String dateOfBirth;
    public String userRoles = null;
    public String id;
    public String userName;
    public String normalizedUserName;
    public String email;
    public String normalizedEmail;
    public boolean emailConfirmed;
    public String passwordHash = null;
    public String securityStamp;
    public String concurrencyStamp;
    public String phoneNumber = null;
    public boolean phoneNumberConfirmed;
    public boolean twoFactorEnabled;
    public String lockoutEnd = null;
    public boolean lockoutEnabled;
    public float accessFailedCount;
}

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

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getUserRoles() {
        return userRoles;
    }

    public String getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getNormalizedUserName() {
        return normalizedUserName;
    }

    public String getEmail() {
        return email;
    }

    public String getNormalizedEmail() {
        return normalizedEmail;
    }

    public boolean isEmailConfirmed() {
        return emailConfirmed;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getSecurityStamp() {
        return securityStamp;
    }

    public String getConcurrencyStamp() {
        return concurrencyStamp;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public boolean isPhoneNumberConfirmed() {
        return phoneNumberConfirmed;
    }

    public boolean isTwoFactorEnabled() {
        return twoFactorEnabled;
    }

    public String getLockoutEnd() {
        return lockoutEnd;
    }

    public boolean isLockoutEnabled() {
        return lockoutEnabled;
    }

    public float getAccessFailedCount() {
        return accessFailedCount;
    }
}


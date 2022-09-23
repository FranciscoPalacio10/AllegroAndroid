package com.example.allegroandroid.models.user;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;

public class User {
    public String email;
    @ColumnInfo(name = "first_name")
    public String firstName;
    @ColumnInfo(name = "last_name")
    public String lastName;
    public String dateOfBirth;
    @ColumnInfo(name = "role")
    public String rols;
    @NonNull
    public String id;
    @ColumnInfo(name = "user_roles")
    public String userRoles = null;
    @ColumnInfo(name = "user_name")
    public String userName;
    @ColumnInfo(name = "normalized_user_name")
    public String normalizedUserName;
    @ColumnInfo(name = "normalized_email")
    public String normalizedEmail;
    @ColumnInfo(name = "email_confirmed")
    public boolean emailConfirmed;
    @ColumnInfo(name = "password_hash")
    public String passwordHash = null;
    @ColumnInfo(name = "security_stamp")
    public String securityStamp;
    @ColumnInfo(name = "concurrency_stamp")
    public String concurrencyStamp;

    public User(String email, String firstName, String rols, String dateOfBirth, String userName) {
        this.email=email;
        this.firstName=firstName;
        this.rols=rols;
        this.dateOfBirth=dateOfBirth;
        this.userName=userName;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(String userRoles) {
        this.userRoles = userRoles;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNormalizedUserName() {
        return normalizedUserName;
    }

    public void setNormalizedUserName(String normalizedUserName) {
        this.normalizedUserName = normalizedUserName;
    }

    public String getNormalizedEmail() {
        return normalizedEmail;
    }

    public void setNormalizedEmail(String normalizedEmail) {
        this.normalizedEmail = normalizedEmail;
    }

    public boolean isEmailConfirmed() {
        return emailConfirmed;
    }

    public void setEmailConfirmed(boolean emailConfirmed) {
        this.emailConfirmed = emailConfirmed;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getSecurityStamp() {
        return securityStamp;
    }

    public void setSecurityStamp(String securityStamp) {
        this.securityStamp = securityStamp;
    }

    public String getConcurrencyStamp() {
        return concurrencyStamp;
    }

    public void setConcurrencyStamp(String concurrencyStamp) {
        this.concurrencyStamp = concurrencyStamp;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isPhoneNumberConfirmed() {
        return phoneNumberConfirmed;
    }

    public void setPhoneNumberConfirmed(boolean phoneNumberConfirmed) {
        this.phoneNumberConfirmed = phoneNumberConfirmed;
    }

    public boolean isTwoFactorEnabled() {
        return twoFactorEnabled;
    }

    public void setTwoFactorEnabled(boolean twoFactorEnabled) {
        this.twoFactorEnabled = twoFactorEnabled;
    }

    public String getLockoutEnd() {
        return lockoutEnd;
    }

    public void setLockoutEnd(String lockoutEnd) {
        this.lockoutEnd = lockoutEnd;
    }

    public boolean isLockoutEnabled() {
        return lockoutEnabled;
    }

    public void setLockoutEnabled(boolean lockoutEnabled) {
        this.lockoutEnabled = lockoutEnabled;
    }

    public float getAccessFailedCount() {
        return accessFailedCount;
    }

    public void setAccessFailedCount(float accessFailedCount) {
        this.accessFailedCount = accessFailedCount;
    }

    @ColumnInfo(name = "phone_mumber")
    public String phoneNumber = null;
    @ColumnInfo(name = "phone_number_nonfirmed")
    public boolean phoneNumberConfirmed;
    @ColumnInfo(name = "two_factor_enabled")
    public boolean twoFactorEnabled;
    @ColumnInfo(name = "lockout_end")
    public String lockoutEnd = null;
    @ColumnInfo(name = "lockout_enabled")
    public boolean lockoutEnabled;
    @ColumnInfo(name = "access_failed_count")
    public float accessFailedCount;
}

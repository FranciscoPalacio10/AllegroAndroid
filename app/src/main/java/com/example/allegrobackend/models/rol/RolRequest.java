package com.example.allegrobackend.models.rol;

public class RolRequest {

    public String rol;
    public String email;

    public RolRequest(String rol, String email) {
        this.rol = rol;
        this.email = email;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

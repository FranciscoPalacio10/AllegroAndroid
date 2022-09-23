package com.example.allegroandroid.models.pointsxuser;

public class PointXUserRequest {

    public int historialDeClasesId;
    public int pointsIA;

    public PointXUserRequest(int historialDeClaseId, int pointsIA) {
        this.historialDeClasesId = historialDeClaseId;
        this.pointsIA = pointsIA;
    }

    public PointXUserRequest() {
    }
}

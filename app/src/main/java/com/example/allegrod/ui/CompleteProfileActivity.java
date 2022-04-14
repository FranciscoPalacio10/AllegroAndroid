package com.example.allegrod.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.allegrod.R;
import com.example.allegrod.services.DateService;
import com.example.allegrod.ui.start.utils.TimePickerFragment;

public class CompleteProfileActivity extends AppCompatActivity {
    private EditText dateOfBirth;
    private DateService obtenerFecha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_profile);

        dateOfBirth = findViewById(R.id.editTextDate);
        obtenerFecha = DateService.getInstance();
    }

    public void showTimePickerDialog(View v) {
        TimePickerFragment newFragment = TimePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                dateOfBirth.setText(obtenerFecha.parseDate(year,month,day));
            }
        });
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

}
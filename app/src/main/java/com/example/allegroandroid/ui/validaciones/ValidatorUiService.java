package com.example.allegroandroid.ui.validaciones;

import android.widget.EditText;

import java.util.List;

public class ValidatorUiService {
        public static void validateEditText(List<EditText> listToValidate) throws Exception {
            for (EditText editText: listToValidate) {
                if(editText.getText().toString().isEmpty()){
                   String nameEditText= editText.getHint().toString();
                    throw new Exception("Campo no puede ser vacio: "+ nameEditText);
                }
            }
        }
}
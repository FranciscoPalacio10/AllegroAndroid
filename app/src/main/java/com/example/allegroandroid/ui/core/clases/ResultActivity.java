package com.example.allegroandroid.ui.core.clases;

import static com.example.allegroandroid.constants.AppConstant.RESULT_CLASE_EVALUATE;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.allegroandroid.R;
import com.example.allegroandroid.constants.AppConstant;
import com.example.allegroandroid.models.InitActitvy;
import com.example.allegroandroid.models.ResultClaseEvaluate;
import com.example.allegroandroid.services.ActivitiesInitiator;
import com.example.allegroandroid.services.DateService;
import com.google.gson.Gson;

import java.util.Locale;

public class ResultActivity extends AppCompatActivity {
    private TextView txt_materia_resultado, txt_evaluado, txt_fecha_resultados,txt_puntos_sumados, txt_resultado;
    private ResultClaseEvaluate resultClaseEvaluate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        resultClaseEvaluate = getExtras(RESULT_CLASE_EVALUATE);
        getSupportActionBar().hide();

        txt_materia_resultado = findViewById(R.id.txt_materia_resultado);
        txt_evaluado = findViewById(R.id.txt_evaluado);
        txt_fecha_resultados = findViewById(R.id.txt_fecha_resultados);
        txt_puntos_sumados = findViewById(R.id.txt_puntos_sumados);
        txt_resultado = findViewById(R.id.txt_resultado);

        txt_materia_resultado.setText(resultClaseEvaluate.historialDeClaseResponse.clase.materia.getName().toUpperCase(Locale.ROOT));
        txt_evaluado.setText(resultClaseEvaluate.historialDeClaseResponse.clase.name.toUpperCase(Locale.ROOT));
        txt_fecha_resultados.setText(DateService.getInstance().getFechaSistema());
        txt_resultado.setText(resultClaseEvaluate.poseEvaluatedResult.confidence + "% " + resultClaseEvaluate.poseEvaluatedResult.resultado);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivitiesInitiator.initAutenticationActivity(new InitActitvy(this,null));
    }

    private ResultClaseEvaluate getExtras(String key) {
        Bundle resultClase = getIntent().getExtras();

        Gson gson = new Gson();
        ResultClaseEvaluate jsonResultClaseEvaluate = gson.fromJson(resultClase.getString(RESULT_CLASE_EVALUATE), ResultClaseEvaluate.class);
        return jsonResultClaseEvaluate;
    }

}
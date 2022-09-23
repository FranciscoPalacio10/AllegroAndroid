package com.example.allegroandroid.ui.core.clases;

import static com.example.allegroandroid.constants.AppConstant.RESULT_CLASE_EVALUATE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.TextView;

import com.example.allegroandroid.R;
import com.example.allegroandroid.constants.AppConstant;
import com.example.allegroandroid.models.InitActitvy;
import com.example.allegroandroid.models.ResultClaseEvaluate;
import com.example.allegroandroid.models.historialdeclase.HistorialDeClaseRequest;
import com.example.allegroandroid.models.historialdeclase.HistorialDeClaseResponse;
import com.example.allegroandroid.models.pointsxuser.PointXUserRequest;
import com.example.allegroandroid.models.pointsxuser.PointXUserResponse;
import com.example.allegroandroid.repository.HistorialDeClaseRepository;
import com.example.allegroandroid.repository.PointXUserRepository;
import com.example.allegroandroid.repository.resource.Resource;
import com.example.allegroandroid.repository.resource.Status;
import com.example.allegroandroid.services.ActivitiesInitiator;
import com.example.allegroandroid.services.DateService;
import com.example.allegroandroid.services.FireBaseLoginService;
import com.example.allegroandroid.services.token.SessionService;
import com.example.allegroandroid.ui.MyViewModelFactory;
import com.example.allegroandroid.utils.AppExecutors;
import com.example.allegroandroid.utils.AppModule;
import com.example.allegroandroid.viewmodel.HistorialDeClasesViewModel;
import com.example.allegroandroid.viewmodel.PointXUserViewModel;
import com.google.gson.Gson;

import java.util.Locale;

public class ResultActivity extends AppCompatActivity {
    private static final String TAG = "ResultActivity.class";
    private TextView txt_materia_resultado, txt_evaluado, txt_fecha_resultados,txt_puntos_sumados, txt_resultado;
    private ResultClaseEvaluate resultClaseEvaluate;
    private HistorialDeClasesViewModel historialDeClasesViewModel;
    private PointXUserViewModel pointXUserViewModel;
    private AppExecutors appExecutors;
    private AppModule appModule;
    private FireBaseLoginService fireBaseLoginService;
    private ResultClaseEvaluate jsonResultClaseEvaluate;
    private ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        resultClaseEvaluate = getExtras(RESULT_CLASE_EVALUATE);
        getSupportActionBar().hide();

        progress = new ProgressDialog(this);
        txt_materia_resultado = findViewById(R.id.txt_materia_resultado);
        txt_evaluado = findViewById(R.id.txt_evaluado);
        txt_fecha_resultados = findViewById(R.id.txt_fecha_resultados);
        txt_puntos_sumados = findViewById(R.id.txt_puntos_sumados);
        txt_resultado = findViewById(R.id.txt_resultado);

        txt_materia_resultado.setText(resultClaseEvaluate.historialDeClaseResponse.clase.materia.getName().toUpperCase(Locale.ROOT));
        txt_evaluado.setText(resultClaseEvaluate.historialDeClaseResponse.clase.name.toUpperCase(Locale.ROOT));
        txt_fecha_resultados.setText(DateService.getInstance().getFechaSistema());
        txt_resultado.setText(resultClaseEvaluate.poseEvaluatedResult.confidence + "% " + resultClaseEvaluate.poseEvaluatedResult.resultado);

        Integer resultado = 1;
        if(resultClaseEvaluate.poseEvaluatedResult.resultado.toUpperCase(Locale.ROOT).equals("BIEN")){
            resultado = resultClaseEvaluate.poseEvaluatedResult.confidence.intValue();
        }

        txt_puntos_sumados.setText(resultado.toString());

        initServices(resultado);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivitiesInitiator.initAutenticationActivity(new InitActitvy(this,null));
    }

    private ResultClaseEvaluate getExtras(String key) {
        Bundle resultClase = getIntent().getExtras();

        Gson gson = new Gson();
        jsonResultClaseEvaluate = gson.fromJson(resultClase.getString(RESULT_CLASE_EVALUATE), ResultClaseEvaluate.class);
        return jsonResultClaseEvaluate;
    }

    private void initHistorialClaseService() {
        historialDeClasesViewModel = ViewModelProviders.of(this, new MyViewModelFactory<>(getApplication(),
                new HistorialDeClaseRepository(appExecutors, appModule.provideHistorialDeClaseRetrofit(), appModule.provideDb(),
                        new SessionService(this)
                )))
                .get(HistorialDeClasesViewModel.class);
    }

    private void initServices(int resultado) {
        appExecutors = new AppExecutors();
        appModule = new AppModule(getApplicationContext());
        fireBaseLoginService = new FireBaseLoginService(getString(R.string.default_web_client_id), this);
        initHistorialClaseService();
        initPointXUserService();
        updateHistorial(resultado);

    }

    private void initPointXUserService() {
        pointXUserViewModel = ViewModelProviders.of(this, new MyViewModelFactory<>(getApplication(),
                new PointXUserRepository(appExecutors, appModule.providePointsXUserRetrofit(), appModule.provideDb(),
                        new SessionService(this)
                )))
                .get(PointXUserViewModel.class);
    }

    private void updateHistorial(int resultado) {
        int valor= 1;
        historialDeClasesViewModel.setHistorialClaseRequestPost(new HistorialDeClaseRequest(true,
                jsonResultClaseEvaluate.historialDeClaseResponse.clase.claseId, valor, valor, resultado));

        historialDeClasesViewModel.putHistorialDeClases(fireBaseLoginService.getCurrentUser().getEmail(),
                jsonResultClaseEvaluate.historialDeClaseResponse.historialDeClaseId).observe(this, new Observer<Resource<HistorialDeClaseResponse>>() {
            @Override
            public void onChanged(Resource<HistorialDeClaseResponse> historialDeClaseResponseResource) {
                if (historialDeClaseResponseResource.status == Status.ERROR) {
                    ActivitiesInitiator.initErrorActivity(new InitActitvy(getApplicationContext(), getBundle(historialDeClaseResponseResource.message)));
                } else if (historialDeClaseResponseResource.status == Status.SUCCESS) {
                    jsonResultClaseEvaluate.historialDeClaseResponse = historialDeClaseResponseResource.data;
                    progress.dismiss();
                } else {
                    progress.setTitle("Loading");
                    progress.setMessage("Espera unos segundos...");
                    progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
                    progress.show();
                }
            }
        });
    }

    @NonNull
    private Bundle getBundle(String t) {
        Bundle pBundle = new Bundle();
        pBundle.putString(AppConstant.ERROR_MESSAGE, t);
        pBundle.putString(AppConstant.ACTIVITY_WITH_ERROR, this.TAG);
        return pBundle;
    }

}
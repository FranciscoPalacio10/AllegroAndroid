package com.example.allegroandroid.ui.core;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.allegroandroid.R;
import com.example.allegroandroid.constants.AppConstant;
import com.example.allegroandroid.ia.LivePreviewActivity;
import com.example.allegroandroid.ia.StillImageActivity;
import com.example.allegroandroid.ia.posedetector.ConstantPoseToEvalute;
import com.example.allegroandroid.models.CamaraSelect;
import com.example.allegroandroid.models.historialdeclase.HistorialDeClaseResponse;
import com.example.allegroandroid.ui.core.adapters.AdapterSelectCamaraClasePractica;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelectCamaraClasePracticaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectCamaraClasePracticaFragment extends Fragment {
    private static final String TAG = "ChooserActivity";

    private static  HistorialDeClaseResponse historialDeClaseResponse;
    private static final int CODIGO_PERMISOS_CAMARA = 1;
    private RecyclerView recyclerView;
    private TextView txtPoseAEvaularCamera;
    @SuppressWarnings("NewApi") // CameraX is only available on API 21+
    private static final Class<?>[] CLASSES =
            Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP
                    ? new Class<?>[]{
                    LivePreviewActivity.class, StillImageActivity.class,
            }
                    : new Class<?>[]{
                    LivePreviewActivity.class,
                    StillImageActivity.class,
            };

    private static final int[] DESCRIPTION_IDS =
            Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP
                    ? new int[]{
                    R.string.desc_camera_source_activity, R.string.desc_still_image_activity,
            }
                    : new int[]{
                    R.string.desc_camera_source_activity,
                    R.string.desc_still_image_activity,
            };

    public SelectCamaraClasePracticaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SelectCamaraClasePracticaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SelectCamaraClasePracticaFragment newInstance(String param1, String param2) {
        SelectCamaraClasePracticaFragment fragment = new SelectCamaraClasePracticaFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            HistorialDeClaseResponse historialDeClaseResponse = getExtras(AppConstant.HISTORIAL_DE_CLASE_RESPONSE);
            this.historialDeClaseResponse = historialDeClaseResponse;
        }
    }

    private HistorialDeClaseResponse getExtras(String key) {
        String historialClaseString = getArguments().getString(key);
        Gson gson = new Gson();
        HistorialDeClaseResponse jsonHistorialDeClaseResponse = gson.fromJson(historialClaseString, HistorialDeClaseResponse.class);
        return jsonHistorialDeClaseResponse;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_select_camara_clase_practica, container, false);
        recyclerView = v.findViewById(R.id.reciclyerSelectCamara);
        txtPoseAEvaularCamera = v.findViewById(R.id.txtPoseAEvaularCamera);

        txtPoseAEvaularCamera.setText(historialDeClaseResponse.clase.name.toUpperCase(Locale.ROOT));

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.HORIZONTAL));
        recyclerView.setLayoutManager(layoutManager);

        CamaraSelect livePreviewActivity = new CamaraSelect(getString(R.string.camaraEnVivo), getString(R.string.cameraEnVivoDescripcion),
                R.id.action_selectCamaraClasePracticaFragment_to_livePreviewActivity, historialDeClaseResponse);

        CamaraSelect stillImageActivity = new CamaraSelect(getString(R.string.camaraCelular), getString(R.string.camaraCelularDescripcion),
                R.id.action_selectCamaraClasePracticaFragment_to_stillImageActivity, historialDeClaseResponse);

        List<CamaraSelect> camaraSelectList = new ArrayList<CamaraSelect>();
        camaraSelectList.add(livePreviewActivity);
        camaraSelectList.add(stillImageActivity);

        AdapterSelectCamaraClasePractica adapterCursos = new AdapterSelectCamaraClasePractica(camaraSelectList, R.layout.adaptadorselectcamera, getContext());

        recyclerView.setAdapter(adapterCursos);

        return v;
    }




}
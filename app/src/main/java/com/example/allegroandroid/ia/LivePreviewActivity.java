/*
 * Copyright 2020 Google LLC. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.allegroandroid.ia;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.allegroandroid.PermissionApp;
import com.example.allegroandroid.R;
import com.example.allegroandroid.constants.AppConstant;
import com.example.allegroandroid.ia.posedetector.PoseDetectorProcessor;
import com.example.allegroandroid.ia.preference.PreferenceUtils;
import com.example.allegroandroid.models.historialdeclase.HistorialDeClaseRequest;
import com.example.allegroandroid.models.historialdeclase.HistorialDeClaseResponse;
import com.example.allegroandroid.repository.HistorialDeClaseRepository;
import com.example.allegroandroid.repository.resource.Resource;
import com.example.allegroandroid.repository.resource.Status;
import com.example.allegroandroid.services.FireBaseLoginService;
import com.example.allegroandroid.services.token.SessionService;
import com.example.allegroandroid.ui.MyViewModelFactory;
import com.example.allegroandroid.utils.AppExecutors;
import com.example.allegroandroid.utils.AppModule;
import com.example.allegroandroid.viewmodel.HistorialDeClasesViewModel;
import com.google.android.gms.common.annotation.KeepName;

import com.google.gson.Gson;
import com.google.mlkit.vision.pose.PoseDetectorOptionsBase;

import java.io.IOException;

/**
 * Live preview demo for ML Kit APIs.
 */
@KeepName
public final class LivePreviewActivity extends AppCompatActivity
        implements OnItemSelectedListener, CompoundButton.OnCheckedChangeListener, IChangeListener {

    private static final String POSE_DETECTION = "Pose Detection";

    private static final String TAG = "LivePreviewActivity";

    private CameraSource cameraSource = null;
    private CameraSourcePreview preview;
    private GraphicOverlay graphicOverlay;
    private String selectedModel = POSE_DETECTION;
    private Speaker speaker;
    private PermissionApp permissionApp;
    private HistorialDeClaseResponse historialDeClaseResponse;
    private ExplicationPose explicationPose;
    private HistorialDeClasesViewModel historialDeClasesViewModel;
    private AppExecutors appExecutors;
    private AppModule appModule;
    private FireBaseLoginService fireBaseLoginService;
    private boolean showCamera = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_vision_live_preview);

        Bundle getIntent = getIntent().getExtras();
        explicationPose = new ExplicationPose(this);
        explicationPose.setChangeListener(this);
        preview = findViewById(R.id.preview_view);

        if (preview == null) {
            Log.d(TAG, "Preview is null");
        }

        graphicOverlay = findViewById(R.id.graphic_overlay);

        if (graphicOverlay == null) {
            Log.d(TAG, "graphicOverlay is null");
        }

        speaker = new Speaker(getApplicationContext());

        if (getIntent != null) {
            HistorialDeClaseResponse historialDeClaseResponse = getExtras(AppConstant.HISTORIAL_DE_CLASE_RESPONSE);
            this.historialDeClaseResponse = historialDeClaseResponse;
            explicationPose.showExplicationPose(historialDeClaseResponse.clase.name);
            initHistorialClaseService();
        }

        ToggleButton facingSwitch = findViewById(R.id.facing_switch);
        facingSwitch.setOnCheckedChangeListener(this);
        ImageButton btnInfo = findViewById(R.id.info_pose);
        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                explicationPose.showExplicationPose(historialDeClaseResponse.clase.name);
            }
        });
    }

    private void initHistorialClaseService() {
        appExecutors = new AppExecutors();
        appModule = new AppModule(getApplicationContext());

        fireBaseLoginService = new FireBaseLoginService(getString(R.string.default_web_client_id), this);

        historialDeClasesViewModel = ViewModelProviders.of(this, new MyViewModelFactory<>(getApplication(),
                new HistorialDeClaseRepository(appExecutors, appModule.provideHistorialDeClaseRetrofit(), appModule.provideDb(),
                        new SessionService(this)
                )))
                .get(HistorialDeClasesViewModel.class);
    }

    private void postHistorial() {
        historialDeClasesViewModel.setHistorialClaseRequestPost(new HistorialDeClaseRequest(false, historialDeClaseResponse.clase.claseId, 1,
                1, 1));
        historialDeClasesViewModel.postHistorialDeClases(fireBaseLoginService.getCurrentUser().getEmail()).observe(this, new Observer<Resource<HistorialDeClaseResponse>>() {
            @Override
            public void onChanged(Resource<HistorialDeClaseResponse> historialDeClaseResponseResource) {
                if (historialDeClaseResponseResource.status == Status.SUCCESS) {
                    historialDeClaseResponse = historialDeClaseResponseResource.data;
                    createCameraSource(selectedModel);
                    startCameraSource();
                }
            }
        });
    }


    private HistorialDeClaseResponse getExtras(String key) {
        String historialClaseString = getIntent().getExtras().getString(key);
        Gson gson = new Gson();
        HistorialDeClaseResponse jsonHistorialDeClaseResponse = gson.fromJson(historialClaseString, HistorialDeClaseResponse.class);
        return jsonHistorialDeClaseResponse;
    }

    @Override
    protected void onStart() {
        super.onStart();
        permissionApp = new PermissionApp(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {

        permissionApp.processonRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public synchronized void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        selectedModel = parent.getItemAtPosition(pos).toString();
        Log.d(TAG, "Selected model: " + selectedModel);
        preview.stop();
        createCameraSource(selectedModel);
        startCameraSource();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Do nothing.
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Log.d(TAG, "Set facing");
        if (cameraSource != null) {
            if (isChecked) {
                cameraSource.setFacing(CameraSource.CAMERA_FACING_FRONT);
            } else {
                cameraSource.setFacing(CameraSource.CAMERA_FACING_BACK);
            }
        }
        preview.stop();
        startCameraSource();
    }

    private void createCameraSource(String model) {
        // If there's no existing cameraSource, create one.
        if (cameraSource == null) {
            cameraSource = new CameraSource(this, graphicOverlay);
        }

        try {
            switch (model) {
                case POSE_DETECTION:
                    PoseDetectorOptionsBase poseDetectorOptions =
                            PreferenceUtils.getPoseDetectorOptionsForLivePreview(this);
                    Log.i(TAG, "Using Pose Detector with options " + poseDetectorOptions);
                    boolean shouldShowInFrameLikelihood =
                            PreferenceUtils.shouldShowPoseDetectionInFrameLikelihoodLivePreview(this);
                    boolean visualizeZ = PreferenceUtils.shouldPoseDetectionVisualizeZ(this);
                    boolean rescaleZ = PreferenceUtils.shouldPoseDetectionRescaleZForVisualization(this);
                    boolean runClassification = PreferenceUtils.shouldPoseDetectionRunClassification(this);
                    cameraSource.setMachineLearningFrameProcessor(
                            new PoseDetectorProcessor(
                                    this,
                                    poseDetectorOptions,
                                    shouldShowInFrameLikelihood,
                                    visualizeZ,
                                    rescaleZ,
                                    runClassification,
                                    historialDeClaseResponse,
                                    /* isStreamMode = */ true,
                                    speaker));
                    break;
                default:
                    Log.e(TAG, "Unknown model: " + model);
            }
        } catch (RuntimeException e) {
            Log.e(TAG, "Can not create image processor: " + model, e);
            Toast.makeText(
                    getApplicationContext(),
                    "Can not create image processor: " + e.getMessage(),
                    Toast.LENGTH_LONG)
                    .show();
        }
    }

    /**
     * Starts or restarts the camera source, if it exists. If the camera source doesn't exist yet
     * (e.g., because onResume was called before the camera source was created), this will be called
     * again when the camera source is created.
     */
    private void startCameraSource() {
        if (cameraSource != null) {
            try {
                if (preview == null) {
                    Log.d(TAG, "resume: Preview is null");
                }
                if (graphicOverlay == null) {
                    Log.d(TAG, "resume: graphOverlay is null");
                }
                preview.start(cameraSource, graphicOverlay);
            } catch (IOException e) {
                Log.e(TAG, "Unable to start camera source.", e);
                cameraSource.release();
                cameraSource = null;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        if (showCamera) {
            createCameraSource(selectedModel);
            startCameraSource();
        }
    }


    @Override
    public void onBackPressed() {
        if (cameraSource != null) {
            cameraSource.release();
            speaker.destroy();
        }
        super.onBackPressed();
    }

    /**
     * Stops the camera.
     */
    @Override
    protected void onPause() {
        super.onPause();
        preview.stop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (cameraSource != null) {
            cameraSource.release();
            speaker.destroy();
        }
    }

    @Override
    public void alertDialogIsClosed() {
        if(!showCamera){
            showCamera = true;
            postHistorial();
        }
    }
}


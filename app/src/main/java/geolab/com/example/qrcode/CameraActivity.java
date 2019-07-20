package geolab.com.example.qrcode;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import github.nisrulz.qreader.QRDataListener;
import github.nisrulz.qreader.QREader;

public class CameraActivity extends AppCompatActivity {
    private SurfaceView cameraView;
    private TextView readTxtView;
    private QREader qrEader;
    private TextView noPermissionTxtView;
    private int cameraPermissionRequestCode = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        cameraView = findViewById(R.id.camera_view_id);
        readTxtView = findViewById(R.id.text_field_id);
        noPermissionTxtView = findViewById(R.id.no_permission_txt_view_id);


        qrEader = new QREader.Builder(this, cameraView, new QRDataListener() {
            @Override
            public void onDetected(String data) {
                readTxtView.post(new Runnable() {
                    @Override
                    public void run() {
                        readTxtView.setText(data + "\n");
                    }
                });
            }
        }).facing(QREader.BACK_CAM)
                .enableAutofocus(true)
                .height(cameraView.getHeight())
                .width(cameraView.getWidth())
                .build();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (hasCameraPermission()) {
            qrEader.initAndStart(cameraView);
        } else {
            requestPermission();
        }
    }

    private boolean hasCameraPermission() {
        int res = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        return res == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, cameraPermissionRequestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == cameraPermissionRequestCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                qrEader.initAndStart(cameraView);
            }
        } else {
            Toast.makeText(this, "permission is switched off", Toast.LENGTH_SHORT).show();
        }
    }
}

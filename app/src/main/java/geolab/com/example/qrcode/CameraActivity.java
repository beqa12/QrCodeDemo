package geolab.com.example.qrcode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Camera;
import android.os.Bundle;
import android.view.SurfaceView;
import android.widget.TextView;

import androidmads.library.qrgenearator.QRGContents;
import github.nisrulz.qreader.QRDataListener;
import github.nisrulz.qreader.QREader;

public class CameraActivity extends AppCompatActivity {
    private SurfaceView cameraView;
    private QREader qrEader;
    private TextView codeReadTxtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera2);
        cameraView = findViewById(R.id.camera_view_id);
        codeReadTxtView = findViewById(R.id.text_id);
        qrEader = new QREader.Builder(this, cameraView, new QRDataListener() {
            @Override
            public void onDetected(String data) {
                codeReadTxtView.post(new Runnable() {
                    @Override
                    public void run() {
                        codeReadTxtView.setText(data);
                        codeReadTxtView.setOnClickListener(v->{
                            startActivity(new Intent(CameraActivity.this, ScannerActivity.class));
                        });

                    }
                });
            }
        }).facing(QREader.BACK_CAM)
                .height(cameraView.getHeight())
                .width(cameraView.getWidth())
                .build();
    }

    @Override
    protected void onResume() {
        super.onResume();
        qrEader.initAndStart(cameraView);
    }

    @Override
    protected void onStop() {
        super.onStop();
        qrEader.stop();
    }
}

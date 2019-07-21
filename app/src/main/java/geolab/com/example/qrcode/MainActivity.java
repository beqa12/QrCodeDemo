package geolab.com.example.qrcode;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.zxing.WriterException;

import java.io.File;
import java.io.FileOutputStream;
import java.security.PrivilegedAction;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import github.nisrulz.qreader.QREader;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    private Button generateBtn;
    private EditText userName;
    private EditText userMobileNumber;
    private QRGEncoder encoder;
    private Button cameraBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        System.out.println("----");
        System.out.println("beqa");
    }
    private void initUI() {
        imageView = findViewById(R.id.qr_code_image_id);
        generateBtn = findViewById(R.id.generate_btn_id);
        userName = findViewById(R.id.user_name_id);
        userMobileNumber = findViewById(R.id.user_number_id);
        cameraBtn = findViewById(R.id.start_camera_id);
        initAction();
    }
    private void initAction(){
        generateBtn.setOnClickListener(v -> {
            String name = userName.getText().toString();
            String number = userMobileNumber.getText().toString();
            Bundle bundle = new Bundle();
            bundle.putString("number", "599114713");
            genarateQrCode(name, bundle);
        });
        cameraBtn.setOnClickListener(v->{
            camaerHasPermission();
        });
    }
    private void genarateQrCode(String name, Bundle bundle){
        encoder = new QRGEncoder(name, bundle, QRGContents.Type.TEXT, 200);
        try {
            Bitmap bitmap = encoder.encodeAsBitmap();
            imageView.setImageBitmap(bitmap);
            File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File pathFile = new File(file, "beqa");
            FileOutputStream fo = null;
            try {
                fo = new FileOutputStream(pathFile);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fo);
                Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show();
            }catch (Exception ex){
                ex.printStackTrace();
            }
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
    private void startCamera(){
        Intent intent = new Intent(this, CameraActivity.class);
        startActivity(intent);
    }
    private void camaerHasPermission(){
        int res = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if(res == PackageManager.PERMISSION_GRANTED){
            startCamera();
        }else{
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 100);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 100){
            if (grantResults [0] == PackageManager.PERMISSION_GRANTED){
                startCamera();
            }else {
                Toast.makeText(this, "permission is denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

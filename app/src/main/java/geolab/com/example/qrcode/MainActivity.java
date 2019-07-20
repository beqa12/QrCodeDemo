package geolab.com.example.qrcode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidmads.library.qrgenearator.QRGSaver;

public class MainActivity extends AppCompatActivity {
    private Button generateBtn;
    private EditText userNameEditTxtView;
    private EditText userMobileNumberEditTxtView;
    private ImageView qrCodeImageView;
    private Button cameraBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }
    private void initUI(){
        generateBtn = findViewById(R.id.generate_btn_id);
        userNameEditTxtView = findViewById(R.id.user_name_id);
        userMobileNumberEditTxtView = findViewById(R.id.user_mobile_number_id);
        qrCodeImageView = findViewById(R.id.qr_code_image_id);
        cameraBtn = findViewById(R.id.start_camera_id);
        initAction();
    }

    private void initAction(){
        generateBtn.setOnClickListener(v -> {
            String userName = userNameEditTxtView.getText().toString();
//            String phoneNumber = userMobileNumberEditTxtView.getText().toString();
            String phoneNumber = "655645";
            generateMyQrCode(userName, phoneNumber);
        });
        cameraBtn.setOnClickListener(v->{
            startCamera();
        });
    }
    private void generateMyQrCode(String name,String ph){
        QRGEncoder qrgEncoder = new QRGEncoder(name, null, QRGContents.Type.TEXT, 250);
        try{
            Bitmap bitmap = qrgEncoder.encodeAsBitmap();
            qrCodeImageView.setImageBitmap(bitmap);
//            String result;
//            String savePath = Environment.getExternalStorageDirectory().getPath();
//            boolean save = QRGSaver.save(savePath, name, bitmap, QRGContents.ImageType.IMAGE_JPEG);
//            result = save ? "image saved" : "image not saved";
//            Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Log.e("qr code", e.toString());
        }
    }

    private void startCamera(){
        Intent intent  =new Intent(MainActivity.this, CameraActivity.class);
        startActivity(intent);
    }
}

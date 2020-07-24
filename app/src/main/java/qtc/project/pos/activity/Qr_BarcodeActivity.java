package qtc.project.pos.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.hardware.Camera;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Result;
import com.google.zxing.Writer;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.ArrayList;
import java.util.Hashtable;

import b.laixuantam.myaarlibrary.helper.map.location.PermissionUtils;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import qtc.project.pos.R;

public class Qr_BarcodeActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 50;
    private EditText editTextProductId;
    private Button buttonGenerate, buttonScan;
    private ImageView imageViewResult;
    String productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr__barcode);

        checkpermission2();
//        if (checkpermission2()){
//            Toast.makeText(this, "thanh cong", Toast.LENGTH_SHORT).show();
//        }
//        else {
//            requestPermission();
//        }

        initView();
    }

    private void checkpermission2() {
        ArrayList<String> permissions = new ArrayList<>();
        PermissionUtils permissionUtils = new PermissionUtils(Qr_BarcodeActivity.this, new PermissionUtils.PermissionResultCallback() {
            @Override
            public void PermissionGranted(int request_code) {
                if (request_code == 2002){
                    Toast.makeText(Qr_BarcodeActivity.this, "Đồng ý", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void PartialPermissionGranted(int request_code, ArrayList<String> granted_permissions) {

            }

            @Override
            public void PermissionDenied(int request_code) {

            }

            @Override
            public void NeverAskAgain(int request_code) {

            }
        });

        permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissions.add(Manifest.permission.CAMERA);

        permissionUtils.check_permission(permissions, "Quyền truy cập camera", 2002);
    }

    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        }
        return true;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA},
                PERMISSION_REQUEST_CODE);
    }

    private void initView() {
        editTextProductId = findViewById(R.id.editTextProductId);
        imageViewResult = findViewById(R.id.imageViewResult);
        buttonGenerate = findViewById(R.id.buttonGenerate);
        buttonGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonGenerate_onClick();
            }
        });
        buttonScan = findViewById(R.id.buttonScan);
        buttonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonScan_onClick(view);
            }
        });
    }

    private void buttonGenerate_onClick() {
        try {
            String productId = editTextProductId.getText().toString();
            Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<EncodeHintType, ErrorCorrectionLevel>();
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            Writer codeWriter;
            codeWriter = new Code128Writer();
            BitMatrix byteMatrix = codeWriter.encode(productId, BarcodeFormat.CODE_128,400, 200, hintMap);
            int width = byteMatrix.getWidth();
            int height = byteMatrix.getHeight();
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    bitmap.setPixel(i, j, byteMatrix.get(i, j) ? Color.BLACK : Color.WHITE);
                }
            }
            imageViewResult.setImageBitmap(bitmap);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void buttonScan_onClick(View view) {
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        intentIntegrator.setCameraId(0);
        intentIntegrator.initiateScan();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {
            productId = intentResult.getContents();
            editTextProductId.setText(productId);
            buttonGenerate_onClick();
            Toast.makeText(getApplicationContext(), productId, Toast.LENGTH_LONG).show();
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
package com.example.gldng.attendancewatchsheet;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.MultiFormatReader;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.google.zxing.qrcode.QRCodeReader;

public class AttandanceQrreaderActivity extends AppCompatActivity implements NavigationMenuActions{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attandance_qrreader);

        ImageView imageView = findViewById(R.id.qrReader);

        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.DATA_MATRIX_TYPES);
        intentIntegrator.setPrompt("Scan the qr code");
        intentIntegrator.setCameraId(0);
        intentIntegrator.initiateScan();

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        Toast.makeText(this,result.getContents(),Toast.LENGTH_LONG).show();
    }

    public void navMenuSelection(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.navigation_home:
                intent = new Intent(AttandanceQrreaderActivity.this,HomeActivityInstructor.class);
                break;
            case R.id.navigation_courses:
                intent = new Intent(AttandanceQrreaderActivity.this,Courses_Instructor.class);
                break;
            case R.id.navigation_attend:
                intent = new Intent(AttandanceQrreaderActivity.this,AttandanceSelectorActivity.class);
                break;
            case R.id.navigation_calendar:
                //intent = new Intent(HomeActivityInstructor.this,.class);
                break;
        }
        startActivity(intent);
    }

}

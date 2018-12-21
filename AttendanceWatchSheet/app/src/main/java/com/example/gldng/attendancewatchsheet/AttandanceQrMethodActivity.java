package com.example.gldng.attendancewatchsheet;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class AttandanceQrMethodActivity extends AppCompatActivity implements NavigationMenuActions{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attandance_qrmethod);

        ImageView imageView = findViewById(R.id.qrLoc);
        String text = "abc";
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(text,BarcodeFormat.QR_CODE,200,200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            imageView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }

    }

    public void navMenuSelection(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.navigation_home:
                intent = new Intent(AttandanceQrMethodActivity.this,HomeActivityInstructor.class);
                break;
            case R.id.navigation_courses:
                intent = new Intent(AttandanceQrMethodActivity.this,Courses_Instructor.class);
                break;
            case R.id.navigation_attend:
                intent = new Intent(AttandanceQrMethodActivity.this,AttandanceSelectorActivity.class);
                break;
            case R.id.navigation_calendar:
                //intent = new Intent(HomeActivityInstructor.this,.class);
                break;
        }
        startActivity(intent);
    }

}

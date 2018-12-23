package com.example.gldng.attendancewatchsheet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class AttandanceQrreaderActivity extends AppCompatActivity implements NavigationMenuActions{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attandance_qrreader);

        ImageView imageView = findViewById(R.id.qrReader);

        navBarBuilder();

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


    @Override
    public void navBarBuilder(){
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = null;
                switch (item.getItemId()){
                    case R.id.navigation_home:
                        intent = new Intent(AttandanceQrreaderActivity.this,HomeActivity.class);
                        break;
                    case R.id.navigation_courses:
                        intent = new Intent(AttandanceQrreaderActivity.this,Courses_Student.class);
                        break;
                    case R.id.navigation_scan:
                        return true;
                    case R.id.navigation_calendar:
                        intent = new Intent(AttandanceQrreaderActivity.this,HomeActivity.class);
                        break;
                }
                intent.putExtra("email",getIntent().getStringExtra("email"));
                startActivity(intent);
                return false;
            }
        });
    }
}

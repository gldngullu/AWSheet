package com.example.gldng.attendancewatchsheet;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

public class AddUser extends AppCompatActivity {

    Button addInstructor;
    Button addAssistant;
    Button addAdmin;
    FragmentManager manager;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        addInstructor = findViewById(R.id.addInstructor);
        addAssistant = findViewById(R.id.addAssistant);
        addAdmin = findViewById(R.id.addAdmin);
        manager = getFragmentManager();
        frameLayout = findViewById(R.id.frameLayout);



        addInstructor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addInstructor.setVisibility(View.INVISIBLE);
                addAdmin.setVisibility(View.INVISIBLE);
                addAssistant.setVisibility(View.INVISIBLE);
                addInstructorFragmentAddition(v);
            }
        });
    }



    public void addInstructorFragmentAddition(View v){
        AddInstructor fragment1 = new AddInstructor();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frameLayout,fragment1,"Fragment1");
        transaction.commit();
    }

    public void removeAddInstructorFragment(View v) {
        addInstructor.setVisibility(View.VISIBLE);
        addAdmin.setVisibility(View.VISIBLE);
        addAssistant.setVisibility(View.VISIBLE);
        AddInstructor fragment1 = (AddInstructor) manager.findFragmentByTag("Fragment1");
        FragmentTransaction transaction = manager.beginTransaction();
        if (fragment1 != null) {
            transaction.remove(fragment1);
            transaction.commit();
        } else {
            Toast.makeText(this,"There is no add instuctor fragment here", Toast.LENGTH_LONG).show();
        }
    }
}

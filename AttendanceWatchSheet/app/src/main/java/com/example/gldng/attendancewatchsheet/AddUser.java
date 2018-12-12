package com.example.gldng.attendancewatchsheet;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Build;
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

        addAssistant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addInstructor.setVisibility(View.INVISIBLE);
                addAdmin.setVisibility(View.INVISIBLE);
                addAssistant.setVisibility(View.INVISIBLE);
                addAssistantFragmentAddition(v);
            }
        });

        addAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addInstructor.setVisibility(View.INVISIBLE);
                addAdmin.setVisibility(View.INVISIBLE);
                addAssistant.setVisibility(View.INVISIBLE);
                addAdminFragmentAddition(v);
            }
        });

    }



    public void addInstructorFragmentAddition(View v){
        AddInstructor fragment1 = new AddInstructor();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frameLayout,fragment1,"AddInstructorFragment");
        transaction.commit();
    }
    public void addAssistantFragmentAddition(View v){
        AddAssistant fragment1 = new AddAssistant();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frameLayout,fragment1,"AddAssistantFragment");
        transaction.commit();
    }
    public void addAdminFragmentAddition(View v){
        AddAdmin fragment1 = new AddAdmin();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frameLayout,fragment1,"AddAdminFragment");
        transaction.commit();
    }

    public void alertDialogInstructor(final View v){
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(v.getContext(), android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(v.getContext());
        }
        builder.setTitle("Add Instructor User")
                .setMessage("You will add a New Instructor to the System, Are you sure ? ")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // ADD USER TO DATABASE
                        removeAddInstructorFragment(v);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // RETURN BACK
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void alertDialogAssistant(final View v){
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(v.getContext(), android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(v.getContext());
        }
        builder.setTitle("Add Assistant User")
                .setMessage("You will add a New Assistant to the System, Are you sure ? ")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // ADD USER TO DATABASE
                        removeAddAssistantFragment(v);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // RETURN BACK
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void alertDialogAdmin(final View v){
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(v.getContext(), android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(v.getContext());
        }
        builder.setTitle("Add Admin User")
                .setMessage("You will add a New Admin to the System, Are you sure ? ")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // ADD USER TO DATABASE
                        removeAddAdminFragment(v);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // RETURN BACK
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }



    public void removeAddInstructorFragment(View v) {
        addInstructor.setVisibility(View.VISIBLE);
        addAdmin.setVisibility(View.VISIBLE);
        addAssistant.setVisibility(View.VISIBLE);
        AddInstructor fragment1 = (AddInstructor) manager.findFragmentByTag("AddInstructorFragment");
        FragmentTransaction transaction = manager.beginTransaction();
        if (fragment1 != null) {
            transaction.remove(fragment1);
            transaction.commit();
        } else {
            Toast.makeText(this,"There is no fragment here", Toast.LENGTH_LONG).show();
        }
    }

    public void removeAddAssistantFragment(View v) {
        addInstructor.setVisibility(View.VISIBLE);
        addAdmin.setVisibility(View.VISIBLE);
        addAssistant.setVisibility(View.VISIBLE);
        AddAssistant fragment1 = (AddAssistant) manager.findFragmentByTag("AddAssistantFragment");
        FragmentTransaction transaction = manager.beginTransaction();
        if (fragment1 != null) {
            transaction.remove(fragment1);
            transaction.commit();
        } else {
            Toast.makeText(this,"There is no fragment here", Toast.LENGTH_LONG).show();
        }
    }
    public void removeAddAdminFragment(View v) {
        addInstructor.setVisibility(View.VISIBLE);
        addAdmin.setVisibility(View.VISIBLE);
        addAssistant.setVisibility(View.VISIBLE);
        AddAdmin fragment1 = (AddAdmin) manager.findFragmentByTag("AddAdminFragment");
        FragmentTransaction transaction = manager.beginTransaction();
        if (fragment1 != null) {
            transaction.remove(fragment1);
            transaction.commit();
        } else {
            Toast.makeText(this,"There is no fragment here", Toast.LENGTH_LONG).show();
        }
    }

}

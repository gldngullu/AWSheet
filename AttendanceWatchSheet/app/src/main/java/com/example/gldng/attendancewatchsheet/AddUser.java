package com.example.gldng.attendancewatchsheet;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddUser extends AppCompatActivity implements NavigationMenuActions{

    Button addInstructor;
    Button addAssistant;
    Button addAdmin;
    FragmentManager manager;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        navBarBuilder();

        addInstructor = findViewById(R.id.addInstructor);
        addAssistant = findViewById(R.id.addAssistant);
        addAdmin = findViewById(R.id.addAdmin);
        manager = getFragmentManager();
        frameLayout = findViewById(R.id.frameLayout);
    }

    public void FragmentOpener(View v) {
        addInstructor.setVisibility(View.INVISIBLE);
        addAdmin.setVisibility(View.INVISIBLE);
        addAssistant.setVisibility(View.INVISIBLE);
        int button = v.getId();
        System.out.println(button);
        FragmentAdder(button);
    }

    public void FragmentAdder(int userType) {
        FragmentTransaction transaction = manager.beginTransaction();
        switch (userType) {
            case R.id.addInstructor:
                AddInstructor fragment1 = new AddInstructor();
                transaction.add(R.id.frameLayout, fragment1, "AddInstructorFragment");
                break;
            case R.id.addAssistant:
                AddAssistant fragment2 = new AddAssistant();
                transaction.add(R.id.frameLayout, fragment2, "AddAssistantFragment");
                break;
            case R.id.addAdmin:
                AddAdmin fragment3 = new AddAdmin();
                transaction.add(R.id.frameLayout, fragment3, "AddAdminFragment");
                break;
        }
        transaction.commit();
    }

    public void alertDialog(final View v) {
        int buttonId = v.getId();
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(v.getContext(), android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
        switch (buttonId) {
            case R.id.doneButtonInstructor:
                builder.setTitle("Add Instructor User")
                        .setMessage("You will add a New Instructor to the System, Are you sure ? ")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // ADD USER TO DATABASE
                                Log.d("aa",v.toString());
                                removeFragment(-1);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // RETURN BACK
                            }
                        });
                break;
            case R.id.doneButtonAssistant:
                builder = new AlertDialog.Builder(v.getContext(), android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
                builder.setTitle("Add Assistant User")
                        .setMessage("You will add a New Assistant to the System, Are you sure ? ")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // ADD USER TO DATABASE
                                removeFragment(-2);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // RETURN BACK
                            }
                        });
                break;
            case R.id.doneButtonAdmin:
                builder.setTitle("Add Admin User")
                        .setMessage("You will add a New Admin to the System, Are you sure ? ")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // ADD USER TO DATABASE
                                removeFragment(-3);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // RETURN BACK
                            }
                        });
                break;
        }
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.show();
    }

    public void removeFragment(View v){
        int id = v.getId();
        FragmentTransaction transaction = manager.beginTransaction();
        switch (id){
            case R.id.cancelInstructorAdditionButton:
                AddInstructor fragment1 = (AddInstructor) manager.findFragmentByTag("AddInstructorFragment");
                if (fragment1 != null) {
                    transaction.remove(fragment1);
                    transaction.commit();
                } else {
                    Toast.makeText(this, "There is no fragment here", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.cancelAssistantAdditionButton:
                AddAssistant fragment2 = (AddAssistant) manager.findFragmentByTag("AddAssistantFragment");
                if (fragment2 != null) {
                    transaction.remove(fragment2);
                    transaction.commit();
                } else {
                    Toast.makeText(this, "There is no fragment here", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.cancelAdminAdditionButton:
                AddAdmin fragment3 = (AddAdmin) manager.findFragmentByTag("AddAdminFragment");
                if (fragment3 != null) {
                    transaction.remove(fragment3);
                    transaction.commit();
                } else {
                    Toast.makeText(this, "There is no fragment here", Toast.LENGTH_LONG).show();
                }
                break;
        }
        addInstructor.setVisibility(View.VISIBLE);
        addAdmin.setVisibility(View.VISIBLE);
        addAssistant.setVisibility(View.VISIBLE);
    }

    public void removeFragment(int id){
        FragmentTransaction transaction = manager.beginTransaction();
        switch (id){
            case -1:
                AddInstructor fragment1 = (AddInstructor) manager.findFragmentByTag("AddInstructorFragment");
                if (fragment1 != null) {
                    transaction.remove(fragment1);
                    transaction.commit();
                } else {
                    Toast.makeText(this, "There is no fragment here", Toast.LENGTH_LONG).show();
                }
                break;
            case -2:
                AddAssistant fragment2 = (AddAssistant) manager.findFragmentByTag("AddAssistantFragment");
                if (fragment2 != null) {
                    transaction.remove(fragment2);
                    transaction.commit();
                } else {
                    Toast.makeText(this, "There is no fragment here", Toast.LENGTH_LONG).show();
                }
                break;
            case -3:
                AddAdmin fragment3 = (AddAdmin) manager.findFragmentByTag("AddAdminFragment");
                if (fragment3 != null) {
                    transaction.remove(fragment3);
                    transaction.commit();
                } else {
                    Toast.makeText(this, "There is no fragment here", Toast.LENGTH_LONG).show();
                }
                break;
        }
        addInstructor.setVisibility(View.VISIBLE);
        addAdmin.setVisibility(View.VISIBLE);
        addAssistant.setVisibility(View.VISIBLE);
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
                        intent = new Intent(AddUser.this,HomeActivityAdmin.class);
                        break;
                    case R.id.navigation_courses:
                        intent = new Intent(AddUser.this,AdminCoursePage.class);
                        break;
                    case R.id.navigation_member:
                        return false;
                }
                intent.putExtra("email",getIntent().getStringExtra("email"));
                startActivity(intent);
                return true;
            }
        });
    }

}

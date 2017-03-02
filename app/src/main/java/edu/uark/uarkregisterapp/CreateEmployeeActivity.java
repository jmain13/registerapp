package edu.uark.uarkregisterapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import edu.uark.uarkregisterapp.models.transition.ProductTransition;

public class CreateEmployeeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_employee);
    }

    public void createUserProfileButtonOnClick(View view) {

        // TO DO - Store user info on database

        // TO DO - Display a message on success?

        // Open home screen
        this.startActivity(new Intent(getApplicationContext(), HomeScreenActivity.class));
    }

}
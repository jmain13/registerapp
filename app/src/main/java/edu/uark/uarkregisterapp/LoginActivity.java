package edu.uark.uarkregisterapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TO DO - Query the server here to determine if employees exist in the database
        // TO DO - If no employees exist in database, open create employee screen

        // Open login screen
        setContentView(R.layout.activity_login);
    }

    public void loginButtonOnClick(View view) {

        // TO DO - Check the database for valid employee login here
        // If valid employee login, then open home screen
        this.startActivity(new Intent(getApplicationContext(), HomeScreenActivity.class));

        // TO DO - If invalid employee login, notify user to try again
    }

}

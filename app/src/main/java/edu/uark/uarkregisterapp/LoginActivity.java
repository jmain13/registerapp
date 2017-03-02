package edu.uark.uarkregisterapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Query the server here to determine if employees exist in the database?

        super.onCreate(savedInstanceState); // Not sure what this does
        setContentView(R.layout.activity_login);    // Loads the XML (UI) file
    }

    public void loginButtonOnClick(View view) {

        // Check the database for valid employee login here?

        this.startActivity(new Intent(getApplicationContext(), HomeScreenActivity.class));
    }

}

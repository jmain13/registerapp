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
        this.startActivity(new Intent(getApplicationContext(), LandingActivity.class));
        // This doesn't seem to work right now
        // Check the database for valid employee login here?
    }

/*  // Stuff from ActivityLanding.java
    public void createProductButtonOnClick(View view) {
        Intent intent = new Intent(getApplicationContext(), ProductViewActivity.class);

        intent.putExtra(
                getString(R.string.intent_extra_product),
                new ProductTransition()
        );

        this.startActivity(intent);
    }
*/  // Not sure what this commented chunk does
}

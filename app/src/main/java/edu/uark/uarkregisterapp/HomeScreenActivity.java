package edu.uark.uarkregisterapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import edu.uark.uarkregisterapp.models.transition.ProductTransition;

public class HomeScreenActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_screen);
	}

	// Start Transaction Button
	public void startTransactionButtonOnClick(View view) {
		// Popup message
	}

	// Create Employee Button
	public void createEmployeeButtonOnClick(View view) {
		// Popup message
	}

	// Sales Report: Product Button
	public void viewSalesReportProductButtonOnClick(View view) {
		// Popup message
	}

	// Sales Report: Cashier Button
	public void viewSalesReportCashierButtonOnClick(View view) {
		// Popup message
	}

	// Log Out Button
	public void logoutButtonOnClick(View view) {
		this.startActivity(new Intent(getApplicationContext(), LoginActivity.class));
	}

}

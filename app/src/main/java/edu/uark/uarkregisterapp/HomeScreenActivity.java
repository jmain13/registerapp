package edu.uark.uarkregisterapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
		this.functionalityNotAvailableDialog(view);
	}

	// Create Employee Button
	public void createEmployeeButtonOnClick(View view) {
		this.startActivity(new Intent(getApplicationContext(), CreateEmployeeActivity.class));
	}

	// Sales Report: Product Button
	public void viewSalesReportProductButtonOnClick(View view) {
		this.functionalityNotAvailableDialog(view);
	}

	// Sales Report: Cashier Button
	public void viewSalesReportCashierButtonOnClick(View view) {
		this.functionalityNotAvailableDialog(view);
	}

	// Log Out Button
	public void logoutButtonOnClick(View view) {
		this.startActivity(new Intent(getApplicationContext(), LoginActivity.class));
	}

	// Functionality Not Available Dialog
	public void functionalityNotAvailableDialog(View view) {
		new AlertDialog.Builder(this).
				setTitle(R.string.alert_title_functionality_not_available).
				setMessage(R.string.alert_functionality_not_available).
				setPositiveButton(
						R.string.button_dismiss,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.dismiss();
							}
						}
				).
				create().
				show();
	}
}

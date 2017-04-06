package edu.uark.uarkregisterapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import edu.uark.uarkregisterapp.models.transition.EmployeeTransition;
import edu.uark.uarkregisterapp.models.transition.ProductTransition;

public class HomeScreenActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_screen);

		this.employeeTransition = this.getIntent().getParcelableExtra(this.getString(R.string.intent_extra_employee));
	}

	@Override
	protected void onStart() {
		super.onStart();
		this.getHomeScreenWelcomeLine1TextView().setText("Welcome " + this.employeeTransition.getFirstName() + " (" + this.employeeTransition.getEmployeeId() + ")!");
	}

	// Start Transaction Button
	public void startTransactionButtonOnClick(View view) {
		this.displayFunctionalityNotAvailableAlert(view);
	}

	// Create Employee Button
	public void createEmployeeButtonOnClick(View view) {
		this.startActivity(new Intent(getApplicationContext(), CreateEmployeeActivity.class));
	}

	// Sales Report: Product Button
	public void viewSalesReportProductButtonOnClick(View view) {
		this.displayFunctionalityNotAvailableAlert(view);
	}

	// Sales Report: Cashier Button
	public void viewSalesReportCashierButtonOnClick(View view) {
		this.displayFunctionalityNotAvailableAlert(view);
	}

	// Log Out Button
	public void logoutButtonOnClick(View view) {
		this.startActivity(new Intent(getApplicationContext(), LoginActivity.class));
	}

	private TextView getHomeScreenWelcomeLine1TextView() {
		return (TextView)this.findViewById(R.id.text_view_home_screen_welcome_line1);
	}

	// Functionality Not Available Dialog
	public void displayFunctionalityNotAvailableAlert(View view) {
		new AlertDialog.Builder(this).
				setMessage(R.string.alert_dialog_functionality_not_available).
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

	private EmployeeTransition employeeTransition;
}

package edu.uark.uarkregisterapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Locale;

import edu.uark.uarkregisterapp.models.api.TransactionEntry;
import edu.uark.uarkregisterapp.models.api.enums.TransactionEntryApiRequestStatus;
import edu.uark.uarkregisterapp.models.api.services.TransactionEntryService;
import edu.uark.uarkregisterapp.models.transition.ProductTransition;
import edu.uark.uarkregisterapp.models.transition.TransactionEntryTransition;

public class ProductViewActivity extends AppCompatActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_view);
		setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

		ActionBar actionBar = this.getSupportActionBar();
		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(true);
		}

		this.productTransition = this.getIntent().getParcelableExtra(this.getString(R.string.intent_extra_product));
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:  // Respond to the action bar's Up/Home button
				this.finish();

				return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onResume() {
		super.onResume();

		this.getProductLookupCodeEditText().setText(this.productTransition.getLookupCode());
		this.getProductQuantityEditText().setText(String.format(Locale.getDefault(), "%d", this.productTransition.getQuantity()));
		this.getProductPriceEditText().setText("$" + String.format(Locale.getDefault(), "%.2f", this.productTransition.getPrice()));
	}

	public void addToTransactionButtonOnClick(View view)  {
		//this.displayFunctionalityNotAvailableDialog();

		if (!this.validateInput()) {
			return;
		}

		this.addToTransactionAlert = new AlertDialog.Builder(this).
				setMessage(R.string.alert_dialog_add_to_transaction).
				create();

		(new AddToTransactionActivityTask(
				this,
				this.getProductLookupCodeEditText().getText().toString(),
				Integer.parseInt(this.getProductDesiredQuantityEditText().getText().toString()),
				Double.parseDouble(this.getProductPriceEditText().getText().toString())
		)).execute();

	}
	/*
	private void displayFunctionalityNotAvailableDialog() {
		new AlertDialog.Builder(this).
				setMessage(R.string.alert_dialog_functionality_not_available).
				setPositiveButton(
						R.string.button_ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.dismiss();
							}
						}
				).
				create().
				show();
	}*/

	private EditText getProductLookupCodeEditText() {
		return (EditText) this.findViewById(R.id.edit_text_product_lookup_code);
	}

	private EditText getProductQuantityEditText() {
		return (EditText) this.findViewById(R.id.edit_text_product_quantity);
	}

	private EditText getProductPriceEditText() {
		return (EditText) this.findViewById(R.id.edit_text_product_price);
	}

	private EditText getProductDesiredQuantityEditText() {
		return (EditText) this.findViewById(R.id.edit_text_product_desired_quantity);
	}


	private class AddToTransactionActivityTask extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {
			TransactionEntry transactionEntry = (new TransactionEntryService()).putTransactionEntry(
				(new TransactionEntry()).
					setId(transactionEntryTransition.getId()).
					setFromTransaction(transactionEntryTransition.getFromTransaction()).
					setLookupCode(this.lookupCode).
					setQuantity(this.desiredQuantity).
					setPrice(this.price)
			);

			// TODO: Check ID, FromTransaction!

			if (transactionEntry.getApiRequestStatus() == TransactionEntryApiRequestStatus.OK) {
				transactionEntryTransition.setLookupCode(this.lookupCode);
				transactionEntryTransition.setQuantity(this.desiredQuantity);
				transactionEntryTransition.setPrice(this.price);
			}

			return (transactionEntry.getApiRequestStatus() == TransactionEntryApiRequestStatus.OK);
		}

		@Override
		protected void onPostExecute(Boolean successfulSave) {
			String message;

			addToTransactionAlert.dismiss();

			if (successfulSave) {
				message = getString(R.string.alert_dialog_add_to_transaction_success);
			} else {
				message = getString(R.string.alert_dialog_add_to_transaction_failure);
			}

			new AlertDialog.Builder(this.activity).
				setMessage(message).
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

		private ProductViewActivity activity;
		private String lookupCode;
		private int desiredQuantity;
		private double price;

		private AddToTransactionActivityTask(ProductViewActivity activity, String lookupCode, int desiredQuantity, double price) {
			this.activity = activity;
			this.lookupCode = lookupCode;
			this.desiredQuantity = desiredQuantity;
			this.price = price;
		}
	}

	private boolean validateInput() {
		boolean inputIsValid = true;
		String validationMessage = StringUtils.EMPTY;

		if (StringUtils.isBlank(this.getProductLookupCodeEditText().getText().toString())) {
			validationMessage = this.getString(R.string.validation_product_lookup_code);
			inputIsValid = false;
		}

		if (!inputIsValid && StringUtils.isBlank(this.getProductQuantityEditText().getText().toString())) {
			validationMessage = this.getString(R.string.validation_product_quantity);
			inputIsValid = false;
		}

		try {
			if (Integer.parseInt(this.getProductQuantityEditText().getText().toString()) < 0) {
				validationMessage = this.getString(R.string.validation_product_quantity);
				inputIsValid = false;
			}
		} catch (NumberFormatException nfe) {
			validationMessage = this.getString(R.string.validation_product_quantity);
			inputIsValid = false;
		}

		try {
			if (Double.parseDouble(this.getProductDesiredQuantityEditText().getText().toString()) < 0) {
				validationMessage = this.getString(R.string.validation_product_desired_quantity);
				inputIsValid = false;
			}
		} catch (NumberFormatException nfe) {
			validationMessage = this.getString(R.string.validation_product_desired_quantity);
			inputIsValid = false;
		}

		if (!inputIsValid) {
			new AlertDialog.Builder(this).
				setMessage(validationMessage).
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

		return inputIsValid;
	}

	private AlertDialog addToTransactionAlert;
	private ProductTransition productTransition;
	private TransactionEntryTransition transactionEntryTransition;
}

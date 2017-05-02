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

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.UUID;

import edu.uark.uarkregisterapp.models.api.TransactionEntry;
import edu.uark.uarkregisterapp.models.api.enums.TransactionEntryApiRequestStatus;
import edu.uark.uarkregisterapp.models.api.services.TransactionEntryService;
import edu.uark.uarkregisterapp.models.transition.ProductTransition;
import edu.uark.uarkregisterapp.models.transition.TransactionEntryTransition;
import edu.uark.uarkregisterapp.models.transition.TransactionTransition;

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
		/*
		this.transactionTransition = this.getIntent().getParcelableExtra("intent_extra_transaction");

		Bundle b = this.getIntent().getExtras();
		ArrayList<TransactionEntry> entries = b.getParcelableArrayList(getString(R.string.intent_extra_transaction_entries));
		this.transactionTransition.setTransactionEntries(entries);
		*/
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
		this.getProductPriceEditText().setText(String.format(Locale.getDefault(), "%.2f", this.productTransition.getPrice()));
		/*
		this.transactionTransition = this.getIntent().getParcelableExtra("intent_extra_transaction");

		Bundle b = this.getIntent().getExtras();
		ArrayList<TransactionEntry> entries = b.getParcelableArrayList(getString(R.string.intent_extra_transaction_entries));
		this.transactionTransition.setTransactionEntries(entries);
		*/
	}

	public void addToTransactionButtonOnClick(View view) {
		if (!this.validateInput()) {
			return;
		}

		this.displayFunctionalityNotAvailableDialog();

		/*
		if(this.transactionTransition.getTransactionEntries() == null) {
			this.transactionTransition.setTransactionEntries(new ArrayList<TransactionEntry>());
		}

		this.transactionTransition.addTransactionEntry(new TransactionEntry().
				setFromTransaction(this.transactionTransition.getId()).
				setLookupCode(this.getProductLookupCodeEditText().getText().toString()).
				setPrice(Double.parseDouble(this.getProductPriceEditText().getText().toString())).
				setQuantity(Integer.parseInt(this.getProductDesiredQuantityEditText().getText().toString()))
		);

		Intent intent = new Intent(getApplicationContext(), StartTransactionActivity.class);

		intent.putExtra(
				getString(R.string.intent_extra_transaction),
				transactionTransition
		);

		ArrayList<TransactionEntry> entries = new ArrayList<TransactionEntry>();
		Bundle b = new Bundle();
		b.putParcelableArrayList(
				getString(R.string.intent_extra_transaction_entries),
				transactionTransition.getTransactionEntries()
		);
		intent.putExtras(b);


		startActivity(intent);*/
	}

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
	}

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
	private TransactionTransition transactionTransition;
	private TransactionEntryTransition transactionEntryTransition;
}

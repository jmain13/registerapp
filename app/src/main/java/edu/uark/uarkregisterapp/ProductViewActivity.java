package edu.uark.uarkregisterapp;

import android.content.DialogInterface;
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

import edu.uark.uarkregisterapp.models.api.Product;
import edu.uark.uarkregisterapp.models.api.enums.ProductApiRequestStatus;
import edu.uark.uarkregisterapp.models.api.services.ProductService;
import edu.uark.uarkregisterapp.models.transition.ProductTransition;

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
		this.getProductCreatedOnEditText().setText(
			(new SimpleDateFormat("MM/dd/yyyy", Locale.US)).format(this.productTransition.getCreatedOn())
		);
	}

	public void saveButtonOnClick(View view) {
		if (!this.validateInput()) {
			return;
		}

		this.savingProductAlert = new AlertDialog.Builder(this).
			setMessage(R.string.alert_dialog_product_save).
			create();

		(new SaveActivityTask(
			this,
			this.getProductLookupCodeEditText().getText().toString(),
			Integer.parseInt(this.getProductQuantityEditText().getText().toString())
		)).execute();
	}

	private EditText getProductLookupCodeEditText() {
		return (EditText) this.findViewById(R.id.edit_text_product_lookup_code);
	}

	private EditText getProductQuantityEditText() {
		return (EditText) this.findViewById(R.id.edit_text_product_quantity);
	}

	private EditText getProductCreatedOnEditText() {
		return (EditText) this.findViewById(R.id.edit_text_product_price);
	}

	private class SaveActivityTask extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {
			Product product = (new ProductService()).putProduct(
				(new Product()).
					setId(productTransition.getId()).
					setLookupCode(this.lookupCode).
					setQuantity(this.quantity)
			);

			if (product.getApiRequestStatus() == ProductApiRequestStatus.OK) {
				productTransition.setQuantity(this.quantity);
				productTransition.setLookupCode(this.lookupCode);
			}

			return (product.getApiRequestStatus() == ProductApiRequestStatus.OK);
		}

		@Override
		protected void onPostExecute(Boolean successfulSave) {
			String message;

			savingProductAlert.dismiss();

			if (successfulSave) {
				message = getString(R.string.alert_dialog_product_save_success);
			} else {
				message = getString(R.string.alert_dialog_product_save_failure);
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

		private int quantity;
		private String lookupCode;
		private ProductViewActivity activity;

		private SaveActivityTask(ProductViewActivity activity, String lookupCode, int quantity) {
			this.quantity = quantity;
			this.activity = activity;
			this.lookupCode = lookupCode;
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

	private AlertDialog savingProductAlert;
	private ProductTransition productTransition;
}

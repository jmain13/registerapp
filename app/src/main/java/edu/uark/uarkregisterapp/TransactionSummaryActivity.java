package edu.uark.uarkregisterapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.text.DecimalFormat;

import edu.uark.uarkregisterapp.adapters.ProductListAdapter;
import edu.uark.uarkregisterapp.models.api.Product;
import edu.uark.uarkregisterapp.models.api.services.ProductService;
import edu.uark.uarkregisterapp.models.transition.ProductTransition;

public class TransactionSummaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_summary);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        this.products = new ArrayList<>();
        this.productListAdapter = new ProductListAdapter(this, this.products);

        this.getProductsListView().setAdapter(this.productListAdapter);
        this.getProductsListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ProductViewActivity.class);

                intent.putExtra(
                        getString(R.string.intent_extra_product),
                        new ProductTransition((Product) getProductsListView().getItemAtPosition(position))
                );

                startActivity(intent);
            }
        });

        this.loadingTransactionSummaryAlert = new AlertDialog.Builder(this).
                setMessage(R.string.alert_dialog_transaction_summary_loading).
                create();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void checkOutButtonOnClick(View view) {
        this.displayFunctionalityNotAvailableDialog();
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

    @Override
    protected void onResume() {
        super.onResume();

        this.loadingTransactionSummaryAlert.show();
        (new TransactionSummaryActivity.RetrieveProductsTask()).execute();
    }

    private ListView getProductsListView() {
        return (ListView) this.findViewById(R.id.list_view_products);
    }

    private class RetrieveProductsTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            products.clear();
            products.addAll(
                    (new ProductService()).getProducts()
            );

            return null;
        }

        @Override
        protected void onPostExecute(Void param) {
            productListAdapter.notifyDataSetChanged();
            getTotalPrice();
            displayTotalPrice();
            loadingTransactionSummaryAlert.dismiss();
        }


    }

    private void getTotalPrice() {
        totalPrice = 0.00;
        for (int i=0; i < products.size(); i++) {
            Product p = products.get(i);
            totalPrice = totalPrice + p.getPrice();
        }
    }

    private void displayTotalPrice() {
        DecimalFormat df = new DecimalFormat();
        df.setMinimumFractionDigits(2);
        getTotalPriceTextView().setText("Total Price: $" + df.format(totalPrice));
    }


    private TextView getTotalPriceTextView() {
        return (TextView)this.findViewById(R.id.textView_total_price);
    }

    private List<Product> products;
    private AlertDialog loadingTransactionSummaryAlert;
    private ProductListAdapter productListAdapter;
    private double totalPrice;
}

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

import edu.uark.uarkregisterapp.adapters.TransactionListAdapter;
import edu.uark.uarkregisterapp.models.api.TransactionEntry;
import edu.uark.uarkregisterapp.models.api.services.TransactionEntryService;
import edu.uark.uarkregisterapp.models.transition.TransactionEntryTransition;

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

        this.entries = new ArrayList<>();
        this.transactionListAdapter = new TransactionListAdapter(this, this.entries);

        this.getTransactionEntriesListView().setAdapter(this.transactionListAdapter);
        this.getTransactionEntriesListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), TransactionEntryViewActivity.class);

                intent.putExtra(
                        getString(R.string.intent_extra_product),
                        new TransactionEntryTransition((TransactionEntry) getTransactionEntriesListView().getItemAtPosition(position))
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
        (new RetrieveTransactionEntriesTask()).execute();
    }

    private ListView getTransactionEntriesListView() {
        return (ListView) this.findViewById(R.id.list_view_transaction_entries);
    }

    private class RetrieveTransactionEntriesTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            entries.clear();
            entries.addAll(
                    (new TransactionEntryService()).getTransactionEntries()
            );

            return null;
        }

        @Override
        protected void onPostExecute(Void param) {
            transactionListAdapter.notifyDataSetChanged();
            getTotalPrice();
            displayTotalPrice();
            loadingTransactionSummaryAlert.dismiss();
        }


    }

    private void getTotalPrice() {
        totalPrice = 0.00;
        for (int i=0; i < entries.size(); i++) {
            TransactionEntry e = entries.get(i);
            totalPrice = totalPrice + e.getPrice();
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

    private List<TransactionEntry> entries;
    private AlertDialog loadingTransactionSummaryAlert;
    private TransactionListAdapter transactionListAdapter;
    private double totalPrice;
}

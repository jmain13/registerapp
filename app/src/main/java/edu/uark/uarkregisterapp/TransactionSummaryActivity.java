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

import edu.uark.uarkregisterapp.adapters.TransactionEntryListAdapter;
import edu.uark.uarkregisterapp.models.api.TransactionEntry;
import edu.uark.uarkregisterapp.models.api.services.TransactionService;
import edu.uark.uarkregisterapp.models.transition.TransactionTransition;
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

        this.transactionTransition = this.getIntent().getParcelableExtra(this.getString(R.string.intent_extra_transaction));

        ArrayList<TransactionEntry> my_entries = this.transactionTransition.getTransactionEntries();
        for(int i = 0; i < this.transactionTransition.getTransactionEntries().size(); i++) {
            TransactionEntry e = new TransactionEntry(my_entries.get(i));
            System.out.println("UUID: " + e.getId().toString());
            System.out.println("FromTransaction: " + e.getFromTransaction().toString());
            System.out.println("lookupcode: " + e.getLookupCode());
            System.out.println("quantity: " + e.getQuantity());
        }
        System.out.println("Entries: " + this.transactionTransition.getTransactionEntries().toString());

        this.transactionEntryListAdapter = new TransactionEntryListAdapter(this, this.transactionTransition.getTransactionEntries());



        this.getTransactionEntriesListView().setAdapter(this.transactionEntryListAdapter);
        this.getTransactionEntriesListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), TransactionEntryViewActivity.class);

                intent.putExtra(
                        getString(R.string.intent_extra_transaction_entry),
                        new TransactionEntryTransition((TransactionEntry) getTransactionEntriesListView().getItemAtPosition(position))
                );

                intent.putExtra(
                        getString(R.string.intent_extra_transaction),
                        transactionTransition
                );

                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() { super.onStart(); }

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
    protected void onResume() { super.onResume(); }

    private ListView getTransactionEntriesListView() {
        return (ListView) this.findViewById(R.id.list_view_transaction_entries);
    }

    private List<TransactionEntry> entries;
    private TransactionEntryListAdapter transactionEntryListAdapter;
    private TransactionTransition transactionTransition;
}

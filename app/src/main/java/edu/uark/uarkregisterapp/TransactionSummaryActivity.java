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
        /*
        this.transactionTransition = this.getIntent().getParcelableExtra("intent_extra_transaction");

        Bundle b = this.getIntent().getExtras();
        ArrayList<TransactionEntry> entries = b.getParcelableArrayList(getString(R.string.intent_extra_transaction_entries));
        this.transactionTransition.setTransactionEntries(entries);
        */

        //ArrayList<TransactionEntry> my_entries = this.transactionTransition.getTransactionEntries();

        TransactionEntry entry1 = new TransactionEntry().
                setLookupCode("lookupcode100").
                setQuantity(7).
                setPrice(7.77);
        TransactionEntry entry2 = new TransactionEntry().
                setLookupCode("lookupcode101").
                setQuantity(12).
                setPrice(12.00);
        TransactionEntry entry3 = new TransactionEntry().
                setLookupCode("lookupcode103").
                setQuantity(40).
                setPrice(0.40);
        ArrayList<TransactionEntry> my_entries = new ArrayList<TransactionEntry>();
        my_entries.add(entry1);
        my_entries.add(entry2);
        my_entries.add(entry3);

        this.transactionEntryListAdapter = new TransactionEntryListAdapter(this, my_entries);

        this.getTransactionEntriesListView().setAdapter(this.transactionEntryListAdapter);

        /*
        this.getTransactionEntriesListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                this.displayFunctionalityNotAvailableDialog();


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
        });*/
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

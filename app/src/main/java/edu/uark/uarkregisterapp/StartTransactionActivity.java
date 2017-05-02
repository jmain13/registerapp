package edu.uark.uarkregisterapp;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import edu.uark.uarkregisterapp.adapters.ProductListAdapter;
import edu.uark.uarkregisterapp.models.api.Product;
import edu.uark.uarkregisterapp.models.api.services.ProductService;
import edu.uark.uarkregisterapp.models.transition.ProductTransition;

import edu.uark.uarkregisterapp.adapters.TransactionListAdapter;
import edu.uark.uarkregisterapp.models.api.Transaction;
import edu.uark.uarkregisterapp.models.api.services.TransactionService;
import edu.uark.uarkregisterapp.models.transition.TransactionTransition;
import edu.uark.uarkregisterapp.models.api.enums.TransactionApiRequestStatus;

import java.util.ArrayList;
import java.util.List;

public class StartTransactionActivity extends AppCompatActivity {

    ListView lv;
    SearchView sv;

    String[] products = {"LookUpCode1", "LookUpCode2", "LookUpCode3", "LookUpCode4", "LookUpCode5", "LookUpCode6", "LookUpCode7", "LookUpCode8", "LookUpCode9", "LookUpCode10", "LookUpCode11"};
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_transaction);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }

        lv = (ListView) findViewById(R.id.list_view_products);
        sv = (SearchView) findViewById(R.id.searchView_search_products);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, products);
        lv.setAdapter(adapter);

        sv.setOnQueryTextListener(new OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });

        this.startTransactionAlert = new AlertDialog.Builder(this).
                setMessage(R.string.alert_dialog_add_to_transaction).
                create();

        (new StartTransactionActivityTask(
                this
        )).execute();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void viewTransactionButtonOnClick(View view) {
        this.startActivity(new Intent(getApplicationContext(), TransactionSummaryActivity.class));
    }

    private class StartTransactionActivityTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            Transaction transaction = (new TransactionService()).putTransaction(
                    (new Transaction())
            );

            if (transaction.getApiRequestStatus() == TransactionApiRequestStatus.OK) {
            }

            return (transaction.getApiRequestStatus() == TransactionApiRequestStatus.OK);
        }

        @Override
        protected void onPostExecute(Boolean successfulSave) {
            String message;

            startTransactionAlert.dismiss();

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

        private StartTransactionActivity activity;

        private StartTransactionActivityTask(StartTransactionActivity activity) {
            this.activity = activity;
        }
    }

    private AlertDialog startTransactionAlert;
}

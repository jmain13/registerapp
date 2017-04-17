package edu.uark.uarkregisterapp;
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

import java.util.ArrayList;
import java.util.List;

import edu.uark.uarkregisterapp.adapters.ProductListAdapter;

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
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void viewCartButtonOnClick(View view) {
        this.startActivity(new Intent(getApplicationContext(), TransactionSummaryActivity.class));
    }
}

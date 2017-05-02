package edu.uark.uarkregisterapp;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.widget.AdapterView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_transaction);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }

        this.products = new ArrayList<Product>();

        lv = getProductsListView();
        sv = (SearchView) findViewById(R.id.searchView_search_products);

        productListAdapter = new ProductListAdapter(this, products);
        lv.setAdapter(productListAdapter);

        sv.setOnQueryTextListener(new OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                productListAdapter.getFilter().filter(s);
                return false;
            }
        });

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

        this.loadingProductsAlert = new AlertDialog.Builder(this).
                setMessage(R.string.alert_dialog_products_loading).
                create();
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.loadingProductsAlert.show();
        (new StartTransactionActivity.RetrieveProductsTask()).execute();
    }

    public void viewCartButtonOnClick(View view) {
        this.startActivity(new Intent(getApplicationContext(), TransactionSummaryActivity.class));
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
            loadingProductsAlert.dismiss();
        }
    }

    private List<Product> products;
    private AlertDialog loadingProductsAlert;
    private ProductListAdapter productListAdapter;
}

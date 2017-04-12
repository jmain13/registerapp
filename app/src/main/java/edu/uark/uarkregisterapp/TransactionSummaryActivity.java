package edu.uark.uarkregisterapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class TransactionSummaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_summary);
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
}

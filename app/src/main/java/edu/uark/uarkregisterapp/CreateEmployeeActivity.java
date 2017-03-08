package edu.uark.uarkregisterapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Locale;

import edu.uark.uarkregisterapp.models.api.Employee;
import edu.uark.uarkregisterapp.models.api.enums.EmployeeApiRequestStatus;
import edu.uark.uarkregisterapp.models.api.services.EmployeeService;
import edu.uark.uarkregisterapp.models.transition.EmployeeTransition;

// Modeled from ProductViewActivity.java

public class CreateEmployeeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_employee);

        this.employeeTransition = this.getIntent().getParcelableExtra(this.getString(R.string.intent_extra_employee));
    }

    @Override
    protected void onResume() {
        super.onResume();

        this.getFirstNameEditText().setText(this.employeeTransition.getFirstName());
        this.getLastNameEditText().setText(this.employeeTransition.getLastName());
        this.getPasswordEditText().setText(this.employeeTransition.getPassword());
    }


    public void createUserProfileButtonOnClick(View view) {
        if (!this.validateInput()) {
            return;
        }

        this.savingEmployeeAlert = new AlertDialog.Builder(this).
                setMessage(R.string.alert_dialog_product_save).
                create();

        (new CreateEmployeeActivity.SaveActivityTask(
                this,
                this.getFirstNameEditText().getText().toString(),
                this.getLastNameEditText().getText().toString(),
                this.getPasswordEditText().getText().toString()
        )).execute();

        // Open home screen
        this.startActivity(new Intent(getApplicationContext(), HomeScreenActivity.class));
    }

    private EditText getFirstNameEditText() {
        return (EditText) this.findViewById(R.id.text_edit_create_employee_first_name);
    }

    private EditText getLastNameEditText() {
        return (EditText) this.findViewById(R.id.text_edit_create_employee_last_name);
    }

    private EditText getPasswordEditText() {
        return (EditText) this.findViewById(R.id.text_edit_create_employee_password);
    }

    private class SaveActivityTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            Employee employee = (new EmployeeService()).putEmployee(
                    (new Employee()).
                            setId(employeeTransition.getId()).
                            setFirstName(this.firstName).
                            setLastName(this.lastName).
                            setPassword(this.password)
            );

            if (employee.getApiRequestStatus() == EmployeeApiRequestStatus.OK) {
                employeeTransition.setFirstName(this.firstName);
                employeeTransition.setLastName(this.lastName);
                employeeTransition.setPassword(this.password);
            }

            return (employee.getApiRequestStatus() == EmployeeApiRequestStatus.OK);
        }

        @Override
        protected void onPostExecute(Boolean successfulSave) {
            String message;

            savingEmployeeAlert.dismiss();

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

        private String firstName;
        private String lastName;
        private String password;
        private CreateEmployeeActivity activity;

        private SaveActivityTask(CreateEmployeeActivity activity, String firstName, String lastName, String password) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.password = password;
        }
    }

    private boolean validateInput() {
        boolean inputIsValid = true;
        String validationMessage = StringUtils.EMPTY;

        if (StringUtils.isBlank(this.getFirstNameEditText().getText().toString())) {
            validationMessage = this.getString(R.string.validation_employee_first_name);
            inputIsValid = false;
        }

        if (StringUtils.isBlank(this.getLastNameEditText().getText().toString())) {
            validationMessage = this.getString(R.string.validation_employee_last_name);
            inputIsValid = false;
        }

        if (StringUtils.isBlank(this.getPasswordEditText().getText().toString())) {
            validationMessage = this.getString(R.string.validation_employee_password);
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

    private AlertDialog savingEmployeeAlert;
    private EmployeeTransition employeeTransition;
}

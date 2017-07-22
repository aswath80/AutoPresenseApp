package com.m2e.cs5540.autopresence.register;

import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.m2e.cs5540.autopresence.R;
import com.m2e.cs5540.autopresence.base.AsyncLoaderStatus;
import com.m2e.cs5540.autopresence.base.BaseActivity;
import com.m2e.cs5540.autopresence.login.LoginActivity;

public class RegisterActivity extends BaseActivity implements View.OnClickListener,
        LoaderManager.LoaderCallbacks<AsyncLoaderStatus>{

    private static final String TAG = RegisterActivity.class.getName();

    private EditText nameText;
    private EditText emailText;
    private EditText cinText;
    private EditText passwordText;

    private Spinner  typeText;
    private ArrayAdapter<CharSequence> spinnerAdapter;

    private Button   submitButton;
    private TextView alreadyMemberLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d(TAG, "$$$$$$ onCreate() Invoked... ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        this.nameText       = (EditText) findViewById(R.id.userfullnameText);
        this.emailText      = (EditText) findViewById(R.id.email);
        this.cinText        = (EditText) findViewById(R.id.cin);
        this.passwordText   = (EditText) findViewById(R.id.password);

        this.typeText   = (Spinner) findViewById(R.id.userType);
        spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.userType_array, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeText.setAdapter(spinnerAdapter);

        this.submitButton = (Button)findViewById(R.id.btn_signup);
        submitButton.setOnClickListener(this);

        this.alreadyMemberLogin = (TextView) findViewById(R.id.link_login);
        alreadyMemberLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    private void signup() {

        Log.d(TAG, "Signup");
        submitButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this, R.style.Theme_AppCompat_Light_DarkActionBar);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        // TODO: Implementing your own signup logic here.
        getLoaderManager().initLoader(0, null, this);

        new android.os.Handler().postDelayed(
            new Runnable() {
            public void run() {
                // On complete call either onSignupSuccess or onSignupFailed
                // depending on success
                //onSignupSuccess();
                // onSignupFailed();
                progressDialog.dismiss();
            }
        }, 3000);
        progressDialog.hide();
    }

    @Override
    public Loader<AsyncLoaderStatus> onCreateLoader(int id, Bundle args) {
        Log.d(TAG, "$$$$$$ RegisterActivity.onCreateLoader called");
        return new RegisterAsyncTaskLoader(this, nameText, emailText, cinText, passwordText,
                typeText.getSelectedItem().toString());
    }

    @Override
    public void onLoadFinished(Loader<AsyncLoaderStatus> loader, AsyncLoaderStatus loaderStatus) {

        Log.d(TAG, "$$$$$$ RegisterActivity.onLoadFinished called");
        if (loaderStatus.hasException()) {
            Toast.makeText(this, "Error " + loaderStatus.getExceptionMessage(),
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "You have been registered successfully, need to show on landing for professor/Student.",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLoaderReset(Loader<AsyncLoaderStatus> loader) {

    }

    @Override
    public void onClick(View v) {
        signup();
    }
}
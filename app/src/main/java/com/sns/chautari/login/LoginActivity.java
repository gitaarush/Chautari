package com.sns.chautari.login;

import android.app.ProgressDialog;
import android.content.Intent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.sns.chautari.BuildConfig;
import com.sns.chautari.R;
import com.sns.chautari.common.Constants;
import com.sns.chautari.common.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * LoginActivity ONLY knows how to display views and sending events and data to the presenter
 * LoginActivity doesn't know anything about the model/repository
 */
public class LoginActivity extends AppCompatActivity implements LoginContract.ILoginView {

    LoginContract.ILoginPresenter loginPresenter;
    ProgressDialog progressDialog;
    private Unbinder unbinder;

    // UI references.
    @BindView(R.id.email)
    EditText mEmailView;

    @BindView(R.id.password)
    EditText mPasswordView;

    @BindView(R.id.login_form)
    View mLoginFormView;

    View focusView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        unbinder = ButterKnife.bind(this);

        // Temporary setting for debugging
        if (BuildConfig.DEBUG) {
            mEmailView.setText(Constants.DUMMY_USER_NAME);
            mPasswordView.setText(Constants.DUMMY_PASSWORD);
        }

        loginPresenter = new LoginPresenter(this);
    }

    @OnClick(R.id.email_sign_in_button)
    void onSignInButtonClick() {
        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        if (isLoginInputsValid(email, password)) {
            showLoginProgress(true);
            loginPresenter.attemptLogin(email, password);
        } else {
            focusView.requestFocus();
        }
    }

    private boolean isLoginInputsValid(String email, String password) {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        boolean isValid = true;
        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            isValid = false;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            isValid = false;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            isValid = false;
        }
        return isValid;
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    public void showLoginProgress(final boolean show) {

        //OPTION 1: Shows the progress UI and hides the login form.
//        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//        mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);

        //OPTION 2: Shows the progress dialog
        if (show) {
            progressDialog = ProgressDialog.show(this, "Authenticating...", null);
        } else {
            progressDialog.dismiss();
        }
    }

    @Override
    public void openDashboardActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("username", mEmailView.getText().toString());
        startActivity(intent);
    }

    @Override
    public void onLoginSuccess() {
        //Open dashboard
        showLoginProgress(false);
        Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
        openDashboardActivity();
    }

    @Override
    public void onLoginFailure() {
        showLoginProgress(false);
        mEmailView.requestFocus();
        Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}


package com.sns.chautari.login;

import android.os.AsyncTask;

import com.sns.chautari.common.Constants;

public class LoginPresenter implements LoginContract.ILoginPresenter {

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private LoginTask mAuthTask = null;
    private LoginContract.ILoginView loginActivity;


    LoginPresenter(LoginContract.ILoginView loginActivity) {

        this.loginActivity = loginActivity;
    }

    @Override
    public void attemptLogin(String username, String password) {
        mAuthTask = new LoginTask(username, password);
        mAuthTask.execute((Void) null);
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class LoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        LoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            if (Constants.DUMMY_USER_NAME.equals(mEmail)) {
                // Account exists, return true if the password matches.
                return Constants.DUMMY_PASSWORD.equals(mPassword);
            }

            // TODO: register the new account here.
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            if (success) {
                loginActivity.onLoginSuccess();
            } else {
                loginActivity.onLoginFailure();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            loginActivity.onLoginFailure();
        }
    }
}

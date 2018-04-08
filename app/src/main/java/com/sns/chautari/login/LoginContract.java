package com.sns.chautari.login;

public interface LoginContract {

    interface ILoginView {
        void onLoginSuccess();
        void onLoginFailure();
        void showLoginProgress(boolean show);
        void openDashboardActivity();
    }

    interface ILoginPresenter {
        void attemptLogin(String username, String password);
    }

//    interface ILoginRepository {
//        //TODO: Will be implemented later
//    }
}

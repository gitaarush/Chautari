package com.sns.chautari.login;

public interface LoginContract {

    public interface ILoginView {
        void onLoginSuccess();
        void onLoginFailure();
        void showLoginProgress(boolean show);
    }

    public interface ILoginPresenter {
        void attemptLogin(String username, String password);
    }

    public interface ILoginRepository {
        //TODO: Will be implemented later
    }
}

package istiak.com.rxjavaretrofitsampleapp.Registration;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import javax.inject.Inject;

import istiak.com.rxjavaretrofitsampleapp.location.LocationGetActivity;
import istiak.com.rxjavaretrofitsampleapp.model.User;
import istiak.com.rxjavaretrofitsampleapp.network.RegistrationApi;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by slbd on 6/16/17.
 */

public class RegistrationPresenter {
    private RegistrationApi mRegistrationApi;
    private Context mContext;
    private RegistrationView mRegistrationView;

    @Inject
    RegistrationPresenter(Context context,  RegistrationApi registrationApi) {
        this.mContext = context;
        this.mRegistrationApi = registrationApi;
    }

    void setupView(RegistrationView view) {
        this.mRegistrationView = view;
    }

    void attemptRegister(String email, String phoneNumber, String bloodGroup) {

        // Store values at the time of the login attempt.
        mRegistrationView.showProgress(true);

        User user = new User(email,phoneNumber,bloodGroup);
        Observable<User> call = mRegistrationApi.doRegistration(user);
        Subscription subscription = call
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<User>() {
                    @Override
                    public void onCompleted() {
                        mRegistrationView.showProgress(false);
                        Log.d("REGISTRATION","DONE");
                        Toast.makeText(mContext.getApplicationContext(), "Registration Success", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        // cast to retrofit.HttpException to get the response code
                        mRegistrationView.showProgress(false);
                        if (e instanceof HttpException) {
                            HttpException response = (HttpException)e;
                            int code = response.code();
                            Toast.makeText(mContext.getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onNext(User user) {
                        mRegistrationView.showProgress(false);
                        Log.d("USER", user.toString());
                        Toast.makeText(mContext.getApplicationContext(), "Registered User" + user.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

}

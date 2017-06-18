package istiak.com.rxjavaretrofitsampleapp.Registration;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.text.style.SubscriptSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import istiak.com.rxjavaretrofitsampleapp.R;
import istiak.com.rxjavaretrofitsampleapp.RXJavaRetrofitSampleApp;
import istiak.com.rxjavaretrofitsampleapp.model.User;
import istiak.com.rxjavaretrofitsampleapp.network.RegistrationApi;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

import static android.Manifest.permission.PACKAGE_USAGE_STATS;
import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class RegistrationActivity extends AppCompatActivity implements RegistrationView  {


    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };

    @Inject
    RegistrationPresenter registrationPresenter;
    @Inject
    Context mContext;


    // UI references.
    @BindView(R.id.name) EditText mName;
    @BindView(R.id.phoneNumber) EditText mPhoneNumber;
    @BindView(R.id.spinner1) Spinner spinner;

    @BindView(R.id.login_progress) View mProgressView;
    @BindView(R.id.login_form) View mLoginFormView;
    @BindView(R.id.registrationButton) Button registrationButton ;

    private Subscription subscription = null;
    Observable<CharSequence> name;
    Observable<CharSequence> phoneNumber;
    Observable<CharSequence> bloodGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
        ((RXJavaRetrofitSampleApp) getApplication()).getComponent().inject(this);
        name = RxTextView.textChanges(mName);
        phoneNumber = RxTextView.textChanges(mPhoneNumber);
        registrationPresenter.setupView(this);

        addListenerOnSpinnerItemSelection();
        combineEvent();


    }


    @OnClick(R.id.registrationButton)
    public void attemptRegister() {
        String email = mName.getText().toString();
        String phoneNumber = mPhoneNumber.getText().toString();
        String bloodGroup = String.valueOf(spinner.getSelectedItem()) ;

            registrationPresenter.attemptRegister(email,phoneNumber,bloodGroup);



    }

    public void addListenerOnSpinnerItemSelection() {
        spinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }

    public void combineEvent(){
        subscription = Observable.combineLatest(name, phoneNumber,
                new Func2<CharSequence, CharSequence, Boolean>() {
                    @Override public Boolean call(CharSequence name, CharSequence  phoneNumber) {
                        //here you can validate the edit text
                        boolean nameValid= !TextUtils.isEmpty(name);
                        if(!nameValid){
                            mName.setError("Please Input Name");
                        }
                        boolean phoneValid = !TextUtils.isEmpty(phoneNumber) && phoneNumber.length() == 11;
                        if(!phoneValid){
                            mPhoneNumber.setError("PhoneNumber must be of 11 Digits");
                        }
                        return nameValid && phoneValid;

                    }
                }).subscribe(new Observer<Boolean>() {
            @Override public void onCompleted() {

            }

            @Override public void onError(Throwable e) {

            }

            @Override public void onNext(Boolean aBoolean) {
                if(aBoolean){
                    //here you can enable your button or what ever you want.
                    registrationButton.setEnabled(true);

                }else {
                    registrationButton.setEnabled(false);
                }

            }
        });
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }









}


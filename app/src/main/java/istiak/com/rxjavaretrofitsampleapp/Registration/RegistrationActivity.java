package istiak.com.rxjavaretrofitsampleapp.Registration;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.jakewharton.rxbinding.widget.RxTextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import istiak.com.rxjavaretrofitsampleapp.R;
import istiak.com.rxjavaretrofitsampleapp.RXJavaRetrofitSampleApp;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.functions.Func2;

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
    @BindView(R.id.phoneNumber)
    EditText mPhoneNumber;
    @BindView(R.id.spinner1)
    Spinner spinner;

    @BindView(R.id.login_progress)
    View mProgressView;
    @BindView(R.id.login_form) View mLoginFormView;
    @BindView(R.id.registrationButton)
    Button registrationButton ;

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


package istiak.com.rxjavaretrofitsampleapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import istiak.com.rxjavaretrofitsampleapp.di.network.VoidResponseWrapper;
import istiak.com.rxjavaretrofitsampleapp.repository.RegistrationRepository;

public class RegistrationViewModel extends AndroidViewModel {


    private RegistrationRepository registrationRepository;

    public RegistrationViewModel(@NonNull Application application) {
        super(application);
        registrationRepository = new RegistrationRepository();
    }

    public LiveData<VoidResponseWrapper> getRegistration(String email, String phoneNumber, String bloodGroup) {
        return registrationRepository.attemptRegister(email,phoneNumber,bloodGroup);
    }
}

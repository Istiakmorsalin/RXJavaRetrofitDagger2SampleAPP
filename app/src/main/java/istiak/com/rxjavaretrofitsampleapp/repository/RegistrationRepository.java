package istiak.com.rxjavaretrofitsampleapp.repository;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import istiak.com.rxjavaretrofitsampleapp.api.RegistrationApi;
import istiak.com.rxjavaretrofitsampleapp.di.network.NetworkModule;
import istiak.com.rxjavaretrofitsampleapp.di.network.VoidResponseWrapper;
import istiak.com.rxjavaretrofitsampleapp.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegistrationRepository extends BaseRepository {

    RegistrationApi registrationApi;

    public RegistrationRepository() {
        this.registrationApi = NetworkModule.getRegistrationApi();
    }

    public LiveData<VoidResponseWrapper> attemptRegister(String email, String phoneNumber, String bloodGroup) {
        MutableLiveData<VoidResponseWrapper> data = new MutableLiveData<>();
        User user = new User(email, phoneNumber, bloodGroup);

        registrationApi.doReg(user).enqueue(new Callback<User>() {

          VoidResponseWrapper voidResponseWrapper = new VoidResponseWrapper();
          @Override
          public void onResponse(Call<User> call, Response<User> response) {
              if(response.isSuccessful()){
                  voidResponseWrapper.setSuccess(true);
              } else {
                  voidResponseWrapper.setSuccess(false);
              }

          }

          @Override
          public void onFailure(Call<User> call, Throwable t) {
              voidResponseWrapper.setSuccess(false);
          }
      });

            return data;
    }
}

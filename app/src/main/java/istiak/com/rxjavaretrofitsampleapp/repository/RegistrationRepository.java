package istiak.com.rxjavaretrofitsampleapp.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import istiak.com.rxjavaretrofitsampleapp.api.ApiService;
import istiak.com.rxjavaretrofitsampleapp.model.GithubResponse;
import istiak.com.rxjavaretrofitsampleapp.network.GenericResponse;
import istiak.com.rxjavaretrofitsampleapp.network.NetworkClient;
import istiak.com.rxjavaretrofitsampleapp.network.VoidResponseWrapper;
import istiak.com.rxjavaretrofitsampleapp.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegistrationRepository extends BaseRepository {

    ApiService apiService;

    public RegistrationRepository() {
        this.apiService = NetworkClient.getApiService();
    }

    public LiveData<VoidResponseWrapper> attemptRegister(String email, String phoneNumber, String bloodGroup) {
        MutableLiveData<VoidResponseWrapper> data = new MutableLiveData<>();
        User user = new User(email, phoneNumber, bloodGroup);

        apiService.doReg(user).enqueue(new Callback<User>() {

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



    public LiveData<GenericResponse<GithubResponse>> getData() {
        MutableLiveData<GenericResponse<GithubResponse>> data = new MutableLiveData<>();

        apiService.getData().enqueue(new Callback<GithubResponse>() {
            GenericResponse<GithubResponse> githubResponse = new GenericResponse<>();

            @Override
            public void onResponse(Call<GithubResponse> call, Response<GithubResponse> response) {
                if(response.isSuccessful())
                  githubResponse.setResponse(response.body());
                else
                  githubResponse.setErrorMessage(response.message());

                data.setValue(githubResponse);
            }

            @Override
            public void onFailure(Call<GithubResponse> call, Throwable t) {
                githubResponse.setErrorMessage(t.getMessage());
                data.setValue(githubResponse);
            }
        });

        return data;
    }
}

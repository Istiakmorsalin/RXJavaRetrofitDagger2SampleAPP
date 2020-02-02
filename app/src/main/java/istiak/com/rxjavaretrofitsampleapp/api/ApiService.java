package istiak.com.rxjavaretrofitsampleapp.api;

import istiak.com.rxjavaretrofitsampleapp.model.GithubResponse;
import istiak.com.rxjavaretrofitsampleapp.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

public interface ApiService {

    @GET("/")
    Call<GithubResponse> getData() ;

    @POST("users")
    Observable<User> doRegistration(@Body User user) ;

    @POST("users")
    Call<User> doReg(@Body User user) ;
}

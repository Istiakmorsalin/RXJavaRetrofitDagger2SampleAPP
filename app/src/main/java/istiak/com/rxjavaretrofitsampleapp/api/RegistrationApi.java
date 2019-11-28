package istiak.com.rxjavaretrofitsampleapp.api;

/**
 * Created by slbd on 6/15/17.
 */



import istiak.com.rxjavaretrofitsampleapp.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;


public interface RegistrationApi {

    @POST("users")
    Observable<User> doRegistration(@Body User user) ;


    @POST("users")
    Call<User> doReg(@Body User user) ;


}

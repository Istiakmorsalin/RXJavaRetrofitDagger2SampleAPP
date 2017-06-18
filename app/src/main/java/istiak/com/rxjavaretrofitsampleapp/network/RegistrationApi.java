package istiak.com.rxjavaretrofitsampleapp.network;

/**
 * Created by slbd on 6/15/17.
 */



import dagger.Component;
import istiak.com.rxjavaretrofitsampleapp.model.User;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;


public interface RegistrationApi {

    @POST("users")
    Observable<User> doRegistration(@Body User user) ;

}

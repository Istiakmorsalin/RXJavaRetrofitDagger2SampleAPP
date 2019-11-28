package istiak.com.rxjavaretrofitsampleapp.di.network;

/**
 * Created by slbd on 6/15/17.
 */

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

import istiak.com.rxjavaretrofitsampleapp.api.RegistrationApi;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

@Module
public class NetworkModule {

    private static final String BASE_URL= "http://192.168.1.23:8080/api/";

    private static RegistrationApi registrationApi = null;
    private static OkHttpClient baseClient = null;

    @Provides
    @Singleton
    public HttpLoggingInterceptor providesHttpLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return interceptor;
    }

    @Provides
    @Singleton
    public OkHttpClient providesOkHttpClient(HttpLoggingInterceptor interceptor) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(interceptor);

        return httpClient.build();
    }

    @Provides @Singleton
    public Retrofit providesServerApiRetrofit(OkHttpClient httpClient) {

        return new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(httpClient)
                .build();
    }

    @Provides @Singleton public RegistrationApi providesRegistrationApi(Retrofit retrofit) {
        return retrofit.create(RegistrationApi.class);
    }

    public static RegistrationApi getRegistrationApi() {
        if (registrationApi == null)
            constructBaseService();
        return registrationApi;
    }

    private static void constructBaseService() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(getBaseClient())
                .build();
        registrationApi = retrofit.create(RegistrationApi.class);
    }

    private static OkHttpClient.Builder getBaseBackendClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(interceptor);
        return client;
    }

    private static OkHttpClient getBaseClient() {
        if (baseClient == null) {
            OkHttpClient.Builder client = getBaseBackendClient();
            baseClient = client.build();
        }
        return baseClient;
    }



}

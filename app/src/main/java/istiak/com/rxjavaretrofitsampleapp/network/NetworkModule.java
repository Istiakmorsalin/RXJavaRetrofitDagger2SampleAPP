package istiak.com.rxjavaretrofitsampleapp.network;

/**
 * Created by slbd on 6/15/17.
 */

import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import javax.inject.Singleton;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

@Module
public class NetworkModule {

    private static final String BASE_URL= " ";

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

    @Provides @Singleton @Named("serverAPI")
    public Retrofit providesServerApiRetrofit(OkHttpClient httpClient) {

        return new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(httpClient)
                .build();
    }

    @Provides @Singleton public RegistrationApi providesRegistrationApi(@Named("RegistrationApi") Retrofit retrofit) {
        return retrofit.create(RegistrationApi.class);
    }



}

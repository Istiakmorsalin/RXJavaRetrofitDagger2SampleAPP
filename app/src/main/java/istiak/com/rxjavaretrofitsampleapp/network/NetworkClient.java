package istiak.com.rxjavaretrofitsampleapp.network;

/**
 * Created by slbd on 6/15/17.
 */

import istiak.com.rxjavaretrofitsampleapp.api.ApiService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

public class NetworkClient {

    private static final String BASE_URL= "https://api.github.com/";
    private static ApiService apiService = null;
    private static OkHttpClient baseClient = null;

    public static ApiService getApiService() {
        if (apiService == null)
            constructBaseService();
        return apiService;
    }

    private static void constructBaseService() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(getBaseClient())
                .build();
        apiService = retrofit.create(ApiService.class);
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

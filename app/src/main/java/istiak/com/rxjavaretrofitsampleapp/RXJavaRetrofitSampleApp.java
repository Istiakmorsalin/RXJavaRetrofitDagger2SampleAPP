package istiak.com.rxjavaretrofitsampleapp;

import android.app.Application;

import istiak.com.rxjavaretrofitsampleapp.network.NetworkModule;

/**
 * Created by slbd on 6/16/17.
 */

public class RXJavaRetrofitSampleApp extends Application {
    private AppComponent mComponent;

    @Override public void onCreate() {
        super.onCreate();

        mComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule())
                .build();
    }

    public AppComponent getComponent() {
        return mComponent;
    }
}

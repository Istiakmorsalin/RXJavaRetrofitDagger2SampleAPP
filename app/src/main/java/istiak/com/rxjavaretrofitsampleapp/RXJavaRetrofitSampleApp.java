package istiak.com.rxjavaretrofitsampleapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;

import istiak.com.rxjavaretrofitsampleapp.di.AppComponent;
import istiak.com.rxjavaretrofitsampleapp.di.AppModule;
import istiak.com.rxjavaretrofitsampleapp.di.DaggerAppComponent;
import istiak.com.rxjavaretrofitsampleapp.di.network.NetworkModule;

/**
 * Created by slbd on 6/16/17.
 */

public class RXJavaRetrofitSampleApp extends Application implements LifecycleOwner {
    private AppComponent mComponent;
    private LifecycleRegistry lifecycleRegistry;

    private static RXJavaRetrofitSampleApp instance = null;

    @Override public void onCreate() {
        super.onCreate();

        if (instance == null) {
            instance = this;
        }

        lifecycleRegistry = new LifecycleRegistry(this);
        lifecycleRegistry.markState(Lifecycle.State.CREATED);

        mComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule())
                .build();
    }

    public AppComponent getComponent() {
        return mComponent;
    }

    public static RXJavaRetrofitSampleApp getInstance() {
        return instance;
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return lifecycleRegistry;
    }
}

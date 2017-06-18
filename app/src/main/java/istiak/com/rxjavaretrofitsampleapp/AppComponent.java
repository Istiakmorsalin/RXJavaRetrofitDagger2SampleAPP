package istiak.com.rxjavaretrofitsampleapp;

import javax.inject.Singleton;

import dagger.Component;
import istiak.com.rxjavaretrofitsampleapp.Registration.RegistrationActivity;
import istiak.com.rxjavaretrofitsampleapp.network.NetworkModule;

/**
 * Created by slbd on 6/16/17.
 */

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class
})
public interface AppComponent {
    void inject(RegistrationActivity target);
}

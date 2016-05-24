package ph.codeia.gcmnmsyncing;

import android.app.Application;
import android.content.Context;
import android.support.v4.content.LocalBroadcastManager;

import com.google.android.gms.gcm.GcmNetworkManager;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Module;
import dagger.Provides;

@Singleton
@Component(modules = {GlobalInjector.Resources.class})
public interface GlobalInjector {
    ActivityInjector activity(ActivityInjector.Resources resources);

    @Module
    class Resources {
        private final Application application;

        public Resources(Application application) {
            this.application = application;
        }

        @Provides
        Context provideContext() {
            return application;
        }

        @Provides @Singleton
        LocalBroadcastManager provideLocalBroadcastManager(Context c) {
            return LocalBroadcastManager.getInstance(c);
        }

        @Provides @Singleton
        GcmNetworkManager provideNetworkManager(Context c) {
            return GcmNetworkManager.getInstance(c);
        }
    }
}

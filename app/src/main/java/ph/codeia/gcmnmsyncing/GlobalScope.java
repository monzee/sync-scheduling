package ph.codeia.gcmnmsyncing;

import android.app.Application;
import android.content.Context;
import android.support.v4.content.LocalBroadcastManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class GlobalScope {
    private final Application application;

    public GlobalScope(Application application) {
        this.application = application;
    }

    @Provides
    Context provideContext() {
        return application;
    }

    @Provides
    @Singleton
    LocalBroadcastManager provideLocalBroadcastManager(Context c) {
        return LocalBroadcastManager.getInstance(c);
    }
}

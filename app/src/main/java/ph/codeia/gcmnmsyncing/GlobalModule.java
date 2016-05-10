package ph.codeia.gcmnmsyncing;

import android.app.Application;

import dagger.Module;

@Module
public class GlobalModule {
    private final Application application;

    public GlobalModule(Application application) {
        this.application = application;
    }
}

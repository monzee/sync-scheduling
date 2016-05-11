package ph.codeia.gcmnmsyncing;

import android.app.Activity;
import android.app.Application;

public class GcmNmApp extends Application {
    private GlobalInjector injector;

    @Override
    public void onCreate() {
        super.onCreate();
        injector = DaggerGlobalInjector.builder()
                .globalScope(new GlobalScope(this))
                .build();
    }

    public GlobalInjector getInjector() {
        return injector;
    }

    public ActivityInjector getInjector(Activity activity) {
        return injector.activity(new ActivityScope(activity));
    }
}

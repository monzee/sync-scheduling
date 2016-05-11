package ph.codeia.gcmnmsyncing;

import android.app.Activity;
import android.app.Application;

public class GcmNmApp extends Application {
    private GlobalScope injector;

    @Override
    public void onCreate() {
        super.onCreate();
        injector = DaggerGlobalScope.builder()
                .globalModule(new GlobalModule(this))
                .build();
    }

    public GlobalScope getInjector() {
        return injector;
    }

    public ActivityScope getInjector(Activity activity) {
        return injector.activity(new ActivityModule(activity));
    }
}

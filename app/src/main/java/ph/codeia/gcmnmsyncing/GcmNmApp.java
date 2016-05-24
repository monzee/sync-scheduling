package ph.codeia.gcmnmsyncing;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;

public class GcmNmApp extends Application {
    private static GlobalInjector injector;

    @Override
    public void onCreate() {
        super.onCreate();
        injector = DaggerGlobalInjector.builder()
                .resources(new GlobalInjector.Resources(this))
                .build();
    }

    public static GlobalInjector getInjector() {
        return injector;
    }

    public static ActivityInjector getInjector(AppCompatActivity activity) {
        return injector.activity(new ActivityInjector.Resources(activity));
    }
}

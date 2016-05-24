package ph.codeia.gcmnmsyncing;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;

import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;
import ph.codeia.gcmnmsyncing.main.MainInjector;
import ph.codeia.gcmnmsyncing.util.PerActivity;

@PerActivity
@Subcomponent(modules = {ActivityInjector.Resources.class})
public interface ActivityInjector {
    MainInjector main();

    @Module
    class Resources {
        private final AppCompatActivity activity;

        public Resources(AppCompatActivity activity) {
            this.activity = activity;
        }

        @Provides
        Activity provideActivity() {
            return activity;
        }

        @Provides
        LinearLayoutManager provideLinearLayoutManager(Activity c) {
            return new LinearLayoutManager(c);
        }

        @Provides @PerActivity
        LayoutInflater provideLayoutInflater(Activity c) {
            return LayoutInflater.from(c);
        }
    }
}

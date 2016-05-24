package ph.codeia.gcmnmsyncing.main;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;
import ph.codeia.gcmnmsyncing.util.PerFeature;

@PerFeature
@Subcomponent(modules = {MainInjector.Resources.class})
public interface MainInjector {
    void inject(MainActivity activity);

    @Module
    class Resources {
        @Provides
        static MainContract.Interaction interaction(MainPresenter p) {
            return p;
        }

        @Provides
        static MainContract.Synchronization synchronization(MainPresenter p) {
            return p;
        }

        @Provides
        static MainContract.Display display(MainView v) {
            return v;
        }

        @Provides
        static MainContract.Storage storage(TaskStorage s) {
            return s;
        }

        @Provides @Named("alt_tasks")
        static List<TaskItem> tasks(MainState state) {
            return state.tasks;
        }

        @Provides @Named("alt_tasks")
        static AtomicBoolean staleness(MainState state) {
            return state.stale;
        }
    }
}

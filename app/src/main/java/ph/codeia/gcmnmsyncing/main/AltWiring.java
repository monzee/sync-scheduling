package ph.codeia.gcmnmsyncing.main;

import android.app.Activity;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.inject.Named;

import butterknife.ButterKnife;
import dagger.MembersInjector;
import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;
import ph.codeia.gcmnmsyncing.util.Consumer;
import ph.codeia.gcmnmsyncing.util.PerFeature;

public abstract class AltWiring {
    @Module
    public static class Scope {
        @Provides
        AltAdapter adapter(MembersInjector<AltAdapter> deps) {
            AltAdapter a = new AltAdapter();
            deps.injectMembers(a);
            return a;
        }

        @Provides @PerFeature
        AltView view(Activity a, MembersInjector<AltView> deps) {
            AltView v = new AltView();
            ButterKnife.bind(v, a);
            deps.injectMembers(v);
            v.prepare();
            return v;
        }

        @Provides @PerFeature
        AltPresenter presenter(MembersInjector<AltPresenter> deps) {
            AltPresenter p = new AltPresenter();
            deps.injectMembers(p);
            return p;
        }

        @Provides
        AltContract.Interaction interaction(AltPresenter p) {
            return p;
        }

        @Provides
        AltContract.Synchronization synchronization(AltPresenter p) {
            return p;
        }

        @Provides
        AltContract.Display display(AltView v) {
            return v;
        }

        @Provides
        static AltContract.Storage storage(final TaskStorage s) {
            return new AltContract.Storage() {
                @Override
                public void load(Consumer<List<TaskItem>> then) {
                    s.load(then);
                }

                @Override
                public void add(TaskItem task, Consumer<TaskItem> then) {
                    s.add(task, then);
                }

                @Override
                public void delete(TaskItem task, Consumer<TaskItem> then) {
                    s.delete(task, then);
                }
            };
        }

        @Provides @Named("alt_tasks")
        List<TaskItem> tasks(AltState state) {
            return state.tasks;
        }

        @Provides @Named("alt_tasks")
        AtomicBoolean initFlag(AltState state) {
            return state.initialized;
        }
    }

    @PerFeature
    @Subcomponent(modules = {Scope.class})
    public interface Injector {
        void inject(AltActivity activity);
    }
}

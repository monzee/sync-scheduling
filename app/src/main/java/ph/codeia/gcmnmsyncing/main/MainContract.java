package ph.codeia.gcmnmsyncing.main;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import butterknife.ButterKnife;
import dagger.MembersInjector;
import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;

public abstract class MainContract {
    @Module
    @SuppressWarnings("unused")
    public static class Provider {
        private final List<TaskItem> tasks = new ArrayList<>();

        @Provides
        MainView provideView(Activity a, MembersInjector<MainView> dependencies) {
            MainView view = new MainView();
            ButterKnife.bind(view, a);
            dependencies.injectMembers(view);
            view.prepare();
            return view;
        }

        @Provides
        TaskAdapter provideAdapter(MembersInjector<TaskAdapter> dependencies) {
            TaskAdapter a = new TaskAdapter();
            dependencies.injectMembers(a);
            return a;
        }

        @Provides @Named("tasks")
        List<TaskItem> provideTasks() {
            return tasks;
        }
    }

    @Subcomponent(modules = {MainContract.Provider.class})
    public interface Scope {
        void inject(MainActivity activity);
    }
}

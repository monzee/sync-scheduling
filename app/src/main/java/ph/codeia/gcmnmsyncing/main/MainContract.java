package ph.codeia.gcmnmsyncing.main;

import android.app.Activity;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import butterknife.ButterKnife;
import dagger.MembersInjector;
import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;
import ph.codeia.gcmnmsyncing.util.Consumer;

@SuppressWarnings("unused")
public abstract class MainContract {
    public interface View {
        void show(List<TaskItem> tasks);
        void add(TaskItem task);
        void delete(TaskItem task);
        void update(TaskItem task);
    }

    public interface Presenter {
        void bind(View view);
        void loadTasks();
        void addTask(TaskItem task);
        void deleteTask(TaskItem task);
    }

    public interface Model {
        void didLoad(List<TaskItem> tasks);
        void didAdd(TaskItem task);
        void didDelete(TaskItem task);
        @Nullable List<TaskItem> unload();
    }

    public interface Storage {
        void load(Consumer<List<TaskItem>> then);
        void add(TaskItem task, Consumer<TaskItem> then);
        void delete(TaskItem task, Consumer<TaskItem> then);
    }

    @Module
    public static class Scope {
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

    @Subcomponent(modules = {Scope.class})
    public interface Injector {
        void inject(MainActivity activity);
    }
}

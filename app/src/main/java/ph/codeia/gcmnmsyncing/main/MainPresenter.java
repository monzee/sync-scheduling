package ph.codeia.gcmnmsyncing.main;


import java.util.List;

import javax.inject.Inject;

import ph.codeia.gcmnmsyncing.util.PerActivity;

@PerActivity
public class MainPresenter {

    private MainView view;
    private final MainState state;
    private final TaskStorage store;

    @Inject
    public MainPresenter(MainState state, TaskStorage store) {
        this.state = state;
        this.store = store;
    }

    public void bind(MainView view) {
        this.view = view;
        if (view == null) {
            return;
        }
        List<TaskItem> gotItems = state.unload();
        if (gotItems != null) {
            view.show(gotItems);
        }
        state.forEachNewTask(view::add);
    }

    public void loadTasks() {
        store.load(tasks -> {
            if (view != null) {
                view.show(tasks);
            } else {
                state.load(tasks);
            }
        });
    }

    public void addTask(TaskItem task) {
        store.add(task, item -> {
            if (view != null) {
                view.add(item);
            } else {
                state.add(item);
            }
        });
    }

    public void deleteTask(TaskItem task) {
        store.delete(task, item -> {
            if (view != null) {
                view.delete(item);
            } else {
                state.delete(item);
            }
        });
    }
}

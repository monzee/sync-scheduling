package ph.codeia.gcmnmsyncing.main;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.inject.Inject;
import javax.inject.Named;

import ph.codeia.gcmnmsyncing.util.PerFeature;

@PerFeature
public class MainPresenter implements MainContract.Interaction, MainContract.Synchronization {
    @Inject @Named("alt_tasks")
    List<TaskItem> tasks;

    @Inject @Named("alt_tasks")
    AtomicBoolean stale;

    @Inject
    MainContract.Storage store;

    private MainContract.Display view;

    @Inject
    public MainPresenter() {}

    @Override
    public void didPressNow() {
        addTask(TaskItem.NOW_TASK, TaskItem.PENDING_STATUS);
    }

    @Override
    public void didPressDeferred() {
        addTask(TaskItem.ONEOFF_TASK, TaskItem.PENDING_STATUS);
    }

    @Override
    public void didPressDelete(int pos) {
        if (isValidIndex(pos)) {
            deleteTask(tasks.get(pos).getId());
        }
    }

    @Override
    public void bind(MainContract.Display view) {
        this.view = view;
        if (view != null) {
            if (stale.get()) {
                loadTasks();
                stale.set(false);
            } else {
                view.refresh();
            }
        }
    }

    @Override
    public void loadTasks() {
        store.load(items -> {
            tasks.clear();
            tasks.addAll(items);
            if (view != null) {
                view.refresh();
            }
        });
    }

    @Override
    public void addTask(String type, String status) {
        TaskItem t = new TaskItem(generateId(), type, status);
        store.add(t, task -> {
            tasks.add(0, task);
            if (view != null) {
                stale.set(false);
                view.inserted(0);
            }
        });
    }

    @Override
    public void taskUpdated(String id, String status) {
        int i = find(id);
        if (isValidIndex(i)) {
            TaskItem t = tasks.get(i);
            t.setStatus(status);
            if (view != null) {
                view.updated(i);
            }
        }
    }

    @Override
    public void deleteTask(String id) {
        store.delete(new TaskItem(id, "", ""), task -> {
            int i = find(id);
            if (isValidIndex(i)) {
                tasks.remove(i);
                if (view != null) {
                    stale.set(false);
                    view.removed(i);
                }
            }
        });
    }

    private static String generateId() {
        return "task_" + Calendar.getInstance().getTimeInMillis();
    }

    private int find(String id) {
        int i = 0;
        for (TaskItem t : tasks) {
            if (id.equals(t.getId())) {
                return i;
            }
            i++;
        }
        return -1;
    }

    private boolean isValidIndex(int i) {
        return 0 <= i && i < tasks.size();
    }
}

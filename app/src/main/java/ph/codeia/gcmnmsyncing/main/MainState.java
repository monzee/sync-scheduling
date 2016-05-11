package ph.codeia.gcmnmsyncing.main;

import android.support.annotation.Nullable;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import ph.codeia.gcmnmsyncing.util.Consumer;

@Singleton
public class MainState {

    private final Deque<TaskItem> newTasks = new ArrayDeque<>();
    private @Nullable List<TaskItem> loadedTasks;

    @Inject
    public MainState() {
    }

    public void load(List<TaskItem> tasks) {
        loadedTasks = tasks;
    }

    public @Nullable List<TaskItem> unload() {
        List<TaskItem> tasks = loadedTasks;
        loadedTasks = null;
        return tasks;
    }

    public void add(TaskItem task) {
        newTasks.add(task);
    }

    public void forEachNewTask(Consumer<TaskItem> block) {
        while (!newTasks.isEmpty()) {
            block.accept(newTasks.remove());
        }
    }
}

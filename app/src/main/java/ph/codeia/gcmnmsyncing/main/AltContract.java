package ph.codeia.gcmnmsyncing.main;

import java.util.List;

import ph.codeia.gcmnmsyncing.util.Consumer;

public abstract class AltContract {
    public interface Display {
        void refresh();
        void inserted(int pos);
        void updated(int pos);
        void removed(int pos);
    }

    public interface Interaction {
        void didPressNow();
        void didPressDeferred();
        void didPressDelete(int pos);
    }

    public interface Synchronization {
        void bind(Display view);
        void loadTasks();
        void addTask(String type, String status);
        void updateTask(String id, String status);
        void deleteTask(String id);
    }

    public interface Storage {
        void load(Consumer<List<TaskItem>> then);
        void add(TaskItem task, Consumer<TaskItem> then);
        void delete(TaskItem task, Consumer<TaskItem> then);
    }
}

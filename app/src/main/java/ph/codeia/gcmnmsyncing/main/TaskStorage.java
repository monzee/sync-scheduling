package ph.codeia.gcmnmsyncing.main;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import java.util.List;

import javax.inject.Inject;

import ph.codeia.gcmnmsyncing.util.Consumer;
import ph.codeia.gcmnmsyncing.util.PerActivity;

@PerActivity
public class TaskStorage {
    private final Context context;

    private class Load extends AsyncTask<Void, Void, List<TaskItem>> {
        private final Consumer<List<TaskItem>> callback;

        private Load(Consumer<List<TaskItem>> callback) {
            this.callback = callback;
        }

        @Override
        protected List<TaskItem> doInBackground(Void... params) {
            return CodelabUtil.getTaskItemsFromFile(context);
        }

        @Override
        protected void onPostExecute(List<TaskItem> taskItems) {
            callback.accept(taskItems);
        }
    }

    private class Add extends AsyncTask<TaskItem, Void, TaskItem> {
        private final Consumer<TaskItem> callback;

        private Add(Consumer<TaskItem> callback) {
            this.callback = callback;
        }

        @Override
        protected TaskItem doInBackground(TaskItem... params) {
            TaskItem t = params[0];
            CodelabUtil.addTaskItemToFile(context, t);
            return t;
        }

        @Override
        protected void onPostExecute(TaskItem taskItem) {
            callback.accept(taskItem);
            if (taskItem.getType().equals(TaskItem.ONEOFF_TASK)) {
                // TODO: 10/05/16
            } else {
                Intent i = new Intent(context, NowIntentService.class);
                i.putExtra(CodelabUtil.TASK_ID, taskItem.getId());
                context.startService(i);
            }
        }
    }

    private class Delete extends AsyncTask<TaskItem, Void, TaskItem> {
        private final Consumer<TaskItem> callback;

        private Delete(Consumer<TaskItem> callback) {
            this.callback = callback;
        }

        @Override
        protected TaskItem doInBackground(TaskItem... params) {
            TaskItem task = params[0];
            CodelabUtil.deleteTaskItemFromFile(context, task);
            return task;
        }

        @Override
        protected void onPostExecute(TaskItem taskItem) {
            callback.accept(taskItem);
        }
    }

    @Inject
    public TaskStorage(Context context) {
        this.context = context;
    }

    public void load(Consumer<List<TaskItem>> onLoad) {
        new Load(onLoad).execute();
    }

    public void add(TaskItem task, Consumer<TaskItem> onAdd) {
        new Add(onAdd).execute(task);
    }

    public void delete(TaskItem task, Consumer<TaskItem> onDelete) {
        new Delete(onDelete).execute(task);
    }
}

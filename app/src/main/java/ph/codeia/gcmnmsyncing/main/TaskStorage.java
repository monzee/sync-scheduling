package ph.codeia.gcmnmsyncing.main;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.OneoffTask;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import ph.codeia.gcmnmsyncing.util.Consumer;

@Singleton
public class TaskStorage implements MainContract.Storage {
    public static final String TAG = TaskStorage.class.getSimpleName();
    private final Context context;
    private final GcmNetworkManager scheduler;
    private final MainState state;

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
                Bundle b = new Bundle();
                b.putString(CodelabUtil.TASK_ID, taskItem.getId());

                scheduler.schedule(new OneoffTask.Builder()
                        .setService(BestTimeService.class)
                        .setTag(taskItem.getId())
                        .setRequiredNetwork(OneoffTask.NETWORK_STATE_CONNECTED)
                        .setExecutionWindow(0, 30)
                        .setExtras(b)
                        .build());
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
    public TaskStorage(Context context, GcmNetworkManager scheduler, MainState state) {
        this.context = context;
        this.scheduler = scheduler;
        this.state = state;
    }

    public void load(Consumer<List<TaskItem>> onLoad) {
        Log.d(TAG, "loading from disk");
        new Load(onLoad).execute();
    }

    public void add(TaskItem task, Consumer<TaskItem> onAdd) {
        state.stale.set(true);
        Log.d(TAG, String.format("adding to disk: %s", task.getId()));
        new Add(onAdd).execute(task);
    }

    public void delete(TaskItem task, Consumer<TaskItem> onDelete) {
        state.stale.set(true);
        Log.d(TAG, String.format("deleting from disk: %s", task.getId()));
        new Delete(onDelete).execute(task);
    }
}

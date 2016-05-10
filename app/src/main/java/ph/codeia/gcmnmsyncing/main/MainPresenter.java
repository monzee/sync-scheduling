package ph.codeia.gcmnmsyncing.main;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import javax.inject.Inject;

public class MainPresenter {

    private MainView view;
    private List<TaskItem> gotItems;
    private final Deque<TaskItem> newItems = new ArrayDeque<>();

    private class LoadTask extends AsyncTask<Void, Void, List<TaskItem>> {
        private final Context context;

        private LoadTask(Context context) {
            this.context = context;
        }

        @Override
        protected List<TaskItem> doInBackground(Void... params) {
            return CodelabUtil.getTaskItemsFromFile(context);
        }

        @Override
        protected void onPostExecute(List<TaskItem> taskItems) {
            Log.d("Presenter", String.format("got %d tasks", taskItems.size()));
            if (view != null) {
                view.show(taskItems);
            } else {
                gotItems = taskItems;
            }
        }
    }

    private class AddTask extends AsyncTask<TaskItem, Void, TaskItem> {
        private final Context context;

        private AddTask(Context context) {
            this.context = context;
        }

        @Override
        protected TaskItem doInBackground(TaskItem... params) {
            TaskItem task = params[0];
            CodelabUtil.addTaskItemToFile(context, task);
            return task;
        }

        @Override
        protected void onPostExecute(TaskItem taskItem) {
            if (view != null) {
                view.add(taskItem);
            } else {
                newItems.add(taskItem);
            }
            if (taskItem.getType().equals(TaskItem.ONEOFF_TASK)) {
                // TODO: 10/05/16
            } else {
                Intent i = new Intent(context, NowIntentService.class);
                i.putExtra(CodelabUtil.TASK_ID, taskItem.getId());
                context.startService(i);
            }
        }
    }

    @Inject
    public MainPresenter() {
    }

    public void bind(MainView view) {
        this.view = view;
        if (view == null) {
            return;
        }
        if (gotItems != null) {
            view.show(gotItems);
            gotItems = null;
        }
        while (!newItems.isEmpty()) {
            view.add(newItems.remove());
        }
    }

    public void loadTasks(Context c) {
        new LoadTask(c).execute();
    }

    public void addTask(Context c, TaskItem task) {
        Log.d("Presenter", "adding task: " + task.getId());
        new AddTask(c).execute(task);
    }
}

package ph.codeia.gcmnmsyncing.main;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import ph.codeia.gcmnmsyncing.R;

public class MainView {
    @BindView(R.id.task_list)
    RecyclerView tasks;

    @Inject
    TaskAdapter taskAdapter;

    @Inject
    LinearLayoutManager taskLayout;

    @Inject
    MainPresenter presenter;

    public void prepareTaskList() {
        tasks.setLayoutManager(taskLayout);
        tasks.setAdapter(taskAdapter);
    }

    @OnClick(R.id.do_not_now)
    public void doBestTime() {
        // TODO: 10/05/16
    }

    @OnClick(R.id.do_now)
    public void doNow(View view) {
        String id = "task_" + Calendar.getInstance().getTimeInMillis();
        TaskItem task = new TaskItem(id, TaskItem.NOW_TASK, TaskItem.PENDING_STATUS);
        presenter.addTask(view.getContext(), task);
    }

    public void show(List<TaskItem> items) {
        taskAdapter.setTaskItems(items);
        taskAdapter.notifyDataSetChanged();
    }

    public void add(TaskItem item) {
        taskAdapter.addTaskItem(item);
        tasks.scrollToPosition(0);
    }

    public void updateTask(String id, String status) {
        taskAdapter.updateTaskItemStatus(id, status);
    }
}

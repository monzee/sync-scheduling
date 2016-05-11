package ph.codeia.gcmnmsyncing.main;


import android.support.v7.widget.RecyclerView;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import ph.codeia.gcmnmsyncing.R;
import ph.codeia.gcmnmsyncing.util.PerActivity;

@PerActivity
public class MainView {
    @BindView(R.id.task_list)
    RecyclerView tasks;

    @Inject
    MainPresenter presenter;

    @Inject
    TaskPresenter taskPresenter;

    public void prepare() {
        presenter.bind(this);
        taskPresenter.bind(tasks);
    }

    @OnClick(R.id.do_not_now)
    public void doBestTime() {
        // TODO: 10/05/16
    }

    @OnClick(R.id.do_now)
    public void doNow() {
        String id = "task_" + Calendar.getInstance().getTimeInMillis();
        presenter.addTask(new TaskItem(id, TaskItem.NOW_TASK, TaskItem.PENDING_STATUS));
    }

    public void show(List<TaskItem> items) {
        taskPresenter.setItems(items);
    }

    public void add(TaskItem item) {
        taskPresenter.addItem(item);
        tasks.scrollToPosition(0);
    }

    public void updateTask(String id, String status) {
        taskPresenter.updateTaskStatus(id, status);
    }
}

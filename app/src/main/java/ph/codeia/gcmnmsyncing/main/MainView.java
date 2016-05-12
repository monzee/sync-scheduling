package ph.codeia.gcmnmsyncing.main;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.OnClick;
import ph.codeia.gcmnmsyncing.R;

public class MainView {
    @BindView(R.id.task_list)
    RecyclerView tasks;

    @Inject
    MainPresenter presenter;

    @Inject
    TaskAdapter adapter;

    @Inject
    LinearLayoutManager linear;

    @Inject @Named("tasks")
    List<TaskItem> taskItems;

    public void prepare() {
        tasks.setAdapter(adapter);
        tasks.setLayoutManager(linear);
    }

    @OnClick(R.id.do_not_now)
    public void doDeferred() {
        // TODO: 10/05/16
    }

    @OnClick(R.id.do_now)
    public void doNow() {
        String id = "task_" + Calendar.getInstance().getTimeInMillis();
        presenter.addTask(new TaskItem(id, TaskItem.NOW_TASK, TaskItem.PENDING_STATUS));
    }

    public void show(List<TaskItem> items) {
        taskItems.clear();
        taskItems.addAll(items);
        adapter.notifyDataSetChanged();
    }

    public void add(TaskItem item) {
        taskItems.add(0, item);
        adapter.notifyItemInserted(0);
        tasks.scrollToPosition(0);
    }

    public void update(TaskItem task) {
        int i = find(task.getId());
        if (0 <= i && i < taskItems.size()) {
            taskItems.get(i).setStatus(task.getStatus());
            adapter.notifyItemChanged(i);
        }
    }

    public void delete(TaskItem task) {
        int i = find(task.getId());
        if (0 <= i && i < taskItems.size()) {
            taskItems.remove(i);
            adapter.notifyItemRemoved(i);
        }
    }

    private int find(String id) {
        int i = 0;
        for (TaskItem task : taskItems) {
            if (task.getId().equals(id)) {
                return i;
            }
            i++;
        }
        return -1;
    }
}

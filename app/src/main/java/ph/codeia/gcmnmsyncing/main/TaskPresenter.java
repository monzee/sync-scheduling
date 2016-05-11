package ph.codeia.gcmnmsyncing.main;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ph.codeia.gcmnmsyncing.R;
import ph.codeia.gcmnmsyncing.util.Consumer;

public class TaskPresenter extends RecyclerView.Adapter<TaskPresenter.Entry> {
    private static final String TAG = TaskPresenter.class.getSimpleName();

    static class Entry extends RecyclerView.ViewHolder {
        @BindView(R.id.label_task)
        TextView task;

        @BindView(R.id.label_status)
        TextView status;

        @BindView(R.id.do_delete)
        Button delete;

        Entry(View itemView, Consumer<Integer> onDelete) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            delete.setOnClickListener(v -> onDelete.accept(getAdapterPosition()));
        }
    }

    private final List<TaskItem> items;

    @Inject
    LayoutInflater inflater;

    @Inject
    LinearLayoutManager layoutManager;

    @Inject
    TaskStorage store;

    public TaskPresenter() {
        this(new ArrayList<>());
    }

    public TaskPresenter(List<TaskItem> items) {
        this.items = items;
    }

    public void bind(RecyclerView view) {
        view.setLayoutManager(layoutManager);
        view.setAdapter(this);
    }

    @Override
    public Entry onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_task, parent, false);
        return new Entry(view, this::deleteItemAt);
    }

    @Override
    public void onBindViewHolder(Entry holder, int position) {
        TaskItem task = items.get(position);
        holder.task.setText(task.getType());
        holder.status.setText(task.getStatus());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<TaskItem> tasks) {
        items.clear();
        items.addAll(tasks);
        notifyDataSetChanged();
    }

    public void addItem(TaskItem task) {
        items.add(0, task);
        Log.d(TAG, String.format("added %s; new size is %d", task.getId(), getItemCount()));
        notifyItemInserted(0);
    }

    public void deleteItemAt(int pos) {
        Log.d(TAG, String.format("deleting task #%d of %d", pos, getItemCount()));
        if (pos < 0 || pos >= items.size()) {
            Log.d(TAG, "illegal index! clicking too fast? stale view?");
            return;
        }
        store.delete(items.get(pos), task -> {
            items.remove(pos);
            Log.d(TAG, String.format("deleted %s; new size is %d", task.getId(), getItemCount()));
            notifyItemRemoved(pos);
        });
    }

    public void updateTaskStatus(String id, String status) {
        int i = 0;
        for (TaskItem task : items) {
            if (task.getId().equals(id)) {
                task.setStatus(status);
                notifyItemChanged(i);
                break;
            }
            i++;
        }
    }
}

package ph.codeia.gcmnmsyncing.main;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import ph.codeia.gcmnmsyncing.R;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.Entry> {
    public static class Entry extends RecyclerView.ViewHolder {
        @BindView(R.id.label_task)
        TextView task;

        @BindView(R.id.label_status)
        TextView status;

        @BindView(R.id.do_delete)
        Button delete;

        public Entry(View itemView) {
            super(itemView);
        }
    }

    @Inject
    LayoutInflater inflater;

    @Inject
    MainContract.Interaction user;

    @Inject @Named("alt_tasks")
    List<TaskItem> tasks;

    @Inject
    public TaskAdapter() {}

    @Override
    public Entry onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_task, parent, false);
        Entry holder = new Entry(view);
        ButterKnife.bind(holder, view);
        holder.delete.setOnClickListener(v -> user.didPressDelete(holder.getAdapterPosition()));
        return holder;
    }

    @Override
    public void onBindViewHolder(Entry holder, int position) {
        TaskItem t = tasks.get(position);
        holder.task.setText(t.getType());
        holder.status.setText(t.getStatus());
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }
}

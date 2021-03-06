package ph.codeia.gcmnmsyncing.main;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ph.codeia.gcmnmsyncing.R;

public class MainView implements MainContract.Display {
    @Inject
    TaskAdapter adapter;

    @Inject
    MainContract.Interaction user;

    @BindView(R.id.task_list)
    RecyclerView tasks;

    @Inject
    public MainView() {}

    @Inject
    public void prepare(Activity a, LinearLayoutManager linear) {
        ButterKnife.bind(this, a);
        tasks.setLayoutManager(linear);
        tasks.setAdapter(adapter);
    }

    @OnClick(R.id.do_now)
    void doNow() {
        user.didPressNow();
    }

    @OnClick(R.id.do_not_now)
    void doDeferred() {
        user.didPressDeferred();
    }

    @Override
    public void refresh() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void inserted(int pos) {
        adapter.notifyItemInserted(pos);
        tasks.scrollToPosition(pos);
    }

    @Override
    public void updated(int pos) {
        adapter.notifyItemChanged(pos);
    }

    @Override
    public void removed(int pos) {
        adapter.notifyItemRemoved(pos);
    }
}

package ph.codeia.gcmnmsyncing.main;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import ph.codeia.gcmnmsyncing.GcmNmApp;
import ph.codeia.gcmnmsyncing.R;
import ph.codeia.gcmnmsyncing.util.OnBroadcast;

public class MainActivity extends AppCompatActivity {
    @Inject
    LocalBroadcastManager broadcastManager;

    @Inject
    MainView view;

    @Inject
    MainPresenter presenter;

    private final BroadcastReceiver doUpdate = OnBroadcast.run((context, intent) -> {
        String id = intent.getStringExtra(CodelabUtil.TASK_ID);
        String status = intent.getStringExtra(CodelabUtil.TASK_STATUS);
        view.update(new TaskItem(id, "(irrelevant)", status));
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((GcmNmApp) getApplication())
                .getInjector(this)
                .main()
                .inject(this);
        presenter.bind(view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        broadcastManager.registerReceiver(doUpdate,
                new IntentFilter(CodelabUtil.TASK_UPDATE_FILTER));
        presenter.loadTasks();
    }

    @Override
    protected void onPause() {
        super.onPause();
        broadcastManager.unregisterReceiver(doUpdate);
    }
}

package ph.codeia.gcmnmsyncing.main;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import ph.codeia.gcmnmsyncing.GcmNmApp;
import ph.codeia.gcmnmsyncing.R;
import ph.codeia.gcmnmsyncing.util.OnBroadcast;

public class MainActivity extends AppCompatActivity {
    @Inject
    MainContract.Display view;

    @Inject
    MainContract.Synchronization presenter;

    @Inject
    LocalBroadcastManager bm;

    private final BroadcastReceiver doUpdate = OnBroadcast.run((context, intent) -> {
        String id = intent.getStringExtra(CodelabUtil.TASK_ID);
        String status = intent.getStringExtra(CodelabUtil.TASK_STATUS);
        presenter.taskUpdated(id, status);
    });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GcmNmApp.getInjector(this).main().inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        bm.registerReceiver(doUpdate, new IntentFilter(CodelabUtil.TASK_UPDATE_FILTER));
        presenter.bind(view);
    }

    @Override
    protected void onPause() {
        super.onPause();
        bm.unregisterReceiver(doUpdate);
        presenter.bind(null);
    }
}

package ph.codeia.gcmnmsyncing;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;

import dagger.Module;
import dagger.Provides;
import ph.codeia.gcmnmsyncing.util.PerActivity;

@Module
@SuppressWarnings("unused")
public class ActivityModule {
    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    Context provideContext() {
        return activity;
    }

    @Provides
    @PerActivity
    Activity provideActivity() {
        return activity;
    }

    @Provides
    LocalBroadcastManager provideLocalBroadcastManager(Context c) {
        return LocalBroadcastManager.getInstance(c);
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(Context c) {
        return new LinearLayoutManager(c);
    }

    @Provides
    @PerActivity
    LayoutInflater provideLayoutInflater(Context c) {
        return LayoutInflater.from(c);
    }
}

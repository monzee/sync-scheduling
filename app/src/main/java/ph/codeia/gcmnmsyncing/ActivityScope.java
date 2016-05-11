package ph.codeia.gcmnmsyncing;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;

import dagger.Module;
import dagger.Provides;
import ph.codeia.gcmnmsyncing.util.PerActivity;

@Module
public class ActivityScope {
    private final Activity activity;

    public ActivityScope(Activity activity) {
        this.activity = activity;
    }

    @Provides
    Activity provideActivity() {
        return activity;
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(Activity c) {
        return new LinearLayoutManager(c);
    }

    @Provides
    @PerActivity
    LayoutInflater provideLayoutInflater(Activity c) {
        return LayoutInflater.from(c);
    }
}

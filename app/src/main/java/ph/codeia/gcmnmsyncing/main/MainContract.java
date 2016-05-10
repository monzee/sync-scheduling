package ph.codeia.gcmnmsyncing.main;

import android.app.Activity;

import butterknife.ButterKnife;
import dagger.MembersInjector;
import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;

public abstract class MainContract {
    @Module
    @SuppressWarnings("unused")
    public static class Provider {
        @Provides
        MainView provideView(Activity a, MembersInjector<MainView> dependencies) {
            MainView view = new MainView();
            ButterKnife.bind(view, a);
            dependencies.injectMembers(view);
            view.prepareTaskList();
            return view;
        }
    }

    @Subcomponent(modules = {MainContract.Provider.class})
    public interface Scope {
        void inject(MainActivity activity);
    }
}

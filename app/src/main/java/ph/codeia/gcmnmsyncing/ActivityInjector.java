package ph.codeia.gcmnmsyncing;

import dagger.Subcomponent;
import ph.codeia.gcmnmsyncing.main.MainContract;
import ph.codeia.gcmnmsyncing.util.PerActivity;

@PerActivity
@Subcomponent(modules = {ActivityScope.class})
public interface ActivityInjector {
    MainContract.Injector main();
}

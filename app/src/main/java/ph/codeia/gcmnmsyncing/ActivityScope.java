package ph.codeia.gcmnmsyncing;

import dagger.Subcomponent;
import ph.codeia.gcmnmsyncing.main.MainContract;
import ph.codeia.gcmnmsyncing.util.PerActivity;

@PerActivity
@Subcomponent(modules = {ActivityModule.class})
public interface ActivityScope {
    MainContract.Scope main();
}

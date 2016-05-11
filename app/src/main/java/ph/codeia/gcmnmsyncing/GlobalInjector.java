package ph.codeia.gcmnmsyncing;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {GlobalScope.class})
public interface GlobalInjector {
    ActivityInjector activity(ActivityScope activityScope);
}

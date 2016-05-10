package ph.codeia.gcmnmsyncing;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {GlobalModule.class})
public interface GlobalScope {
    ActivityScope activity(ActivityModule activityModule);
}

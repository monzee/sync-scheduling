package ph.codeia.gcmnmsyncing.main;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MainState {
    public final List<TaskItem> tasks = new ArrayList<>();
    public final AtomicBoolean stale = new AtomicBoolean(true);

    @Inject
    public MainState() {}
}

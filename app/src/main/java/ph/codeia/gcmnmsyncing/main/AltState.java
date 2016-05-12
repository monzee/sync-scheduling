package ph.codeia.gcmnmsyncing.main;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AltState {
    public final List<TaskItem> tasks = new ArrayList<>();
    public final AtomicBoolean initialized = new AtomicBoolean(false);

    @Inject
    public AltState() {
    }
}

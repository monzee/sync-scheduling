package ph.codeia.gcmnmsyncing.util;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class OnBroadcast extends BroadcastReceiver {
    public static BroadcastReceiver run(Runnable r) {
        return new OnBroadcast((c, i) -> r.run());
    }

    public static BroadcastReceiver run(BinaryConsumer<Context, Intent> receiver) {
        return new OnBroadcast(receiver);
    }

    public static BroadcastReceiver run(Consumer<Context> receiver) {
        return new OnBroadcast((c, i) -> receiver.accept(c));
    }

    private final BinaryConsumer<Context, Intent> delegate;

    private OnBroadcast(BinaryConsumer<Context, Intent> delegate) {
        this.delegate = delegate;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        delegate.accept(context, intent);
    }
}

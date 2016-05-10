package ph.codeia.gcmnmsyncing.util;

public interface BinaryConsumer<T, U> {
    void accept(T t, U u);
}

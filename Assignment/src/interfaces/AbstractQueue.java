package interfaces;

public interface AbstractQueue<E> extends Iterable<E> {
    void offer(E element);
    E poll();
    void pollAll();
    E peek();
    int size();
    boolean isEmpty();
}


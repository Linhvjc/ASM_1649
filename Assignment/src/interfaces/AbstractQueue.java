package interfaces;

import implement.ArrayList;

public interface AbstractQueue<E> extends Iterable<E> {
    void offer(E element);
    E poll();
    void pollAll();
    E peek();
    int size();
    boolean contains(E element);
    boolean remove(E element);
    boolean remove(int index);
    int getIndex(E element);
    E getItemByIndex(int index);
    ArrayList<Integer> getMultipleIndex(E element);
    boolean isEmpty();
}


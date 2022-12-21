package implement;

import interfaces.AbstractQueue;

import java.util.Arrays;
import java.util.Iterator;

public class Queue2<E> implements AbstractQueue<E> {
    private static final int DEFAULT_CAPACITY = 4;
    private int size;
    private E[] elements;
    private E head;

    public Queue2() {
        this.elements = (E[]) new Object[DEFAULT_CAPACITY];
    }
    @Override
    public void offer(E element) {
        if (this.size == this.elements.length) {
            this.elements = grow();
        }

        int indexNew = this.size++;
        this.elements[indexNew] = element;
        if (head == null) {
            head = element;
        }
        size++;
    }

    private E[] grow() {
        return Arrays.copyOf(this.elements, this.elements.length *2);
    }

    @Override
    public E poll() {
        ensureNonEmpty();
        E result = head;
        for (int i =0; i<size; i++) {
            elements[i] = elements[i+1];
        }
        ensureCapacity();
        head = elements[0];
        return result;
    }

    private void ensureCapacity() {
        if (this.size < this.elements.length / 3) {
            this.elements = shrink();
        }
    }

    private E[] shrink() {
        return Arrays.copyOf(this.elements, this.elements.length /2);
    }
    private void ensureNonEmpty() {
        if (size == 0) throw new IllegalStateException("Queue is Empty!!! Can not action!");
    }

    @Override
    public E peek() {
        return head;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private E current = head;
            private int indexHead = 0;
            @Override
            public boolean hasNext() {
                return current!= null;
            }

            @Override
            public E next() {
                E element = current;
                this.current = elements[size++];
                return element;
            }
        };
    }
}


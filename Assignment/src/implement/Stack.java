package implement;
import interfaces.AbstractStack;
import java.util.Iterator;

public class Stack<E> implements AbstractStack<E> {
    private Node<E> top;
    private int size;
    private static class Node<E>{
        E element;
        Node<E> previous;
        public Node( E element){
            this(element,null);
        }
        public Node(E element, Node<E> previous){
            this.element = element;
            this.previous = previous;
        }
    }

    @Override
    public void push(E element) {
        Node<E> newNode = new Node<E>(element);
        newNode.previous = top;
        top = newNode;
        this.size++;
    }

    @Override
    public E pop() {
        ensureNonEmpty();
        E element = this.top.element;
        Node<E> temp = this.top.previous;
        this.top.previous = null;
        this.top = temp;
        this.size--;
        return element;
    }

    private void ensureNonEmpty() {
        if (size == 0) throw new IllegalStateException("Stack is Empty!!! Can not pop!");
    }

    @Override
    public E peek() {
        ensureNonEmpty();
        return this.top.element;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return size ==0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Node<E> current = top;
            @Override
            public boolean hasNext() {
                return current!= null;
            }

            @Override
            public E next() {
                E element = current.element;
                this.current = this.current.previous;
                return element;
            }
        };
    }

    public String toString() {
        Node<E> current = top;
        StringBuilder result = new StringBuilder();
        while(current!=null){
            result.append(current.element+ "\n");
            current = current.previous;
        }
        return result.toString();
    }
}


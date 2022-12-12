package implement;
import interfaces.AbstractQueue;
import java.util.Iterator;

public class Queue<E> implements AbstractQueue<E> {
    private Node<E> head;
    private int size;
    private static class Node<E>{
        E element;
        Node<E> next;
        public Node( E element){
            this(element,null);
        }
        public Node(E element, Node<E> next){
            this.element = element;
            this.next = next;
        }
    }
    @Override
    public void offer(E element) {
        Node<E> node = new Node<>(element);
        if(this.head == null){
            head = node;
        }else{
            Node<E> current = this.head;
            while (current.next != null){
                current = current.next;
            }
            current.next = node;
        }
        this.size++;
    }
    @Override
    public E poll() {
        ensureNonEmpty();
        E element = this.head.element;
        if(this.size == 0) {
            this.head = null;
        } else {
            Node<E> next = this.head.next;
            this.head.next = null;
            this.head = next;
        }
        this.size--;
        return element;
    }
    private void ensureNonEmpty() {
        if (size == 0) throw new IllegalStateException("Stack is Empty!!! Can not poll!");
    }

    @Override
    public E peek() {
        ensureNonEmpty();
        return this.head.element;
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
        return new Iterator() {
            private Queue.Node<E> current = head;
            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Object next() {
                E element = current.element;
                this.current = current.next;
                return element;
            }
        };
    }

    @Override
    public String toString() {
        Node<E> current = head;
        StringBuilder result = new StringBuilder();
        while(current!=null){
            result.append(current.element+ " \n");
            current = current.next;
        }
        return result.toString();
    }
}


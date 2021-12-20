package Calculator;

import java.util.EmptyStackException;

public class myStack<T> {
    static class Node<T> {
        Node<T> next;
        T data;

        public Node(T data) {
            next = null;
            this.data = data;
        }
    }

    Node<T> head;
    int size;

    public myStack() {
        head = new Node<>(null);
        size = 0;
    }

    void push(T data) {
        if (size == 0) {
            head = new Node<>(data);
        }else {
            Node<T> node = new Node<>(data);
            Node<T> current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = node;
        }
        size++;
    }

    T pop() {
        if (size == 0) {
            throw new EmptyStackException();
        }else if(size == 1){
            T x = head.data;
            head = null;
            size--;
            return x;
        } else {
            Node<T> cur = head;
            while (cur.next.next != null) {
                cur = cur.next;
            }
            T x = cur.next.data;
            cur.next = null;
            size--;
            return x;
        }
    }

    T peek() {
        if (size == 0) {
            throw new EmptyStackException();
        } else {
            Node<T> current = head;
            while (current.next != null) {
                current = current.next;
            }
            return current.data;
        }
    }

    boolean isEmpty() {
        return size == 0;
    }

}

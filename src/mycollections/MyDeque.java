package mycollections;

import java.util.*;

public class MyDeque<T> implements Deque<T> {
    private Node<T> main = new Node<>(null, null, null);
    private Node<T> start = main;
    private int size = 0;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return main.value == null;
    }

    @Override
    public boolean contains(Object o) {
        Node<T> node = start;
        int i = 0;
        while (i < size) {
            if (node.value.equals(o)) {
                return true;
            }
            node = node.next;
            i++;
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        Iterator<T> iterator = new Iterator<T>() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public T next() {
                return null;
            }
        };
        return iterator;
    }

    @Override
    public Iterator<T> descendingIterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        Node<T> node = start;

        int i = 0;
        while (node != null) {
            array[i++] = node.value;
            node = node.next;
        }

        return array;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {

        int allSize = size + a.length;
        Object[] array = new Object[allSize];
        Node<T> node = start;

        int i = 0;
        while (node != null) {
            array[i++] = node.value;
            node = node.next;
        }

        int j = 0;
        while (i < allSize) {
            array[i] = a[j];
            i++;
            j++;
        }

        a = Arrays.copyOf(a, allSize);

        System.arraycopy(array, 0, a, 0, allSize);

        return a;
    }

    private void increaseSize() {
        size++;
    }

    private void decreaseSize() {
        if (size < 0) {
            throw new IndexOutOfBoundsException("Size can't be negative");
        }
        size--;
    }

    @Override
    public void addFirst(T t) {
        increaseSize();
        Node<T> n = new Node(t, null, start);
        if (start != null) {
            start.previous = n;
        }

        start = n;
    }

    @Override
    public void addLast(T t) {
        increaseSize();
        Node<T> n = new Node<>(t, main, null);
        main.next = n;
        main = n;
    }

    @Override
    public boolean offerFirst(T t) {
        addFirst(t);
        return true;
    }

    @Override
    public boolean offerLast(T t) {
        addLast(t);
        return true;
    }

    @Override
    public T removeFirst() {
        decreaseSize();
        checkForEmptyStart();

        T value = start.value;

        if (start.next == null) {
            remove();
            return value;
        } else {
            start = start.next;
            start.previous = null;
        }

        return value;
    }

    private void checkForEmptyMain() {
        if (main == null) {
            throw new NoSuchElementException("Deque is empy");
        }
    }

    private void checkForEmptyStart() {
        if (start == null) {
            throw new NoSuchElementException("Deque is empy");
        }
    }

    @Override
    public T removeLast() {
        decreaseSize();
        checkForEmptyStart();

        T value = main.value;

        if (main.previous == null) {
            remove();
            return value;
        } else {
            main = main.previous;
            main.next = null;
        }

        return value;
    }

    @Override
    public T pollFirst() {
        decreaseSize();
        T value = start.value;

        if (start == null) {
            return null;
        }

        if (start.next == null) {
            remove();
        } else {
            start = start.next;
            start.previous = null;
        }

        return value;
    }

    @Override
    public T pollLast() {
        decreaseSize();
        T value = main.value;

        if (main == null) {
            return null;
        }

        if (main.previous == null) {
            remove();
        } else {
            main = main.previous;
            main.next = null;
        }

        return value;
    }

    @Override
    public T getFirst() {
        checkForEmptyStart();
        return start.value;
    }

    @Override
    public T getLast() {
        checkForEmptyMain();
        return main.value;
    }

    @Override
    public T peekFirst() {
        if (start == null) {
            return null;
        }

        return start.value;
    }

    @Override
    public T peekLast() {
        if (main == null) {
            return null;
        }

        return main.value;
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        if (start.value.equals(o)) {
            removeFirst();
            return true;
        }

        return false;
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        if (main.value.equals(o)) {
            removeLast();
            return true;
        }

        return false;
    }

    @Override
    public boolean add(T t) {
        increaseSize();
        if (main.value != null) {
            main = main.next = new Node<>(t, main, null);
        }

        main.value = t;
        return true;
    }

    @Override
    public boolean offer(T t) {
        return add(t);
    }

    @Override
    public T remove() {
        size = 0;
        T val = start.value;
        main = new Node<>(null, null, null);
        start = main;
        return val;
    }

    @Override
    public T poll() {
        return pollFirst();
    }

    @Override
    public T element() {
        return getFirst();
    }

    public void set(int index, T t) {
        if(index < 0 || index >= size) {
            throw new IllegalArgumentException("Your index must be greater than 0, and less than size: " + size);
        }

        Node<T> node = start;
        int i = 0;
        if(index < size / 2) {
            while(i < index) {
                node = node.next;
                i++;
            }
        } else {
            node = main;
            i = size - 1;
            while(i > index) {
                node = node.previous;
                i--;
            }
        }

        node.value = t;

//        while(i < index) {
//            node = node.next;
//            i++;
//        }
//        node.value = t;
    }

    // Данный код можно использовать как set значения
//     //       Node<T> newNode = start;
//    int count = 0;
//        while(count != index) {
//        start = start.next;
//        count++;
//    }
////        start = new Node<T>(t, start, start.next);
//    start.previous = new Node<T>(t, start.previous, start.next);
//
//        while(start.previous != null) {
//        start = start.previous;
//    }
//
//        return true;

    //        Node<T> nd = new Node<T>(t, start, start.next);
//        start.next.previous = nd;
//        start.next = nd;

//        Node<T> nd = new Node<T>(t, start.previous, start.next);
//        start.previous = nd;
//        start.next = nd;

    public boolean add(int index, T t) {
        increaseSize();
        Node<T> current = start;
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index can't be under 0");
        }

        int count = 0;
        while (count != index) {
            if (current.next == null) {
                throw new IndexOutOfBoundsException("Index out of bound");
            }
            current = current.next;
            count++;
        }

        Node<T> nd = new Node<T>(t, current.previous, current);
        if (current.previous != null) {
            current.previous.next = nd;
        }
        current.previous = nd;

        if (index == 0) {
            start = nd;
        }

        return true;
    }

    @Override
    public boolean remove(Object o) {
        return removeFirstOccurrence(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        Node<T> node = start;
        int counter = 0;

        for(int i = 0; i < c.size(); i++) {

            while(node != null) {
                if(c.toArray()[i].equals(node.value)) {
                    counter++;
                    break;
                }
                node = node.next;
            }
            node = start;
        }

        return counter == c.size();
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        boolean modified = false;

        if(c.size() <= 0) {
            throw new NoSuchElementException("Your collection does not contain any elements");
        }

        for(int i = 0; i < c.size(); i++) {
            if(main.next == null) {
                main = main.next = new Node<>((T)c.toArray()[i], main, null);
                modified = true;
            }
        }
        size += c.size();
        return modified;
    }

    @Override
    public void push(T t) {
        addFirst(t);
    }

    @Override
    public T pop() {
        return removeFirst();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        Node<T> node = start;
        boolean modified = false;

        while (node != null) {
            if (c.contains(node.value)) {
                isModified(node);
                modified = true;
            }
            node = node.next;
        }

        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        Node<T> node = start;
        boolean modified = false;

        while (node != null) {
            if (!c.contains(node.value)) {
                isModified(node);
                modified = true;
            }
            node = node.next;
        }

        return modified;
    }

    private void isModified(Node<T> node) {
        if (node.previous != null) {
            node.previous.next = node.next;
        } else {
            start = node.next;
        }

        if(node.next != null) {
            node.next.previous = node.previous;
        } else {
            main = node.previous;
        }
        decreaseSize();
    }

    @Override
    public void clear() {
        remove();
    }

    @Override
    public T peek() {
        return peekFirst();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        Node<T> toStringStart = start;
        str.append("[");
        while (toStringStart != null) {
            str.append(toStringStart.value);
            toStringStart = toStringStart.next;
            if (toStringStart != null) {
                str.append(", ");
            }
        }
        str.append("]");
        return str.toString();
    }

    // Изменить класс и поля на private
    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node previous;

        private Node(T value, Node<T> previous, Node<T> next) {
            this.value = value;
            this.previous = previous;
            this.next = next;
        }
    }
}

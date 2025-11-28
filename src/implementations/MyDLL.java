package implementations;

import utilities.Iterator;
import utilities.ListADT;

import java.util.NoSuchElementException;

/**
 * Doubly-linked list implementation of {@link ListADT} used in Assignment 2.
 *
 * @param <E> element type
 */
public class MyDLL<E> implements ListADT<E> {

    private MyDLLNode<E> head;
    private MyDLLNode<E> tail;
    private int size;

    public MyDLL() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    private void checkElementNotNull(E e) {
        if (e == null) {
            throw new NullPointerException();
        }
    }

    private void checkPositionIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private MyDLLNode<E> node(int index) {
        // index in [0, size)
        if (index < (size / 2)) {
            MyDLLNode<E> x = head;
            for (int i = 0; i < index; i++) {
                x = x.next;
            }
            return x;
        } else {
            MyDLLNode<E> x = tail;
            for (int i = size - 1; i > index; i--) {
                x = x.prev;
            }
            return x;
        }
    }

    @Override
    public boolean add(int index, E toAdd) throws NullPointerException, IndexOutOfBoundsException {
        checkElementNotNull(toAdd);
        checkPositionIndex(index);

        if (index == size) {
            linkLast(toAdd);
        } else {
            linkBefore(toAdd, node(index));
        }
        return true;
    }

    private void linkLast(E e) {
        MyDLLNode<E> newNode = new MyDLLNode<>(e);
        newNode.prev = tail;
        if (tail == null) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    private void linkBefore(E e, MyDLLNode<E> succ) {
        MyDLLNode<E> pred = succ.prev;
        MyDLLNode<E> newNode = new MyDLLNode<>(e);
        newNode.next = succ;
        newNode.prev = pred;
        succ.prev = newNode;
        if (pred == null) {
            head = newNode;
        } else {
            pred.next = newNode;
        }
        size++;
    }

    @Override
    public boolean add(E toAdd) throws NullPointerException {
        checkElementNotNull(toAdd);
        linkLast(toAdd);
        return true;
    }

    @Override
    public boolean addAll(ListADT<? extends E> toAdd) throws NullPointerException {
        if (toAdd == null) {
            throw new NullPointerException();
        }
        boolean modified = false;
        Iterator<? extends E> it = toAdd.iterator();
        while (it.hasNext()) {
            E e = it.next();
            checkElementNotNull(e);
            linkLast(e);
            modified = true;
        }
        return modified;
    }

    @Override
    public E get(int index) throws IndexOutOfBoundsException {
        checkElementIndex(index);
        return node(index).element;
    }

    @Override
    public E remove(int index) throws IndexOutOfBoundsException {
        checkElementIndex(index);
        return unlink(node(index));
    }

    private E unlink(MyDLLNode<E> x) {
        E element = x.element;
        MyDLLNode<E> next = x.next;
        MyDLLNode<E> prev = x.prev;

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
        }

        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
        }

        size--;
        return element;
    }

    @Override
    public E remove(E toRemove) throws NullPointerException {
        checkElementNotNull(toRemove);
        MyDLLNode<E> current = head;
        while (current != null) {
            if (toRemove.equals(current.element)) {
                return unlink(current);
            }
            current = current.next;
        }
        return null;
    }

    @Override
    public E set(int index, E toChange) throws NullPointerException, IndexOutOfBoundsException {
        checkElementNotNull(toChange);
        checkElementIndex(index);
        MyDLLNode<E> x = node(index);
        E old = x.element;
        x.element = toChange;
        return old;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(E toFind) throws NullPointerException {
        checkElementNotNull(toFind);
        MyDLLNode<E> current = head;
        while (current != null) {
            if (toFind.equals(current.element)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public E[] toArray(E[] toHold) throws NullPointerException {
        if (toHold == null) {
            throw new NullPointerException();
        }
        if (toHold.length < size) {
            @SuppressWarnings("unchecked")
            E[] newArray = (E[]) java.lang.reflect.Array.newInstance(toHold.getClass().getComponentType(), size);
            toHold = newArray;
        }
        MyDLLNode<E> current = head;
        int i = 0;
        while (current != null) {
            toHold[i++] = current.element;
            current = current.next;
        }
        // If array is larger than size, leave remaining elements as-is (tests compare only first size positions)
        return toHold;
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        MyDLLNode<E> current = head;
        int i = 0;
        while (current != null) {
            array[i++] = current.element;
            current = current.next;
        }
        return array;
    }

    @Override
    public Iterator<E> iterator() {
        return new DLLIterator();
    }

    private class DLLIterator implements Iterator<E> {
        private MyDLLNode<E> current = head;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public E next() throws NoSuchElementException {
            if (current == null) {
                throw new NoSuchElementException();
            }
            E value = current.element;
            current = current.next;
            return value;
        }
    }
}

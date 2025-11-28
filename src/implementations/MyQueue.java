package implementations;

import utilities.QueueADT;
import utilities.Iterator;
import utilities.EmptyQueueException;

public class MyQueue<E> implements QueueADT<E> {
    private MyDLL<E> list;

    public MyQueue() {
        list = new MyDLL<E>();
    }

    @Override
    public void enqueue(E toAdd) throws NullPointerException {
        if (toAdd == null) {
            throw new NullPointerException("Cannot enqueue null elements");
        }
        list.add(toAdd);
    }

    @Override
    public E dequeue() throws EmptyQueueException {
        if (isEmpty()) {
            throw new EmptyQueueException("Queue is empty");
        }
        return list.remove(0);
    }

    @Override
    public E peek() throws EmptyQueueException {
        if (isEmpty()) {
            throw new EmptyQueueException("Queue is empty");
        }
        return list.get(0);
    }

    @Override
    public void dequeueAll() {
        list.clear();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(E toFind) throws NullPointerException {
        return list.contains(toFind);
    }

    @Override
    public int search(E toFind) {
        if (toFind == null) {
            throw new NullPointerException("Cannot search for null elements");
        }
        
        for (int i = 0; i < list.size(); i++) {
            if (toFind.equals(list.get(i))) {
                return i + 1;
            }
        }
        return -1;
    }

    @Override
    public Iterator<E> iterator() {
        return list.iterator();
    }

    @Override
    public boolean equals(QueueADT<E> that) {
        if (that == null) return false;
        if (this.size() != that.size()) return false;
        
        Object[] thisArray = this.toArray();
        Object[] thatArray = that.toArray();
        
        for (int i = 0; i < thisArray.length; i++) {
            if (thisArray[i] == null && thatArray[i] == null) {
                continue;
            }
            if (thisArray[i] == null || thatArray[i] == null) {
                return false;
            }
            if (!thisArray[i].equals(thatArray[i])) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @Override
    public E[] toArray(E[] holder) throws NullPointerException {
        return list.toArray(holder);
    }

    @Override
    public boolean isFull() {
        return false;
    }

    @Override
    public int size() {
        return list.size();
    }
}

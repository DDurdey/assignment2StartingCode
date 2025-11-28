package implementations;

import utilities.QueueADT;
import utilities.Iterator;
import utilities.EmptyQueueException;

/**
 * A queue implementation using MyDLL (Doubly Linked List) as the underlying data structure.
 * This class provides FIFO (First-In-First-Out) operations and implements
 * the QueueADT interface.
 *
 * @param <E> the type of elements held in this queue
 */
public class MyQueue<E> implements QueueADT<E> {
    
    /**
     * The underlying doubly linked list used to store queue elements.
     */
    private MyDLL<E> list;

    /**
     * Constructs an empty queue.
     */
    public MyQueue() {
        list = new MyDLL<E>();
    }

    /**
     * Inserts the specified element into this queue.
     *
     * @param toAdd the element to add
     * @throws NullPointerException if the specified element is null
     */
    @Override
    public void enqueue(E toAdd) throws NullPointerException {
        if (toAdd == null) {
            throw new NullPointerException("Cannot enqueue null elements");
        }
        list.add(toAdd);
    }

    /**
     * Retrieves and removes the head of this queue.
     *
     * @return the head of this queue
     * @throws EmptyQueueException if this queue is empty
     */
    @Override
    public E dequeue() throws EmptyQueueException {
        if (isEmpty()) {
            throw new EmptyQueueException("Queue is empty");
        }
        return list.remove(0);
    }

    /**
     * Retrieves, but does not remove, the head of this queue.
     *
     * @return the head of this queue
     * @throws EmptyQueueException if this queue is empty
     */
    @Override
    public E peek() throws EmptyQueueException {
        if (isEmpty()) {
            throw new EmptyQueueException("Queue is empty");
        }
        return list.get(0);
    }

    /**
     * Removes all elements from this queue.
     */
    @Override
    public void dequeueAll() {
        list.clear();
    }

    /**
     * Tests if this queue is empty.
     *
     * @return true if this queue contains no elements
     */
    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * Returns true if this queue contains the specified element.
     *
     * @param toFind element whose presence in this queue is to be tested
     * @return true if this queue contains the specified element
     * @throws NullPointerException if the specified element is null
     */
    @Override
    public boolean contains(E toFind) throws NullPointerException {
        return list.contains(toFind);
    }

    /**
     * Returns the 1-based position where an object is on this queue.
     * The first item is at distance 1.
     *
     * @param toFind the desired object
     * @return the 1-based position from the front of the queue where the object is located,
     *         or -1 if the object is not on the queue
     * @throws NullPointerException if the specified element is null
     */
    @Override
    public int search(E toFind) {
        if (toFind == null) {
            throw new NullPointerException("Cannot search for null elements");
        }
        
        // Search from front to rear - queue search is 1-based from front
        for (int i = 0; i < list.size(); i++) {
            if (toFind.equals(list.get(i))) {
                return i + 1; // 1-based position from front
            }
        }
        return -1;
    }

    /**
     * Returns an iterator over the elements in this queue in proper sequence.
     *
     * @return an iterator over the elements in this queue
     */
    @Override
    public Iterator<E> iterator() {
        return list.iterator();
    }

    /**
     * Compares this queue with the specified queue for equality.
     *
     * @param that the queue to be compared for equality with this queue
     * @return true if the specified queue is equal to this queue
     */
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

    /**
     * Returns an array containing all elements in this queue in proper sequence.
     *
     * @return an array containing all elements in this queue
     */
    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    /**
     * Returns an array containing all elements in this queue in proper sequence;
     * the runtime type of the returned array is that of the specified array.
     *
     * @param holder the array into which the elements of this queue are to be stored
     * @return an array containing the elements of this queue
     * @throws NullPointerException if the specified array is null
     */
    @Override
    public E[] toArray(E[] holder) throws NullPointerException {
        return list.toArray(holder);
    }

    /**
     * Returns true if the number of items in the queue equals the length.
     * Since this implementation uses a dynamically sized doubly linked list,
     * the queue never becomes full.
     *
     * @return false always, as this queue has no fixed capacity
     */
    @Override
    public boolean isFull() {
        return false;
    }

    /**
     * Returns the number of elements in this queue.
     *
     * @return the number of elements in this queue
     */
    @Override
    public int size() {
        return list.size();
    }
}

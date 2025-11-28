package implementations;

import utilities.StackADT;
import utilities.Iterator;
import java.util.EmptyStackException;

/**
 * A stack implementation using MyArrayList as the underlying data structure.
 * This class provides LIFO (Last-In-First-Out) operations and implements
 * the StackADT interface.
 *
 * @param <E> the type of elements held in this stack
 */
public class MyStack<E> implements StackADT<E> {
    
    /**
     * The underlying list used to store stack elements.
     */
    private MyArrayList<E> list;

    /**
     * Constructs an empty stack.
     */
    public MyStack() {
        list = new MyArrayList<E>();
    }

    /**
     * Pushes an item onto the top of this stack.
     *
     * @param toAdd the item to be pushed onto this stack
     * @throws NullPointerException if the specified element is null
     */
    @Override
    public void push(E toAdd) throws NullPointerException {
        if (toAdd == null) {
            throw new NullPointerException("Cannot push null elements to the stack");
        }
        list.add(toAdd);
    }

    /**
     * Removes the object at the top of this stack and returns that object.
     *
     * @return the object at the top of this stack
     * @throws EmptyStackException if this stack is empty
     */
    @Override
    public E pop() throws EmptyStackException {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return list.remove(list.size() - 1);
    }

    /**
     * Looks at the object at the top of this stack without removing it.
     *
     * @return the object at the top of this stack
     * @throws EmptyStackException if this stack is empty
     */
    @Override
    public E peek() throws EmptyStackException {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return list.get(list.size() - 1);
    }

    /**
     * Removes all elements from this stack.
     */
    @Override
    public void clear() {
        list.clear();
    }

    /**
     * Tests if this stack is empty.
     *
     * @return true if this stack contains no elements
     */
    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * Returns an array containing all elements in this stack in proper sequence.
     *
     * @return an array containing all elements in this stack
     */
    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    /**
     * Returns an array containing all elements in this stack in proper sequence;
     * the runtime type of the returned array is that of the specified array.
     *
     * @param holder the array into which the elements of this stack are to be stored
     * @return an array containing the elements of this stack
     * @throws NullPointerException if the specified array is null
     */
    @Override
    public E[] toArray(E[] holder) throws NullPointerException {
        return list.toArray(holder);
    }

    /**
     * Returns true if this stack contains the specified element.
     *
     * @param toFind element whose presence in this stack is to be tested
     * @return true if this stack contains the specified element
     * @throws NullPointerException if the specified element is null
     */
    @Override
    public boolean contains(E toFind) throws NullPointerException {
        return list.contains(toFind);
    }

    /**
     * Returns the 1-based position where an object is on this stack.
     * The topmost item is at distance 1.
     *
     * @param toFind the desired object
     * @return the 1-based position from the top of the stack where the object is located,
     *         or -1 if the object is not on the stack
     * @throws NullPointerException if the specified element is null
     */
    @Override
    public int search(E toFind) {
        if (toFind == null) {
            throw new NullPointerException("Cannot search for null elements");
        }
        
        // Search from top (end) to bottom (start) - stack search is 1-based from top
        for (int i = list.size() - 1; i >= 0; i--) {
            if (toFind.equals(list.get(i))) {
                return list.size() - i; // 1-based position from top
            }
        }
        return -1;
    }

    /**
     * Returns an iterator over the elements in this stack in proper sequence.
     *
     * @return an iterator over the elements in this stack
     */
    @Override
    public Iterator<E> iterator() {
        return list.iterator();
    }

    /**
     * Compares this stack with the specified stack for equality.
     *
     * @param that the stack to be compared for equality with this stack
     * @return true if the specified stack is equal to this stack
     */
    @Override
    public boolean equals(StackADT<E> that) {
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
     * Returns the number of elements in this stack.
     *
     * @return the number of elements in this stack
     */
    @Override
    public int size() {
        return list.size();
    }

    /**
     * Returns true if the number of items in the stack equals the length.
     * Since this implementation uses a dynamically resizing ArrayList,
     * the stack never overflows.
     *
     * @return false always, as this stack has no fixed capacity
     */
    @Override
    public boolean stackOverflow() {
        return false;
    }
}

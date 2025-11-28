package utilities;

import java.util.NoSuchElementException;

/**
 * MyArrayList is a dynamic array-based implementation of the ListADT interface.
 * It automatically resizes when capacity is exceeded using Arrays.copyOf().
 * Supports generic type parameters and includes a custom iterator implementation.
 * 
 * NOTE: When resizing, capacity doubles. All methods reject null elements.
 * 
 * @param <E> The type of elements this list holds.
 */
public class MyArrayList<E> implements ListADT<E> {
    
    private E[] items;
    private int size;
    private static final int INITIAL_CAPACITY = 10;

    /**
     * Constructor: Initializes the list with default capacity of 10.
     */
    @SuppressWarnings("unchecked")
    public MyArrayList() {
        items = (E[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the current number of elements in the list.
     * @return the number of elements currently in the list
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Removes all elements from the list.
     */
    @SuppressWarnings("unchecked")
	@Override
    public void clear() {
        items = (E[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Inserts an element at the specified index.
     * Shifts elements at index and beyond to the right.
     * 
     * @param index the position to insert at (0 to size)
     * @param toAdd the element to add
     * @return true if successfully added
     * @throws NullPointerException if toAdd is null
     * @throws IndexOutOfBoundsException if index < 0 or index > size
     */
    @Override
    public boolean add(int index, E toAdd) throws NullPointerException, IndexOutOfBoundsException {
        if (toAdd == null) {
            throw new NullPointerException("Cannot add null elements to the list.");
        }
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }

        // Resize if necessary
        if (size >= items.length) {
            ensureCapacity();
        }

        // Shift elements to the right
        for (int i = size; i > index; i--) {
            items[i] = items[i - 1];
        }

        items[index] = toAdd;
        size++;
        return true;
    }

    /**
     * Appends an element to the end of the list.
     * 
     * @param toAdd the element to add
     * @return true if successfully added
     * @throws NullPointerException if toAdd is null
     */
    @Override
    public boolean add(E toAdd) throws NullPointerException {
        if (toAdd == null) {
            throw new NullPointerException("Cannot add null elements to the list.");
        }

        // Resize if necessary
        if (size >= items.length) {
            ensureCapacity();
        }

        items[size] = toAdd;
        size++;
        return true;
    }

    /**
     * Appends all elements from another ListADT to the end of this list.
     * 
     * @param toAdd the list of elements to add
     * @return true if all elements were added successfully
     * @throws NullPointerException if toAdd is null or contains null elements
     */
    @Override
    public boolean addAll(ListADT<? extends E> toAdd) throws NullPointerException {
        if (toAdd == null) {
            throw new NullPointerException("Cannot add a null list.");
        }

        Iterator<? extends E> iterator = toAdd.iterator();
        while (iterator.hasNext()) {
            E element = iterator.next();
            if (element == null) {
                throw new NullPointerException("Cannot add null elements from the provided list.");
            }
            add(element);
        }
        return true;
    }

    /**
     * Returns the element at the specified index.
     * 
     * @param index the position of the element
     * @return the element at the specified index
     * @throws IndexOutOfBoundsException if index < 0 or index >= size
     */
    @Override
    public E get(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
        return items[index];
    }

    /**
     * Removes and returns the element at the specified index.
     * Shifts subsequent elements to the left.
     * 
     * @param index the position of the element to remove
     * @return the removed element
     * @throws IndexOutOfBoundsException if index < 0 or index >= size
     */
    @Override
    public E remove(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }

        E removed = items[index];

        // Shift elements to the left
        for (int i = index; i < size - 1; i++) {
            items[i] = items[i + 1];
        }

        items[size - 1] = null;
        size--;
        return removed;
    }

    /**
     * Removes the first occurrence of the specified element.
     * 
     * @param toRemove the element to remove
     * @return the removed element, or null if not found
     * @throws NullPointerException if toRemove is null
     */
    @Override
    public E remove(E toRemove) throws NullPointerException {
        if (toRemove == null) {
            throw new NullPointerException("Cannot remove null elements from the list.");
        }

        for (int i = 0; i < size; i++) {
            if (items[i].equals(toRemove)) {
                return remove(i);
            }
        }
        return null;
    }

    /**
     * Replaces the element at the specified index with a new element.
     * 
     * @param index the position of the element to replace
     * @param toChange the new element
     * @return the previous element at that position
     * @throws NullPointerException if toChange is null
     * @throws IndexOutOfBoundsException if index < 0 or index >= size
     */
    @Override
    public E set(int index, E toChange) throws NullPointerException, IndexOutOfBoundsException {
        if (toChange == null) {
            throw new NullPointerException("Cannot set a null element in the list.");
        }
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }

        E previous = items[index];
        items[index] = toChange;
        return previous;
    }

    /**
     * Checks if the list is empty.
     * 
     * @return true if the list contains no elements
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Checks if the list contains the specified element.
     * 
     * @param toFind the element to search for
     * @return true if the element is found
     * @throws NullPointerException if toFind is null
     */
    @Override
    public boolean contains(E toFind) throws NullPointerException {
        if (toFind == null) {
            throw new NullPointerException("Cannot search for null elements.");
        }

        for (int i = 0; i < size; i++) {
            if (items[i].equals(toFind)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Copies the list elements into the provided array.
     * If the array is too small, a new array is allocated.
     * 
     * @param toHold the array to copy into
     * @return an array containing all list elements
     * @throws NullPointerException if toHold is null
     */
    @Override
    @SuppressWarnings("unchecked")
    public E[] toArray(E[] toHold) throws NullPointerException {
        if (toHold == null) {
            throw new NullPointerException("Array cannot be null.");
        }

        if (toHold.length < size) {
            // Create a new array of the same runtime type as toHold
            return java.util.Arrays.copyOf(items, size, (Class<? extends E[]>) toHold.getClass());
        }

        // Use the provided array, copy elements into it
        for (int i = 0; i < size; i++) {
            toHold[i] = items[i];
        }

        // If there is extra space, set the first unused element to null
        if (toHold.length > size) {
            toHold[size] = null;
        }

        return toHold;
    }


    /**
     * Returns a new Object array containing all list elements.
     * 
     * @return an Object array with all elements
     */
    @Override
    public Object[] toArray() {
        Object[] result = new Object[size];
        for (int i = 0; i < size; i++) {
            result[i] = items[i];
        }
        return result;
    }

    /**
     * Returns an iterator over the list elements.
     * The iterator makes a deep copy of the list data.
     * 
     * @return an Iterator<E> for this list
     */
    @Override
    public Iterator<E> iterator() {
        return new MyArrayListIterator();
    }

    /**
     * Doubles the capacity of the internal array using Arrays.copyOf().
     * Called when size reaches capacity.
     */
    @SuppressWarnings("unchecked")
    private void ensureCapacity() {
        int newCapacity = items.length * 2;
        items = java.util.Arrays.copyOf(items, newCapacity);
    }

    /**
     * Inner class: Iterator implementation for MyArrayList.
     * Makes a deep copy of the list to iterate safely.
     */
    private class MyArrayListIterator implements Iterator<E> {
        private E[] iteratorCopy;
        private int currentIndex;

        @SuppressWarnings("unchecked")
        public MyArrayListIterator() {
            // Create a deep copy of the current list
            iteratorCopy = (E[]) new Object[size];
            for (int i = 0; i < size; i++) {
                iteratorCopy[i] = items[i];
            }
            currentIndex = 0;
        }

        @Override
        public boolean hasNext() {
            return currentIndex < iteratorCopy.length;
        }

        @Override
        public E next() throws NoSuchElementException {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements to iterate.");
            }
            return iteratorCopy[currentIndex++];
        }
    }

    /**
     * Main method for basic testing.
     */
    public static void main(String[] args) {
        MyArrayList<String> list = new MyArrayList<>();

        // Test add
        list.add("Hello");
        list.add("World");
        list.add(1, "Beautiful");

        // Test iteration
        System.out.println("List contents:");
        Iterator<String> iter = list.iterator();
        while (iter.hasNext()) {
            System.out.println("  " + iter.next());
        }

        // Test get and remove
        System.out.println("\nElement at index 1: " + list.get(1));
        String removed = list.remove(1);
        System.out.println("Removed: " + removed);

        // Test contains and size
        System.out.println("List contains 'World': " + list.contains("World"));
        System.out.println("List size: " + list.size());
    }
}
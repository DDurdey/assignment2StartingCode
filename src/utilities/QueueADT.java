package utilities;

/**
 * The Queue Interface will define the behavior of the FIRST IN FIRST OUT Queue structure.
 * 
 * @param <E> The type of Elements stored in the queue, E = Element.
 * 
 * Preconditions:
 *  The stack may not accept null elements.
 *  
 * Postconditions:
 *  Elements queue from the rear and exit from the front.
 */
public interface QueueADT<E> {
	
	/**
	 * Inserts an element at the rear of the queue.
	 * 
	 * Preconditions:
	 * 	Element cannot be null.
	 * 
	 * Postcondition:
	 * 	The element is added to the rear of the queue.
	 * 
	 * @param element.
	 * @throws NullPointerException - if the element is null.
	 */
	void enqueue(E element);
	
	/**
	 * Removes and returns the element at the front of the queue.
	 * 
	 * Precondition:
	 * 	Queue must not be empty.
	 * 
	 * Postcondition:
	 * 	The element at the front is removed and returned.
	 * 
	 * @return The element stored at the front of the queue.
	 * @throws EmptyQueueException - if the queue is empty.
	 */
	E dequeue();
	
	/**
	 * Returns the front element of the queue but DOES NOT REMOVE the element.
	 * 
	 * Precondition:
	 * 	The queue must not be empty.
	 * 
	 * Postcondition:
	 * 	The queue remain the same.
	 * 
	 * @return The element at the front of the queue.
	 * @throws EmptyQueueException - if the queue is empty.
	 */
	E peek();
	
	/**
	 * Returns the number of elements in the queue.
	 * 
	 * Precondition:
	 * 	None.
	 * 
	 * Postcondition:
	 * 	No change to the queue.
	 * 
	 * @return The queue size.
	 */
	int size();
	
	/**
	 * Removes all elements from the queue.
	 * 
	 * Precondition:
	 * 	None.
	 * 
	 * Postcondition:
	 * 	The queue will be fully empty.
	 */
	void clear();
	
	/**
	 * Determines if the queue is empty or not.
	 * 
	 * Precondition:
	 * 	None.
	 * 
	 * PostCondition:
	 * 	No change to queue.
	 * 
	 * @return true if the queue is empty, false if it is not.
	 */
	boolean isEmpty();
}

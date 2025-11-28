package utilities;


/**
 * The Stack Interface will define the behavior of the LAST IN FIRST OUT stack structure.
 * 
 * @param <E> The type of Elements stored in the stack, E = Element.
 * 
 * Preconditions:
 *  The stack may not accept null elements.
 *  
 * Postconditions:
 *  Elements are added and removed based on LAST IN FIRST OUT.
 */
public interface StackADT<E> {

	/**
	 * Pushes a new element to the top of the stack.
	 * 
	 * Precondition:
	 *  Element CANNOT be Null.
	 *  
	 * Postcondition:
	 *  THe element is stored at the top of the stack.
	 *  
	 * @param element
	 * @throws NullPointerException - if element is null.
	 */
	void push(E element);
	
	/**
	 * Removes AND returns the top element from the stack.
	 * 
	 * Preconditions:
	 *  The stack must not be empty.
	 *  
	 * Postcondition:
	 * 	The top element is removed and returned.
	 * 
	 * @return The element at the top of the stack.
	 * @throws NoSuchElementException - if the stack is empty.
	 */
	E pop();
	
	/**
	 * Returns the top element of the stack but DOES NOT REMOVE the element.
	 * 
	 * Precondition:
	 * 	The stack must not be empty.
	 * 
	 * Postcondition:
	 * 	The stack remain the same.
	 * 
	 * @return The element at the top of the stack.
	 * @throws NoSuchElementException - if the stack is empty.
	 */
	E peek();
	
	/**
	 * Returns the number of elements in the stack.
	 * 
	 * Precondition:
	 * 	None.
	 * 
	 * Postcondition:
	 * 	No change to the stack.
	 * 
	 * @return The stack size.
	 */
	int size();
	
	/**
	 * Removes all elements from the stack.
	 * 
	 * Precondition:
	 * 	None.
	 * 
	 * Postcondition:
	 * 	The stack will be fully empty.
	 */
	void clear();
	
	/**
	 * Determines if the stack is empty or not.
	 * 
	 * Precondition:
	 * 	None.
	 * 
	 * PostCondition:
	 * 	No change to stack.
	 * 
	 * @return true if the stack is empty, false if it is not.
	 */
	boolean isEmpty();
}

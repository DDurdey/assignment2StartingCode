package implementations;

import utilities.StackADT;
import utilities.Iterator;
import java.util.EmptyStackException;

public class MyStack<E> implements StackADT<E> {
    private MyArrayList<E> list;

    public MyStack() {
        list = new MyArrayList<E>();
    }

    @Override
    public void push(E toAdd) throws NullPointerException {
        if (toAdd == null) {
            throw new NullPointerException("Cannot push null elements to the stack");
        }
        list.add(toAdd);
    }

    @Override
    public E pop() throws EmptyStackException {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return list.remove(list.size() - 1);
    }

    @Override
    public E peek() throws EmptyStackException {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return list.get(list.size() - 1);
    }

    
}

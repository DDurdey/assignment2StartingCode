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

    
}

package implementations;

/**
 * Doubly-linked list node used internally by {@link MyDLL}.
 *
 * @param <E> element type stored in the node
 */
class MyDLLNode<E> {
    E element;
    MyDLLNode<E> next;
    MyDLLNode<E> prev;

    MyDLLNode(E element) {
        this.element = element;
    }
}

package advance.algo.and.data.struct.book;

public interface P1PriorityQueueAPI<T> {
    void insert(T element, int priority);
    T peek();
    void update(T element, int priority);
    void remove(T element);
    T top();

    boolean isEmpty();
}

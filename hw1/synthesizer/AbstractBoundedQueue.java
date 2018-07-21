package synthesizer;

import java.util.Iterator;

public abstract class AbstractBoundedQueue<T> implements BoundedQueue<T>{
    protected int fillCount;
    protected int capacity;

    @Override
    public int capacity() {
        return capacity;
    }

    @Override
    public int fillCount() {
        return fillCount;
    }

    @Override
    public boolean isEmpty() {
        return capacity == 0;
    }

    @Override
    public boolean isFull() {
        return capacity == fillCount;
    }

    public abstract T peek();
    public abstract T dequeue();
    public abstract void enqueue(T x);
}

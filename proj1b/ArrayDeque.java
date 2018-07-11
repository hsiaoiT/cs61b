public class ArrayDeque<T> implements Deque<T>{
    private int size;
    private T[] items;
    private int itemLength = 8;
    private int nextfirst;
    private int nextlast;

    /** Creates empty list. */
    public ArrayDeque() {
        items = (T[]) new Object[itemLength];
        nextfirst = itemLength - 1;
        size = 0;
        nextlast = size;
    }

    /** Resizes the underlying array to the target capacity. */
    private void resize() {  // think how to move when resize ----------------need to fix
        T[] a = (T[]) new Object[needLength()];
        System.arraycopy(items, nextfirst+1, a, 0, itemLength-1-nextfirst);
        System.arraycopy(items, 0, a, itemLength-1-nextfirst, size-itemLength+1+nextfirst);
        items = a;
        itemLength = needLength();
        nextfirst = itemLength - 1;
        nextlast = size;

    } // usage ratio R = size / items.length, R < 0.25、resize

    /** The need of the length of array*/
    private int needLength() {
        int needL = 0;
        if (itemLength < 16) {
            needL = 16;
        }
        else {
            needL = 4 * size;
        }
        return needL;
    }

    /** How to move the pointers. */
    private void moveNextfirst() {
        if (nextfirst == 0) {
            nextfirst = itemLength-1;
        }
        nextfirst -= 1;
    }
    private void moveNextlast() {
        nextlast += 1;
        if (nextlast == itemLength) {
            nextlast = 1;
        }
    }


    /** Adds x to the front of the list. */
    @Override
    public void addFirst(T x) {
        if (size == itemLength) {
            resize();
        }
        items[nextfirst] = x;
        moveNextfirst();
        size += 1;
    }

    /** Adds an item to the end of the list. */
    @Override
    public void addLast(T x) {
        if (size == itemLength) {
            resize();
        }
        items[nextlast] = x;
        moveNextlast();
        size += 1;
    }

    /** Returns whether the deque is empty. */
    @Override
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    /** Returns the number of items */
    @Override
    public int size() {
        return size;
    }

    /** Prints the items in the deque, separated by a space. */
    @Override
    public void printDeque() {
        T[] a = (T[]) new Object[size];
        System.arraycopy(items, nextfirst+1, a, 0, itemLength-1-nextfirst);
        System.arraycopy(items, 0, a, itemLength-1-nextfirst, size-itemLength+1+nextfirst);
        System.out.print(a); //java print array; -----------------need to fix
    }

    /** Removes and returns the item at the front of the deque.
     If no such item exists, returns null. */
    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        size -= 1;
        nextfirst += 1;
        T item = get(nextfirst);
        items[nextfirst] = null;
        resize();
        return item;
    }

    /** Removes and returns the item at the back of the deque.
     If no such item exists, returns null. */
    @Override
    public T removeLast() { // ---------------------------need to fix
        if (size == 0) {
            return null;
        }
        size -= 1;
        nextlast -= 1;
        T item = items[nextlast];
        items[nextlast] = null;
        resize();
        return item;
    }

    /** Gets the item at the given index.
     If no such item exists, returns null. */
    @Override
    public T get(int index) {
        if (index > size - 1) {
            return null;
        }
        else if (index + nextfirst < itemLength -1) {
            return items[index + nextfirst + 1];
        }
        else {
            return items[index + nextfirst + 1 - itemLength];
        }
    }
}

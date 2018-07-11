public interface Deque<Item> {
    /** Adds x to the front of the list. */
    public void addFirst(Item x);

    /** Adds an item to the end of the list. */
    public void addLast(Item x);

    /** Returns whether the deque is empty. */
    public boolean isEmpty();

    /** Returns the number of items */
    public int size();

    /** Prints the items in the deque, separated by a space. */
    public void printDeque();

    /** Removes and returns the item at the front of the deque.
     If no such item exists, returns null. */
    public Item removeFirst();

    /** Removes and returns the item at the back of the deque.
     If no such item exists, returns null. */
    public Item removeLast();

    /** Gets the item at the given index.
     If no such item exists, returns null. */
    public Item get(int index);

}

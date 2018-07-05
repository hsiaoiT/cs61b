public class LinkedListDeque<T> {
    /* The first item (if it exists) is at sentinel.next.prev
        The last item (if it exists) is at sentinel.prev.next.
     */
    private class Node{
        private T item;
        private Node prev, next;

        public Node(T i, Node p, Node n) {
            item = i;
            prev = p;
            next = n;
        }
    }

    private int size;
    private Node sentinel;
    private T sentinelSy;

    /** Creates an empty linked list deque */
    public LinkedListDeque() {
        sentinel = new Node(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    /** Builds the linked list deque */
    public LinkedListDeque(T x) {
        size = 1;

        sentinel = new Node(null, null, null);
        Node xNode = new Node(x, sentinel, sentinel);
        sentinel.next = xNode;
        sentinel.prev = xNode;
    }


    /** Adds x to the front of the list. */
    public void addFirst(T x) {
        size += 1;
        Node first = new Node(x, sentinel, sentinel.next);
        sentinel.next.prev = first;
        sentinel.next = first;
        //sentinel.prev = sentinel.next.next;
        }

    /** Adds an item to the end of the list. */
    public void addLast(T x) {
        size += 1;

        Node last = new Node(x, sentinel.prev, sentinel);
        sentinel.prev.next = last;
        sentinel.prev = last;
    }

    /** Returns whether the deque is empty. */
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    /** Returns the number of items */
    public int size() {
        return size;
    }

    /** Prints the items in the deque, separated by a space. */
    public void printDeque() {
        Node p = sentinel;
        while (p.next != sentinel) {
            System.out.print(p.next.item + " ");
            p = p.next;
        }
    }

    /** Removes and returns the item at the front of the deque.
     If no such item exists, returns null. */
    public T removeFirst() {
        if (sentinel.next == sentinel) {
            return null;
        }
        size -= 1;
        T itemRemove = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        return itemRemove;
    }

    /** Removes and returns the item at the back of the deque.
        If no such item exists, returns null. */
    public T removeLast() {
        if (sentinel.next == sentinel) {
            return null;
        }
        size -= 1;
        T itemRemove = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        return itemRemove;
    }

    /** Gets the item at the given index.
        If no such item exists, returns null. */
    public T get(int index) {
        if (index > size - 1) {
            return null;
        }
        Node getItem = sentinel.next;
        while ( index > 0) {
            getItem = getItem.next;
            index -= 1;
        }
        return getItem.item;
    }

    public T getRecursive(int index) {
        if (index > size -1) {
            return null;
        }
        return getHelper(index, sentinel.next);
    }

    private T getHelper(int index, Node p) {
        if (index == 0) {
            return p.item;
        }
        return getHelper(index-1, p.next);
    }
}

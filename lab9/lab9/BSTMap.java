package lab9;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Tung
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {

        if (p.key == null) {
            return null;
        } else if (p.key.equals(key)) {
            return p.value;
        }
        else if (p.key.compareTo(key) < 0) {
            return getHelper(key, p.right);
        }
        else {
            return getHelper(key, p.left);
        }

    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        if (root == null) {
            return null;
        }
        return getHelper(key, root);
    }

    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
      * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        if (p == null) {
            root = new Node(key, value);
            size += 1;
            return root;
        }
        if (p.key.equals(key)) {
            p.value = value;
            return p;
        } else if (p.key.compareTo(key) < 0) {
            if (p.right == null) {
                p.right = new Node(key, value);
                size += 1;
                return p;
            } else {
                return putHelper(key, value, p.right);
            }
        } else {
            if (p.left == null) {
                p.left = new Node(key, value);
                size += 1;
                return p;
            } else {
                return putHelper(key, value, p.left);
            }
        }
        //throw new UnsupportedOperationException();
    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        //throw new UnsupportedOperationException();
        putHelper(key, value, root);
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
        //throw new UnsupportedOperationException();
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    private Set keys;
    @Override
    public Set<K> keySet() {
        if (root == null) {
            throw new UnsupportedOperationException();
        }        keySetHelper(root);
        return keys;
    }

    private void keySetHelper(Node p) {
        if (root == null) {
            throw new UnsupportedOperationException();
        }
        keys = new LinkedHashSet(size);
        while (p != null) {
            keys.add(p.key);
            keySetHelper(p.left);
            keySetHelper(p.right);
        }
    }

    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */
    @Override
    public V remove(K key) {
        if (root == null) {
            throw new UnsupportedOperationException();
        }
        V value;
        if (key.equals(root.key)) {
            value = root.value;
            removeRoot();
        } else {
            value = removeHelper(key, root);
        }
        return value;
    }

    private V removeHelper(K key, Node p) {
        if (get(key) == null) {
            return null;
        }
        if (p.left.key.equals(key)) {
            V value = removeByChilds(key, p, p.left);
            return value;
        } else if (p.right.key.equals(key)) {
            V value = removeByChilds(key, p, p.right);
            return value;
        } else if (p.key.compareTo(key) < 0) {
            removeHelper(key, p.right);
        } else {
            removeHelper(key, p.left);
        }
        throw new UnsupportedOperationException();
    }


    private V removeByChilds(K key, Node parent, Node removeNode) {
        V value = removeNode.value;
        int removeN = 0;
        if (parent.left.key.equals(key)) {
            removeN = -1;
        } else {
            removeN = 1;
        }
        int numOfChilds = numberOfChildren(removeNode);

        /** Deletion key has no child. */
        if (numOfChilds == 0) {

            if (removeN < 0) {
                parent.left = null;
                size -= 1;
            } else {
                parent.right = null;
                size -= 1;
            }
        }

        /** Deletion key has one child. */
        if (numOfChilds == 1) {
            if (removeNode.left == null) {
                if (removeN < 0) {
                    parent.left = removeNode.right;
                    size -= 1;
                } else {
                    parent.right = removeNode.right;
                    size -= 1;
                }
            } else {
                if (removeN < 0) {
                    parent.left = removeNode.left;
                    size -= 1;
                } else {
                    parent.right = removeNode.left;
                    size -= 1;
                }
            }
        }

        /** Deletion key has two childs. */
        if (numOfChilds == 2) {
            Node replaceNode = findReplaceNodeL(removeNode);
            replaceNode.left = removeNode.left;
            replaceNode.right = removeNode.right;
            if (removeN < 0) {
                parent.left = replaceNode;
                size -= 1;
            } else {
                parent.right = replaceNode;
                size -= 1;
            }
        }
        return value;
    }

    private void removeRoot() {
        int numOfChilds = numberOfChildren(root);
        if (numOfChilds == 0) {
            root = null;
            size -= 1;
        }
        if (numOfChilds == 1) {
            if (root.right == null) {
                root = root.left;
                size -= 1;
            } else {
                root = root.right;
                size -= 1;
            }
        }
        if (numOfChilds == 2) {
            Node replaceNode = findReplaceNodeL(root);
            replaceNode.left = root.left;
            replaceNode.right = root.right;
            size -= 1;
        }
        throw new UnsupportedOperationException();
    }

    /** Find the node to replace the remove Node, if it has left child,
     *  connect it to its parent.*/
    private Node findReplaceNodeL(Node p) {
        Node child = p.left;
        Node parent = p;
        while (child.right != null) {
            parent = child;
            child = child.right;
        }
        if (child.left != null) {
            parent.right = child.left;
        }
        return child;
    }

    private int numberOfChildren(Node p) {
        int n = 2;
        if (p.left == null) {
            n -= 1;
        }
        if (p.right == null) {
            n -= 1;
        }
        return n;
    }


    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        if (root == null) {
            throw new UnsupportedOperationException();
        }
        V getKey = get(key);
        if (getKey == null) {
            return null;
        } else if (getKey.equals(value)) {
            return remove(key);
        } else {
            return null;
        }
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }
}

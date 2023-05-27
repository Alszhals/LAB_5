import java.util.Iterator;
import java.util.Stack;
import java.lang.Object;



public class BST<K extends Comparable<K>, V> implements Iterable<K> {
    private static class Node<K, V> {
        V data;
        K key;
        Node<K, V> left, right;

        public Node(V data, K key) {
            this.data = data;
            this.key = key;
            left = right = null;
        }
    }

    private Node<K, V> root;
    private int size;


    public BST() {
        root = null;
        size = 0;
    }


    public int size() {
        return size;
    }
    public void put(K key, V val) {
        root = insert(root, key, val);
        size++;
    }
    private Node<K, V> insert(Node<K, V> current, K key, V val) {
        if (current == null) {
            return new Node<>(val, key);
        }
        if (key.compareTo(current.key) < 0) {
            current.left = insert(current.left, key, val);
        } else {
            current.right = insert(current.right, key, val);
        }
        return current;
    }
    public V get(K key) {
        Node<K, V> node = getNode(root, key);
        if (node == null) {
            return null;
        }
        return node.data;
    }
    private Node<K, V> getNode(Node<K, V> current, K key) {
        if (current == null || key.compareTo(current.key) == 0) {
            return current;
        }
        if (key.compareTo(current.key) < 0) {
            return getNode(current.left, key);
        } else {
            return getNode(current.right, key);
        }
    }


    public void delete(K key) {
        root = remove(root, key);
        size--;
    }


    private Node<K, V> remove(Node<K, V> current, K key) {
        if (current == null) {
            return null;
        }
        if (key.compareTo(current.key) < 0) {
            current.left = remove(current.left, key);
        } else if (key.compareTo(current.key) > 0) {
            current.right = remove(current.right, key);
        } else {
            if (current.left == null && current.right == null) {
                return null;
            }
            if (current.left == null) {
                return current.right;
            }
            if (current.right == null) {
                return current.left;
            }
            K smallestValue = findSmallestValue(current.right);
            current.data = (V) smallestValue;
            current.right = remove(current.right, smallestValue);
        }
        return current;
    }


    private K findSmallestValue(Node<K, V> current) {
        if (current.left == null) {
            return current.key;
        }
        return findSmallestValue(current.left);
    }

    @Override
    public Iterator<K> iterator() {
        return new BSTIterator();
    }


    private class BSTIterator implements Iterator<K> {
        private Stack<Node<K, V>> stack;


        public BSTIterator() {
            stack = new Stack<>();
            pushLeftNodes(root);
        }


        private void pushLeftNodes(Node<K, V> node) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public K next() {
            Node<K, V> node = stack.pop();
            pushLeftNodes(node.right);
            return node.key;
        }
    }
    public boolean consist(K key){
        return findconsist(root, key) != null;
    }
    private Node<K,V> findconsist(Node node,K key) {
        if (node == null || key.compareTo((K) node.key) == 0) return node;
        if (key.compareTo((K) node.key) < 0) return findconsist(node.left, key);
        else return findconsist(node.right, key);
    }


}

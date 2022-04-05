
public class BinSearchTree {

    private Node root;
    private int size;
    
    public Integer get(Integer key) {
        return getHelper(key, root);
    }

    private Integer getHelper(Integer key, Node troot) {

        if (troot == null) {
            return null;
        }
        else if (troot.elem.key == key) {
            return troot.elem.value;
        }
        else if (troot.elem.key < key) {
            return getHelper(key, troot.right);
        }
        return getHelper(key, troot.left);
        
    }

    public Node insert(Integer key, Integer value) {
        root = insertHelper(key, value, root);
        return root;
    }

    private Node insertHelper(Integer key, Integer value, Node troot) {
        if (troot == null) {
            size++;
            return new Node(new Pair(key, value), null, null);
        }
        else {
            if (troot.elem.key > key) {
                troot.left = insertHelper(key, value, troot.left);
            }
            else if (troot.elem.key < key) {
                troot.right = insertHelper(key, value, troot.right);
            }
            return troot;
        }
    }

    public boolean containsKey(Integer key) {
        return getHelper(key, root) != null;
    }

    public void delete(Integer key) {
        deleteHelper(key, root);
    }

    private Node deleteHelper(Integer key, Node troot) {
        
        if (troot == null) {
            return null;
        }
        else if (troot.elem.key < key) {
            troot.right = deleteHelper(key, troot.right);
        }
        else if (troot.elem.key > key) {
            troot.left = deleteHelper(key, troot.left);
        }
        else {
            if (troot.left != null && troot.right != null) {
                Node tmp = findMax(troot.left);
                troot.elem = tmp.elem;
                troot.left = deleteHelper(troot.elem.key, troot.left);
            }
            else {
                if (troot.left == null) {
                    troot = troot.right;
                }
                else if (troot.right == null) {
                    troot = troot.left;
                }
            }
            size--;
        }
        return troot;
    }

    private Node findMax(Node troot) {
        if (troot == null) {
            return null;
        }
        while (troot.right != null) {
            troot = troot.right;
        }
        return troot;
    }

    private class Node {
        private Pair elem;
        private Node left;
        private Node right;
        public Node(Pair elem, BinSearchTree.Node left, BinSearchTree.Node right) {
            this.elem = elem;
            this.left = left;
            this.right = right;
        }       
    }

    private class Pair {

        private Integer key;
        private Integer value;
        public Pair(Integer key, Integer value) {
            this.key = key;
            this.value = value;
        }
        
    }
}
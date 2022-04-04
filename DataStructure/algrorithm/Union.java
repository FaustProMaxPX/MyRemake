public class Union {
    
    Integer[] parent;
    Integer[] size;

    /*
        AF(n) : a union containing n elements, 
        their parent nodes are stored in {parent}
        every set's size is stored in size

        Rep invarient:
        parent != null, elements in parent are greater than or equal to 0, less than n
        size != null, elements in size are not less than 1
    */

    public Union(int n) {
        parent = new Integer[n];
        size = new Integer[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    /**
     * check if p and q are belong to the same set
     * compress the path at the same time
     * @param p node p
     * @param q node q
     * @return if they are belong to the same set
     */
    public boolean isConnect(int p, int q) {
        int i = find(p);
        int j = find(q);
        return i == j;
    }

    /**
     * find parent of p
     * @param p node p
     * @return its parent
     */
    public int find(int p) {
        if (p == parent[p]) {
            return p;
        }
        return parent[p] = find(parent[p]);
    }

    /**
     * connect set containing p and set containing q together
     * @param p node p
     * @param q node q
     */
    public void connect(int p, int q) {

        int i = parent[p];
        int j = parent[q];
        if (i == j)
            return;
        if (size[i] >= size[j]) {
            parent[j] = i;
            size[i] += size[j];
        }
        else {
            parent[i] = j;
            size[j] += size[i];
        }
    }


}

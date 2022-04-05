import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BinSearchTreeTest {

    static BinSearchTree tree = new BinSearchTree();

    @Test
    public void testDelete() {
        tree.insert(10, 100);
        tree.insert(50, 0);
        tree.insert(7, 6);
        assertTrue(tree.containsKey(10));
        assertTrue(tree.containsKey(50));
        assertTrue(tree.containsKey(7));
        tree.delete(10);
        assertFalse(tree.containsKey(10));
        assertTrue(tree.containsKey(50));
        assertTrue(tree.containsKey(7));
    }

    @Test
    public void testGet() {
        tree.insert(10, 100);
        tree.insert(50, 0);
        tree.insert(7, 6);
        assertEquals(Integer.valueOf(100), tree.get(10));
        assertEquals(Integer.valueOf(0), tree.get(50));
        assertEquals(Integer.valueOf(6), tree.get(7));
        assertNull(tree.get(12));
    }

    @Test
    public void testInsert() {
        tree.insert(10, 100);
        tree.insert(50, 0);
        tree.insert(7, 6);
        assertTrue(tree.containsKey(10));
        assertTrue(tree.containsKey(50));
        assertTrue(tree.containsKey(7));
        assertFalse(tree.containsKey(0));
    }
}

/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import org.junit.Test;

/**
 * Tests for ConcreteVerticesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteVerticesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteVerticesGraphTest extends GraphInstanceTest<String> {
    
    /*
     * Provide a ConcreteVerticesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteVerticesGraph();
    }
    
    /*
     * Testing ConcreteVerticesGraph...
     */
    
    // Testing strategy for ConcreteVerticesGraph.toString()
    //   TODO
    
    // TODO tests for ConcreteVerticesGraph.toString()
    
    /*
     * Testing Vertex...
     */
    
    // Testing strategy for Vertex
    //
    @Test
    public void vertexTest()
    {
        Graph<String> lGraph = emptyInstance();
        lGraph.add("1");
        lGraph.add("2");
        lGraph.set("1", "2", 1);
        assert lGraph.toString().equals("1 ===> 2\n") ;
        lGraph.add("3");
        lGraph.set("2", "3", 2);
        lGraph.set("1", "3", 3);
        assert lGraph.toString().equals("1 ===> 2\n1 ===> 3\n2 ===> 3\n") : lGraph.toString();
        int pre = lGraph.set("1", "2", 0);
        assert pre == 1 : pre;
        assert lGraph.toString().equals("1 ===> 3\n2 ===> 3\n") : lGraph.toString();
    }
}

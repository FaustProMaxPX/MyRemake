/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import org.junit.Test;

/**
 * Tests for ConcreteEdgesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteEdgesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteEdgesGraphTest extends GraphInstanceTest<String> {
    
    /*
     * Provide a ConcreteEdgesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteEdgesGraph<String>();
    }
    
    /*
     * Testing ConcreteEdgesGraph...
     */
    
    // Testing strategy for ConcreteEdgesGraph.toString()
    // partition:
    // circle, normal
    // empty, only one, ...
    // has discrete points, don't have
    // TODO tests for ConcreteEdgesGraph.toString()
    @Test
    public void toStringTest()
    {
        Graph<String> graph = emptyInstance();
        graph.add("0");
        graph.add("1");
        graph.add("2");
        graph.set("0", "1", 1);
        graph.set("1", "2", 2);
        String s = graph.toString();
        assert s.equals("0===>1 weight: 1\n1===>2 weight: 2\n") : s;
    }
    /*
     * Testing Edge...
     */
    
    // Testing strategy for Edge
    // same as above
    @Test
    public void edgeTest()
    {
        Graph<String> graph = emptyInstance();
        int set = graph.set("1", "2", 1);
        assert set == 0;
        graph.add("1");
        graph.add("2");
        graph.set("1", "2", 1);
        int set1 = graph.set("1", "2", 0);
        assert set1 == 1 : set1;
        assert graph.set("1", "2", 1) == 0;
    }
    
    // TODO tests for operations of Edge
    
}

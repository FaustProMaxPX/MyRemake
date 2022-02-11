/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteVerticesGraph<L> implements Graph<L> {
    
    private final List<Vertex> vertices = new ArrayList<>();
    
    // Abstraction function:
    //   TODO
    // Representation invariant:
    //   TODO
    // Safety from rep exposure:
    //   TODO
    
    // TODO constructor
    // TODO checkRep
    
    @Override public boolean add(L vertex) {
        throw new RuntimeException("not implemented");
    }
    
    @Override public int set(L source, L target, int weight) {
        throw new RuntimeException("not implemented");
    }
    
    @Override public boolean remove(L vertex) {
        throw new RuntimeException("not implemented");
    }
    
    @Override public Set<L> vertices() {
        return (Set<L>) Set.copyOf(vertices);
    }
    
    @Override public Map<L, Integer> sources(L target) {
        throw new RuntimeException("not implemented");
    }
    
    @Override public Map<L, Integer> targets(L source) {
        throw new RuntimeException("not implemented");
    }
    
    // TODO toL()
    
}

/**
 * TODO specification
 * Mutable.
 * This class is internal to the rep of ConcreteVerticesGraph.
 * 
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Vertex {
    
    // TODO fields
    
    // Abstraction function:
    //   TODO
    // Representation invariant:
    //   TODO
    // Safety from rep exposure:
    //   TODO
    
    // TODO constructor
    
    // TODO checkRep
    
    // TODO methods
    
    // TODO toL()
    
}

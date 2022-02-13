/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.*;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteVerticesGraph<L> implements Graph<L> {
    
    private final List<Vertex> vertices = new ArrayList<>();
    
    // Abstraction function:
    //   vertices -> graph
    // Representation invariant:
    //   all vertex in vertices are not null
    //   weight is non-negative
    // Safety from rep exposure:
    //   TODO
    
    // constructor
    public ConcreteVerticesGraph(){}
    // checkRep
    public void checkRep()
    {
        for (Vertex vertex : vertices)
        {
            assert vertex != null;
            vertex.checkRep();
        }
    }
    
    @Override public boolean add(L vertex) {
        for (Vertex vertex1 : vertices) {
            if (vertex1.getData().equals(vertex))
                return false;
        }
        vertices.add(new Vertex<L>(vertex));
        checkRep();
        return true;
    }
    
    @Override public int set(L source, L target, int weight) {
        int w = 0;
        for (Vertex vertex : vertices)
        {
            if (vertex.getData().equals(source))
            {
                if (weight == 0)
                    w = vertex.unlink(target);
                else
                    w = vertex.link(target, weight);
            }
        }
        return w;
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
    
    // toString()
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        for (Vertex vertex : vertices) {
            builder.append(vertex.toString());
        }
        return builder.toString();
    }
}

/**
 * TODO specification
 * Mutable.
 * This class is internal to the rep of ConcreteVerticesGraph.
 * 
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Vertex<L> {
    
    // field
    // the data of this vertex
    private L data;
    // the edges that start from this vertex
    private Map<L,Integer> edges;
    // Abstraction function:
    //   Vertex(data, edges) -> created a vertex contain its data and out-degree
    // Representation invariant:
    //   data != null && key of edges != null && value of edges >= 0
    // Safety from rep exposure:
    //  only give public method to mutate edges.
    //  the public get method offer an unmodifiable map to client
    
    // constructor
    public Vertex(L data)
    {
        this.data = data;
        this.edges = new HashMap<>();
    }
    // checkRep
    void checkRep()
    {
        assert data != null;
        assert !edges.containsKey(null);
        for (L key : edges.keySet())
            assert edges.get(key) >= 0;
    }

    // methods
    public int link(L that, int weight)
    {
        Integer pre = edges.put(that, weight);
        checkRep();
        return pre == null ? 0 : pre;
    }

    public int unlink(L that)
    {
        Integer pre = edges.remove(that);
        checkRep();
        return pre == null ? 0 : pre;
    }

    public L getData()
    {
        return data;
    }

    public Map<L,Integer> getEdges()
    {
        return Map.copyOf(edges);
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<L, Integer> entry : edges.entrySet()) {
            builder.append(data).append(" ===> ").append(entry.getKey()).append("\n");
        }
        return builder.toString();
    }
    
}

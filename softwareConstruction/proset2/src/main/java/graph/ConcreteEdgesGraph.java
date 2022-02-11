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
public class ConcreteEdgesGraph<L> implements Graph<L> {
    
    private final Set<L> vertices = new HashSet<>();
    private final List<Edge<L>> edges = new ArrayList<>();
    
    // Abstraction function:
    //   edges and vertices -> graph
    // Representation invariant:
    //   edges' froms and tos are all in graph
    // Safety from rep exposure:
    //  return defensive copy of vertices
    
    // constructor
    public ConcreteEdgesGraph() {}
    // checkRep
    private void checkRep()
    {
        Set<L> edgeVertices = new HashSet<>();
        for (Edge<L> edge : edges)
        {
            assert edge.getWeight() >= 0;
            edgeVertices.add(edge.getFrom());
            edgeVertices.add(edge.getTo());
        }
        vertices.containsAll(edgeVertices);
    }
    
    @Override public boolean add(L vertex) {
        if (vertices.contains(vertex))
            return false;
        vertices.add(vertex);
        checkRep();
        return true;
    }
    
    @Override public int set(L source, L target, int weight) {
        Edge desc = new Edge(source, target, weight);
        for (int i = 0; i < edges.size(); i++)
        {
            if (edges.get(i).equals(desc))
            {
                int w = edges.get(i).getWeight();
                if (weight == 0) edges.remove(i);
                else edges.set(i, desc);
                checkRep();
                return w;
            }
        }
        edges.add(desc);
        checkRep();
        return 0;
    }
    
    @Override public boolean remove(L vertex) {
        if (!vertices.contains(vertex)) return false;
        List<Edge> del = new ArrayList<>();
        for (Edge<L> edge : edges) {
            if (edge.getFrom().equals(vertex) || edge.getTo().equals(vertex))
                del.add(edge);
        }
        edges.removeAll(del);
        vertices.remove(vertex);
        checkRep();
        return true;
    }
    
    @Override public Set<L> vertices() {
        return Set.copyOf(vertices);
    }
    
    @Override public Map<L, Integer> sources(L target) {
        Map<L,Integer> ans = new HashMap<>();
        for (Edge<L> edge : edges) {
            if (edge.getTo().equals(target))
                ans.put(edge.getFrom(), edge.getWeight());
        }
        return ans;
    }
    
    @Override public Map<L, Integer> targets(L source) {
        Map<L, Integer> ans = new HashMap<>();
        for (Edge<L> edge : edges) {
            if (edge.getFrom().equals(source))
                ans.put(edge.getTo(), edge.getWeight());
        }
        return ans;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        for (Edge<L> edge : edges) {
            builder.append(edge.toString()).append("\n");
        }
        return builder.toString();
    }
}

/**
 * TODO specification
 * Immutable.
 * This class is internal to the rep of ConcreteEdgesGraph.
 * 
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Edge <L> {
    
    // field
    private L from;
    private L to;
    private int weight;
    // Abstraction function:
    // Edge(from, to, weight) -> created a direct edge from 'from' to 'to', its weight is non-negative
    // Representation invariant:
    // from != null, to != null, weight >= 0
    // Safety from rep exposure:
    // All public method don't allow client affect this object
    // no set method make the object immutable

    // constructor
    public Edge(L from, L to, int weight)
    {
        if (from == null || to == null || weight < 0)
            throw new IllegalArgumentException("the argument of constructor is invalid");
        this.from = from;
        this.to = to;
        this.weight = weight;

    }

    // checkRep
    private void checkRep()
    {
        assert from != null && to != null && weight >= 0;
    }

    public L getFrom() {
        return from;
    }

    public L getTo() {
        return to;
    }

    public int getWeight() {
        return weight;
    }

    public boolean equals(Edge edge) {
        if (edge == null)
            return false;
        return edge.from.equals(this.from) && edge.to.equals(this.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, weight);
    }

    @Override
    public String toString() {
        return from.toString() + "===>" + to.toString() + " weight: " + weight;
    }
}

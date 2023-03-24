package edu.emory.cs.graph.span;

import edu.emory.cs.graph.Edge;

import java.util.ArrayList;
import java.util.List;

public class SpanningTree implements Comparable<SpanningTree> {
    private final List<Edge> edges;
    private double total_weight;

    public SpanningTree() {
        edges = new ArrayList<>();
    }

    public SpanningTree(SpanningTree tree) {
        edges = new ArrayList<>(tree.getEdges());
        total_weight = tree.getTotalWeight();
    }

    public List<Edge> getEdges() { return edges; }
    public double getTotalWeight() { return total_weight; }
    public int size() { return edges.size(); }

    public void addEdge(Edge edge) {
        edges.add(edge);
        total_weight += edge.getWeight();
    }

    @Override
    public int compareTo(SpanningTree tree) {
        double diff = total_weight - tree.total_weight;
        if (diff > 0) return 1;
        else if (diff < 0) return -1;
        else return 0;
    }
}

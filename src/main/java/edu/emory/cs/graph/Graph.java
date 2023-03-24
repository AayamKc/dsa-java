package edu.emory.cs.graph;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Graph {
    private final List<List<Edge>> incoming_edges;

    public Graph(int size) {
        incoming_edges = Stream.generate(ArrayList<Edge>::new).limit(size).collect(Collectors.toList());
    }

    public Graph(Graph g) {
        incoming_edges = g.incoming_edges.stream().map(ArrayList::new).collect(Collectors.toList());
    }

    public boolean hasNoEdge() {
        return IntStream.range(0, size()).allMatch(i -> getIncomingEdges(i).isEmpty());
    }

    public int size() {
        return incoming_edges.size();
    }

    public Edge setDirectedEdge(int source, int target, double weight) {
        List<Edge> edges = getIncomingEdges(target);
        Edge edge = new Edge(source, target, weight);
        edges.add(edge);
        return edge;
    }

    public void setUndirectedEdge(int source, int target, double weight) {
        setDirectedEdge(source, target, weight);
        setDirectedEdge(target, source, weight);
    }

    public List<Edge> getIncomingEdges(int target) {
        return incoming_edges.get(target);
    }

    public List<Edge> getAllEdges() {
        return incoming_edges.stream().flatMap(List::stream).collect(Collectors.toList());
    }

    public Deque<Integer> getVerticesWithNoIncomingEdges() {
        return IntStream.range(0, size()).filter(i -> getIncomingEdges(i).isEmpty()).boxed().collect(Collectors.toCollection(ArrayDeque::new));
    }

    public List<Deque<Edge>> getOutgoingEdges() {
        List<Deque<Edge>> outgoing_edges = Stream.generate(ArrayDeque<Edge>::new).limit(size()).collect(Collectors.toList());

        for (int target = 0; target < size(); target++) {
            for (Edge incoming_edge : getIncomingEdges(target))
                outgoing_edges.get(incoming_edge.getSource()).add(incoming_edge);
        }

        return outgoing_edges;
    }

    public boolean containsCycle() {
        Deque<Integer> notVisited = IntStream.range(0, size()).boxed().collect(Collectors.toCollection(ArrayDeque::new));

        while (!notVisited.isEmpty()) {
            if (containsCycleAux(notVisited.poll(), notVisited, new HashSet<>()))
                return true;
        }

        return false;
    }

    private boolean containsCycleAux(int target, Deque<Integer> notVisited, Set<Integer> visited) {
        notVisited.remove(target);
        visited.add(target);

        for (Edge edge : getIncomingEdges(target)) {
            if (visited.contains(edge.getSource()))
                return true;

            if (containsCycleAux(edge.getSource(), notVisited, new HashSet<>(visited)))
                return true;
        }

        return false;
    }
}

package edu.emory.cs.graph.flow;

import edu.emory.cs.graph.Edge;
import edu.emory.cs.graph.Graph;
import edu.emory.cs.graph.Subgraph;

import java.util.*;

public class NetworkFlowQuiz {

    public Set<Subgraph> getAugmentingPaths(Graph graph, int source, int target) {
        Set<Subgraph> allPaths = new HashSet<>();
        List<Deque<Edge>> outgoingEdges = graph.getOutgoingEdges();
        getAugmentingPaths(graph, new Subgraph(), source, target, allPaths, new HashSet<>(), outgoingEdges);
        return allPaths;
    }

    private void getAugmentingPaths(Graph graph, Subgraph sub, int current, int target, Set<Subgraph> allPaths, Set<Integer> visited, List<Deque<Edge>> outgoingEdges) {
        visited.add(current);

        if (current == target) {
            allPaths.add(new Subgraph(sub));
        } else {
            Deque<Edge> outgoing = outgoingEdges.get(current);
            List<Edge> outgoingList = new ArrayList<>(outgoing);

            for (Edge edge : outgoingList) {
                sub.addEdge(edge);
                if (!visited.contains(edge.getTarget())) {
                    getAugmentingPaths(graph, sub, edge.getTarget(), target, allPaths, visited, outgoingEdges);
                }
                sub.getEdges().remove(sub.getEdges().size() - 1);
            }
        }

        visited.remove(current);
    }
}

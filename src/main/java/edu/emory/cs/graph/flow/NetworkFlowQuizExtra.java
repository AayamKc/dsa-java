package edu.emory.cs.graph.flow;

import edu.emory.cs.graph.Edge;
import edu.emory.cs.graph.Graph;
import edu.emory.cs.graph.Subgraph;

import java.util.*;

public class NetworkFlowQuizExtra {

    public Set<Subgraph> getAugmentingPaths(Graph graph, int source, int target) {
        Set<Subgraph> allPaths = new HashSet<>();
        List<Deque<Edge>> outgoingEdges = graph.getOutgoingEdges();

        Queue<Subgraph> queue = new LinkedList<>();
        Subgraph start = new Subgraph();
        start.addEdge(new Edge(-1, source));
        queue.offer(start);

        while (!queue.isEmpty()) {
            Subgraph currentSubgraph = queue.poll();
            Edge currentEdge = currentSubgraph.getEdges().get(currentSubgraph.getEdges().size() - 1);

            if (currentEdge.getTarget() == target) {
                allPaths.add(new Subgraph(currentSubgraph));
                continue;
            }

            Deque<Edge> outgoing = outgoingEdges.get(currentEdge.getTarget());

            for (Edge edge : outgoing) {
                if (!currentSubgraph.contains(edge.getTarget())) {
                    Subgraph newSubgraph = new Subgraph(currentSubgraph);
                    newSubgraph.addEdge(edge);
                    queue.offer(newSubgraph);
                }
            }
        }

        // remove the dummy edges from the paths
        for (Subgraph path : allPaths) {
            path.getEdges().remove(0);
        }

        return allPaths;
    }
}
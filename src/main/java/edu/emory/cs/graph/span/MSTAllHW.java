package edu.emory.cs.graph.span;

import edu.emory.cs.graph.Edge;
import edu.emory.cs.graph.Graph;

import java.util.*;

public class MSTAllHW implements MSTAll {

    @Override
    public List<SpanningTree> getMinimumSpanningTrees(Graph graph) {
        SpanningTree initialTree = primSpan(graph);
        double targetWeight = initialTree.getTotalWeight();
        List<SpanningTree> result = new ArrayList<>();

        if (initialTree.size() == 0 && graph.size() == 1) {
            result.add(initialTree);
            return result;
        }

        PriorityQueue<Edge> queue = new PriorityQueue<>();
        SpanningTree tree = new SpanningTree();
        Set<Integer> visited = new HashSet<>();

        addEdgesToQueue(queue, visited, graph, 0);
        primRecursive(graph, targetWeight, queue, visited, tree, result);

        if (result.isEmpty()) {
            result.add(initialTree);
        }

        return result;
    }

    private void primRecursive(Graph graph, double targetWeight, PriorityQueue<Edge> queue, Set<Integer> visited, SpanningTree tree, List<SpanningTree> result) {
        if (tree.getTotalWeight() >= targetWeight) {
            return;
        }

        PriorityQueue<Edge> queueCopy = new PriorityQueue<>(queue);

        while (!queue.isEmpty()) {
            Edge edge = queue.poll();
            queueCopy.poll();

            if (visited.contains(edge.getSource())) {
                primRecursive(graph, targetWeight, queueCopy, visited, tree, result);
            } else {
                if (queue.peek() != null && edge.getWeight() == queue.peek().getWeight()) {
                    primRecursive(graph, targetWeight, new PriorityQueue<>(queueCopy), new HashSet<>(visited), new SpanningTree(tree), result);
                }

                if (tree.getTotalWeight() >= targetWeight) {
                    return;
                }

                tree.addEdge(edge);

                if (tree.size() + 1 == graph.size() && tree.getTotalWeight() == targetWeight) {
                    result.add(tree);
                    return;
                }

                int edgeSource = edge.getSource();
                addEdgesToQueue(queue, visited, graph, edgeSource);
                addEdgesToQueue(queueCopy, visited, graph, edgeSource);
            }
        }
    }

    public SpanningTree primSpan(Graph graph) {
        PriorityQueue<Edge> queue = new PriorityQueue<>();
        SpanningTree tree = new SpanningTree();
        Set<Integer> visited = new HashSet<>();

        if (graph.size() > 0) {
            addEdgesToQueue(queue, visited, graph, 0);

            while (!queue.isEmpty()) {
                Edge edge = queue.poll();
                int source = edge.getSource();

                if (!visited.contains(source)) {
                    tree.addEdge(edge);
                    if (tree.size() + 1 == graph.size()) break;
                    addEdgesToQueue(queue, visited, graph, source);
                }
            }
        }

        return tree;
    }

    private void addEdgesToQueue(PriorityQueue<Edge> queue, Set<Integer> visited, Graph graph, int target) {
        visited.add(target);

        for (Edge edge : graph.getIncomingEdges(target)) {
            int source = edge.getSource();
            if (!visited.contains(source)) {
                queue.add(edge);
            }
        }
    }
}

//hiiii, i just need to add something, so I can make another push to github
package edu.emory.cs.graph.span;

import edu.emory.cs.graph.Edge;
import edu.emory.cs.graph.Graph;
import edu.emory.cs.set.DisjointSet;

import java.util.*;

public class MSTAllHW implements MSTAll {

    private double minWeight;
    private Set<SpanningTree> allSpanningTrees;

    @Override
    public List<SpanningTree> getMinimumSpanningTrees(Graph graph) {
        minWeight = Double.POSITIVE_INFINITY;
        allSpanningTrees = new HashSet<>();
        DisjointSet forest = new DisjointSet(graph.size());
        PriorityQueue<Edge> edges = new PriorityQueue<>(graph.getAllEdges());

        buildMSTs(0, 0.0, edges, new SpanningTree(), forest, graph.size() - 1);

        List<SpanningTree> result = new ArrayList<>();
        for (SpanningTree tree : allSpanningTrees) {
            if (tree.getTotalWeight() == minWeight) {
                result.add(tree);
            }
        }
        return result;
    }

    private void buildMSTs(int index, double weight, PriorityQueue<Edge> edges, SpanningTree currentTree, DisjointSet forest, int remainingEdges) {
        if (remainingEdges == 0) {
            if (weight < minWeight) {
                minWeight = weight;
                allSpanningTrees.clear();
            }
            if (weight == minWeight) {
                allSpanningTrees.add(new SpanningTree(currentTree));
            }
            return;
        }

        if (index < edges.size()) {
            Edge edge = edges.poll();
            PriorityQueue<Edge> remainingEdgesQueue = new PriorityQueue<>(edges);

            int source = edge.getSource();
            int target = edge.getTarget();
            double edgeWeight = edge.getWeight();

            if (weight + edgeWeight * remainingEdges <= minWeight) {
                int sourceRoot = forest.find(source);
                int targetRoot = forest.find(target);

                if (sourceRoot != targetRoot) {
                    DisjointSet newForest = new DisjointSet(forest);
                    newForest.union(sourceRoot, targetRoot);

                    SpanningTree newTree = new SpanningTree(currentTree);
                    newTree.addEdge(edge);

                    buildMSTs(index + 1, weight + edgeWeight, remainingEdgesQueue, newTree, newForest, remainingEdges - 1);
                }

                buildMSTs(index + 1, weight, remainingEdgesQueue, currentTree, forest, remainingEdges);
            }
        }
    }
}
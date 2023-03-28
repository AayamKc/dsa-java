package edu.emory.cs.graph;

import java.util.HashSet;
import java.util.Set;

public class GraphQuiz extends Graph {
    public GraphQuiz(int size) {
        super(size);
    }

    public GraphQuiz(Graph g) {
        super(g);
    }

    public int numberOfCycles() {
        int count = 0;
        Set<Integer> visited = new HashSet<>();

        for (int i = 0; i < size(); i++) {
            if (!visited.contains(i)) {
                Set<Integer> onStack = new HashSet<>();
                count += dfs(i, visited, onStack, -1);
            }
        }

        return count;
    }

    private int dfs(int vertex, Set<Integer> visited, Set<Integer> onStack, int parent) {
        visited.add(vertex);
        onStack.add(vertex);
        int count = 0;

        for (Edge edge : getIncomingEdges(vertex)) {
            int nextVertex = edge.getSource();

            if (nextVertex != parent) {
                if (!visited.contains(nextVertex)) {
                    count += dfs(nextVertex, visited, onStack, vertex);
                } else if (onStack.contains(nextVertex)) {
                    count++;
                }
            }
        }

        onStack.remove(vertex);
        return count;
    }
}
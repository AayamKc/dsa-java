package edu.emory.cs.graph;

import java.util.*;

public class GraphQuiz extends Graph {

    public GraphQuiz(int size) {
        super(size);
    }

    public GraphQuiz(Graph g) {
        super(g);
    }

    public int numberOfCycles() {
        int numCycles = 0;
        Deque<Integer> notVisited = new ArrayDeque<>(size());
        for (int i = 0; i < size(); i++) notVisited.add(i);

        while (!notVisited.isEmpty()) {
            int target = notVisited.poll();
            Set<Integer> visited = new HashSet<>();
            visited.add(target);
            numCycles += numberOfCyclesAux(target, notVisited, visited);
        }

        return numCycles;
    }

    private int numberOfCyclesAux(int target, Deque<Integer> notVisited, Set<Integer> visited) {
        int numCycles = 0;
        for (Edge edge : getIncomingEdges(target)) {
            int source = edge.getSource();
            if (visited.contains(source)) {
                if (visited.size() >= 2 && (source != target || edge.getWeight() != 0)) {
                    numCycles++;
                    break;
                }
            } else {
                visited.add(source);
                numCycles += numberOfCyclesAux(source, notVisited, visited);
                visited.remove(source);
            }
        }
        notVisited.remove(target);
        return numCycles;
    }

}
package edu.emory.cs.graph;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GraphQuiz extends Graph {

    public GraphQuiz(int size) {
        super(size);
    }

    public GraphQuiz(Graph g) {
        super(g);
    }

    public int numberOfCycles() {
        Set<Integer> notVisited = IntStream.range(0, size()).boxed().collect(Collectors.toSet());
        Set<Integer> visited = new HashSet<>();
        int count = 0;
        while (!notVisited.isEmpty()) {
            Integer vertex = notVisited.iterator().next();
            notVisited.remove(vertex);
            count += numberOfCyclesAux(vertex, notVisited, visited, vertex);
            visited.add(vertex);
        }
        return count;
    }

    public int numberOfCyclesAux(int target, Set<Integer> notVisited, Set<Integer> visited, int origin) {
        int count = 0;
        for (Edge edge : getIncomingEdges(target)) {
            if (visited.contains(edge.getSource())) {
                continue;
            }
            if (edge.getSource() == origin) {
                count++;
                continue;
            }
            visited.add(edge.getSource());
            count += numberOfCyclesAux(edge.getSource(), notVisited, visited, origin);
            visited.remove(edge.getSource());
        }
        return count;
    }
}
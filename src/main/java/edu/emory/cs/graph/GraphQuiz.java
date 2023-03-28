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
        Deque<Integer> notVisited = IntStream.range(0, size()).boxed().collect(Collectors.toCollection(ArrayDeque::new));
        Deque<Integer> visited = new ArrayDeque<>();
        int count = 0;
        while (!notVisited.isEmpty()) {
            Integer vertex = notVisited.poll();
            count += numberOfCyclesAux(vertex, notVisited, visited, vertex);
            visited.add(vertex);
        }
        return count;
    }

    public int numberOfCyclesAux(int target, Deque<Integer> notVisited, Deque<Integer> visited, int origin) {
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
            visited.removeLast();
        }
        return count;
    }
}
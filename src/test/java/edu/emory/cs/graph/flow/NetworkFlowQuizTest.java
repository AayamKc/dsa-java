package edu.emory.cs.graph.flow;

import edu.emory.cs.graph.Edge;
import edu.emory.cs.graph.Graph;
import edu.emory.cs.graph.Subgraph;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class NetworkFlowQuizTest {

    @Test
    public void test() {
        NetworkFlowQuiz quiz = new NetworkFlowQuiz();
        Graph graph;
        Set<Subgraph> allPaths;

        graph = getGraph0();
        allPaths = quiz.getAugmentingPaths(graph, 0, graph.size() - 1);
        assertEquals(4, allPaths.size());

        graph = getGraph1();
        allPaths = quiz.getAugmentingPaths(graph, 0, graph.size() - 1);
        assertEquals(3, allPaths.size());
    }

    Graph getGraph0() {
        Graph graph = new Graph(6);
        int s = 0, t = 5;

        graph.setDirectedEdge(s, 1, 4);
        graph.setDirectedEdge(s, 2, 2);
        graph.setDirectedEdge(1, 3, 3);
        graph.setDirectedEdge(2, 3, 2);
        graph.setDirectedEdge(2, 4, 3);
        graph.setDirectedEdge(3, 2, 1);
        graph.setDirectedEdge(3, t, 2);
        graph.setDirectedEdge(4, t, 4);

        return graph;
    }

    public Graph getGraph1() {
        Graph graph = new Graph(4);
        int s = 0, t = 3;

        graph.setDirectedEdge(2, t, 1);
        graph.setDirectedEdge(1, t, 1);
        graph.setDirectedEdge(1, 2, 1);
        graph.setDirectedEdge(s, 2, 1);
        graph.setDirectedEdge(s, 1, 1);

        return graph;
    }

    @Test
    public void test2() {
        NetworkFlowQuiz quiz = new NetworkFlowQuiz();
        Graph graph;
        Set<Subgraph> allPaths;

        graph = getGraph0();
        allPaths = quiz.getAugmentingPaths(graph, 0, graph.size() - 1);
        assertEquals(4, allPaths.size());
        System.out.println("Paths found in graph 0:");
        int pathCount = 1;
        for (Subgraph path : allPaths) {
            System.out.print("Path " + pathCount + ": ");
            for (int i = 0; i < path.getEdges().size(); i++) {
                Edge edge = path.getEdges().get(i);
                System.out.print(edge.getSource() + " -> " + edge.getTarget());
                if (i < path.getEdges().size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println();
            pathCount++;
        }

        graph = getGraph1();
        allPaths = quiz.getAugmentingPaths(graph, 0, graph.size() - 1);
        assertEquals(3, allPaths.size());
        System.out.println("Paths found in graph 1:");
        pathCount = 1;
        for (Subgraph path : allPaths) {
            System.out.print("Path " + pathCount + ": ");
            for (int i = 0; i < path.getEdges().size(); i++) {
                Edge edge = path.getEdges().get(i);
                System.out.print(edge.getSource() + " -> " + edge.getTarget());
                if (i < path.getEdges().size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println();
            pathCount++;
        }
    }
}
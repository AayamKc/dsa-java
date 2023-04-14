package edu.emory.cs.graph.span;

import edu.emory.cs.graph.Graph;

import java.util.List;


public interface MSTAll {
    /**
     * @param graph an undirected graph containing zero to many spanning trees.
     * @return list of all minimum spanning trees.
     */
    public List<SpanningTree> getMinimumSpanningTrees(Graph graph);
}
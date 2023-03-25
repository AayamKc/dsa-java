package edu.emory.cs.trie.autocomplete;

import java.util.*;

import edu.emory.cs.trie.TrieNode;

public class AutocompleteHWExtra extends Autocomplete<AutocompleteHWExtra.Candidate> {

    public AutocompleteHWExtra(String dict_file, int max) {
        super(dict_file, max);
    }

    @Override
    public List<String> getCandidates(String prefix) {
        prefix = prefix.trim();
        TrieNode<Candidate> start = find(prefix);

        if (start == null) return Collections.emptyList();

        PriorityQueue<Candidate> candidates = new PriorityQueue<>();
        Queue<NodeAndPrefix> queue = new ArrayDeque<>();
        queue.add(new NodeAndPrefix(start, prefix));

        while (!queue.isEmpty() && candidates.size() < getMax()) {
            NodeAndPrefix nodeAndPrefix = queue.remove();
            TrieNode<Candidate> node = nodeAndPrefix.node;
            String currentPrefix = nodeAndPrefix.prefix;

            if (node.isEndState()) {
                candidates.add(node.getValue());
            }

            BreadthFirstSearch(queue, node, currentPrefix);
        }

        return candidates.stream().map(Candidate::getWord).toList();
    }

    private void BreadthFirstSearch(Queue<NodeAndPrefix> queue, TrieNode<Candidate> node, String prefix) {
        Map<Character, TrieNode<Candidate>> childrenMap = node.getChildrenMap();
        List<Character> sortedChildren = childrenMap.keySet().stream().sorted().toList();

        for (Character c : sortedChildren) {
            TrieNode<Candidate> child = node.getChild(c);
            queue.add(new NodeAndPrefix(child, prefix + c));
        }
    }

    @Override
    public void pickCandidate(String prefix, String candidate) {
        prefix = prefix.trim();
        TrieNode<Candidate> node = find(prefix + candidate);

        if (node == null) {
            super.put(prefix + candidate, new Candidate(candidate));
            node = find(prefix + candidate);
        }

        node.getValue().incrementFrequency();
        node.getValue().updateTimestamp();
    }

    private static class NodeAndPrefix {
        TrieNode<Candidate> node;
        String prefix;

        NodeAndPrefix(TrieNode<Candidate> node, String prefix) {
            this.node = node;
            this.prefix = prefix;
        }
    }

    static class Candidate implements Comparable<Candidate> {
        private final String word;
        private int frequency;
        private long timestamp;

        public Candidate(String word) {
            this.word = word;
            this.frequency = 0;
            this.timestamp = System.currentTimeMillis();
        }

        public String getWord() {
            return word;
        }

        public void incrementFrequency() {
            frequency++;
        }

        public void updateTimestamp() {
            timestamp = System.currentTimeMillis();
        }

        @Override
        public int compareTo(Candidate o) {
            if (frequency != o.frequency) {
                return Integer.compare(o.frequency, frequency);
            }
            return Long.compare(o.timestamp, timestamp);
        }
    }
}
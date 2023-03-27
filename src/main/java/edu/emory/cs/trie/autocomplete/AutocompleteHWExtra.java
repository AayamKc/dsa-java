package edu.emory.cs.trie.autocomplete;

import java.util.*;

import edu.emory.cs.trie.TrieNode;

public class AutocompleteHWExtra extends Autocomplete<Map<String, Integer>> {
    private final List<PriorityQueue<Candidate>> selectedCandidates;

    public AutocompleteHWExtra(String dict_file, int max) {
        super(dict_file, max);
        selectedCandidates = new ArrayList<>(getMaxPrefixLength());
        for (int i = 0; i < getMaxPrefixLength(); i++) {
            selectedCandidates.add(new PriorityQueue<>(Comparator.comparing((Candidate c) -> c.frequency).reversed().thenComparing(c -> c.timestamp)));
        }
    }

    @Override
    public List<String> getCandidates(String prefix) {
        prefix = prefix.trim();
        TrieNode<Map<String, Integer>> start = find(prefix);

        if (start == null) return Collections.emptyList();

        List<String> candidates = new ArrayList<>();
        PriorityQueue<Candidate> selected = selectedCandidates.get(prefix.length() - 1);
        Queue<NodeAndPrefix> queue = new ArrayDeque<>();
        queue.add(new NodeAndPrefix(start, prefix));

        while (!queue.isEmpty() && candidates.size() < getMax()) {
            while (!selected.isEmpty()) {
                candidates.add(selected.poll().word);
                if (candidates.size() == getMax()) return candidates;
            }

            NodeAndPrefix nodeAndPrefix = queue.remove();
            TrieNode<Map<String, Integer>> node = nodeAndPrefix.node;
            String currentPrefix = nodeAndPrefix.prefix;

            if (node.isEndState()) {
                candidates.add(currentPrefix);
            }

            BreadthFirstSearch(queue, node, currentPrefix);
        }

        return candidates;
    }

    private void BreadthFirstSearch(Queue<NodeAndPrefix> queue, TrieNode<Map<String, Integer>> node, String prefix) {
        Map<Character, TrieNode<Map<String, Integer>>> childrenMap = node.getChildrenMap();
        List<Character> sortedChildren = childrenMap.keySet().stream().sorted().toList();

        for (Character c : sortedChildren) {
            TrieNode<Map<String, Integer>> child = node.getChild(c);
            queue.add(new NodeAndPrefix(child, prefix + c));
        }
    }

    @Override
    public void pickCandidate(String prefix, String candidate) {
        prefix = prefix.trim();
        TrieNode<Map<String, Integer>> node = find(prefix);

        if (node != null) {
            Map<String, Integer> map = node.getValue();
            if (map == null) {
                map = new HashMap<>();
                node.setValue(map);
            }
            map.put(candidate, map.getOrDefault(candidate, 0) + 1);

            PriorityQueue<Candidate> prefixCandidates = selectedCandidates.get(prefix.length() - 1);
            prefixCandidates.removeIf(c -> c.word.equals(candidate));
            prefixCandidates.offer(new Candidate(candidate, map.get(candidate), System.nanoTime()));

            if (prefixCandidates.size() > getMax()) {
                prefixCandidates.poll();
            }
        }
    }

    private int getMaxPrefixLength() {
        return getMax() + 1;
    }

    private static class NodeAndPrefix {
        TrieNode<Map<String, Integer>> node;
        String prefix;

        NodeAndPrefix(TrieNode<Map<String, Integer>> node, String prefix) {
            this.node = node;
            this.prefix = prefix;
        }
    }

    private static class Candidate {
        String word;
        int frequency;
        long timestamp;

        Candidate(String word, int frequency, long timestamp) {
            this.word = word;
            this.frequency = frequency;
            this.timestamp = timestamp;
        }
    }
}
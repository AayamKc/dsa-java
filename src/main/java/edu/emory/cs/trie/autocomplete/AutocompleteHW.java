package edu.emory.cs.trie.autocomplete;

import java.util.*;

import edu.emory.cs.trie.TrieNode;

public class AutocompleteHW extends Autocomplete<List<String>> {
    private final List<List<String>> selectedCandidates;

    public AutocompleteHW(String dict_file, int max) {
        super(dict_file, max);
        selectedCandidates = new ArrayList<>(getMaxPrefixLength());
        for (int i = 0; i < getMaxPrefixLength(); i++) {
            selectedCandidates.add(new ArrayList<>());
        }
    }

    @Override
    public List<String> getCandidates(String prefix) {
        prefix = prefix.trim();
        if (prefix.isEmpty()) {
            List<String> candidates = new ArrayList<>();
            Map<Character, TrieNode<List<String>>> childrenMap = getRoot().getChildrenMap();
            List<Character> sortedChildren = childrenMap.keySet().stream().sorted().toList();

            for (Character c : sortedChildren) {
                TrieNode<List<String>> child = getRoot().getChild(c);
                if (child.isEndState()) {
                    candidates.add(Character.toString(c));
                }
                BreadthFirstSearch(new ArrayDeque<>(), child, Character.toString(c));
                if (candidates.size() == getMax()) return candidates;
            }

            return candidates;
        }

        TrieNode<List<String>> start = find(prefix);

        if (start == null) return Collections.emptyList();

        List<String> candidates = new ArrayList<>();
        List<String> selected = selectedCandidates.get(prefix.length() - 1);
        int selectedIdx = 0;

        Queue<NodeAndPrefix> queue = new ArrayDeque<>();
        queue.add(new NodeAndPrefix(start, prefix));

        while (!queue.isEmpty() && candidates.size() < getMax()) {
            while (selectedIdx < selected.size()) {
                candidates.add(selected.get(selectedIdx++));
                if (candidates.size() == getMax()) return candidates;
            }

            NodeAndPrefix nodeAndPrefix = queue.remove();
            TrieNode<List<String>> node = nodeAndPrefix.node;
            String currentPrefix = nodeAndPrefix.prefix;

            if (node.isEndState()) {
                candidates.add(currentPrefix);
            }

            BreadthFirstSearch(queue, node, currentPrefix);
        }

        return candidates;
    }

    private void BreadthFirstSearch(Queue<NodeAndPrefix> queue, TrieNode<List<String>> node, String prefix) {
        Map<Character, TrieNode<List<String>>> childrenMap = node.getChildrenMap();
        List<Character> sortedChildren = childrenMap.keySet().stream().sorted().toList();

        for (Character c : sortedChildren) {
            TrieNode<List<String>> child = node.getChild(c);
            queue.add(new NodeAndPrefix(child, prefix + c));
        }
    }

    @Override
    public void pickCandidate(String prefix, String candidate) {
        prefix = prefix.trim();
        candidate = candidate.trim(); // Trim the candidate as well

        // Check if prefix or candidate is empty, and return if either is true
        if (prefix.isEmpty() || candidate.isEmpty()) {
            return;
        }

        List<String> prefixCandidates = selectedCandidates.get(prefix.length() - 1);
        prefixCandidates.remove(candidate);

        int index = Collections.binarySearch(prefixCandidates, candidate);
        index = index < 0 ? -(index + 1) : index;
        prefixCandidates.add(index, candidate);

        if (prefixCandidates.size() > getMax()) {
            prefixCandidates.remove(prefixCandidates.size() - 1);
        }

        if (find(prefix) == null) {
            put(prefix, null);
        }
        if (find(candidate) == null) {
            put(candidate, null);
        }
    }

    private int getMaxPrefixLength() {
        return getMax() + 1;
    }

    private static class NodeAndPrefix {
        TrieNode<List<String>> node;
        String prefix;

        NodeAndPrefix(TrieNode<List<String>> node, String prefix) {
            this.node = node;
            this.prefix = prefix;
        }
    }
}
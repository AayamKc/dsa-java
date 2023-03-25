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
        List<String> prefixCandidates = selectedCandidates.get(prefix.length() - 1);
        prefixCandidates.remove(candidate);

        int index = Collections.binarySearch(prefixCandidates, candidate);
        index = index < 0 ? -(index + 1) : index;
        prefixCandidates.add(index, candidate);

        if (prefixCandidates.size() > getMax()) {
            prefixCandidates.remove(prefixCandidates.size() - 1);
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
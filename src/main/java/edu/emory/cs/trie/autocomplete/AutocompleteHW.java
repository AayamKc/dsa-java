package edu.emory.cs.trie.autocomplete;

import java.util.*;
import java.util.stream.Collectors;

import edu.emory.cs.trie.TrieNode;

public class AutocompleteHW extends Autocomplete<List<String>> {
    private final Map<String, List<String>> selectedCandidates;

    public AutocompleteHW(String dict_file, int max) {
        super(dict_file, max);
        selectedCandidates = new HashMap<>();
    }
    @Override
    public List<String> getCandidates(String prefix) {
        prefix = prefix.trim();
        int size = getMax();
        List<String> candidates = new ArrayList<>();
        Queue<TrieNode<List<String>>> queue = new ArrayDeque<>();
        TrieNode<List<String>> start = find(prefix);

        if (start == null) return Collections.emptyList();

        if (selectedCandidates.containsKey(prefix)) {
            candidates.addAll(selectedCandidates.get(prefix));
        }

        queue.add(start);

        while (!queue.isEmpty() && candidates.size() < size) {
            TrieNode<List<String>> node = queue.remove();

            if (node.isEndState() && !candidates.contains(getString(node))) {
                candidates.add(getString(node));
            }

            Map<Character, TrieNode<List<String>>> childrenMap = node.getChildrenMap();
            for (Character c : new TreeSet<>(childrenMap.keySet())) {
                TrieNode<List<String>> child = node.getChild(c);
                queue.add(child);
            }
        }

        return candidates;
    }


    private void BreadthFirstSearch(Queue<TrieNode<List<String>>> queue, TrieNode<List<String>> node) {
        Map<Character, TrieNode<List<String>>> childrenMap = node.getChildrenMap();
        List<Character> sortedChildren = childrenMap.keySet().stream().sorted().toList();

        for (Character c : sortedChildren) {
            TrieNode<List<String>> child = node.getChild(c);
            queue.add(child);
        }
    }

    private String getString(TrieNode<List<String>> node) {
        StringBuilder word = new StringBuilder();
        TrieNode<List<String>> current = node;

        while (current.getParent() != null) {
            word.insert(0, current.getKey());
            current = current.getParent();
        }

        return word.toString();
    }


    @Override
    public void pickCandidate(String prefix, String candidate) {
        selectedCandidates.putIfAbsent(prefix, new ArrayList<>());
        List<String> prefixCandidates = selectedCandidates.get(prefix);
        prefixCandidates.remove(candidate);
        prefixCandidates.add(0, candidate);
    }
}
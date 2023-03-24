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
    public List<String> getCandidates(String prefix) {
        prefix = prefix.trim();

        int size = getMax();

        List<String> candidates = new ArrayList<>();
        List<Integer> compares = new ArrayList<>();
        Queue<TrieNode<List<String>>> queue = new ArrayDeque<>();
        TrieNode<List<String>> start = find(prefix);
        if (start == null) return Collections.emptyList();

        queue.add(start);

        while (!queue.isEmpty()) {

            if (candidates.size()>=size) break;

            TrieNode<List<String>> node = queue.remove();

            if (node.isEndState()) {
                if (node.getValue() != null) {
                    if (node.getValue().get(1).equals(prefix)) {
                        int index = 0;
                        int compare = Integer.parseInt(node.getValue().get(0));
                        if (compares.isEmpty()) {
                            compares.add(0,compare);
                            candidates.add(0,getWord(node));
                        } else {
                            while (index < compares.size() && compare > compares.get(index)){
                                index++;
                            }
                            compares.add(index,compare);
                            candidates.add(index, getWord(node));
                        }
                    }

                } else {
                    candidates.add(getWord(node));
                }
            }

            BFS(queue, node);
        }

        return candidates;
    }

        private void collectCandidates(TrieNode<List<String>> node, String prefix, List<String> candidates) {
            if (candidates.size() >= getMax()) return;

            if (node.isEndState() && !candidates.contains(prefix)) {
                candidates.add(prefix);
            }

            PriorityQueue<Character> sortedChildren = new PriorityQueue<>(node.getChildrenMap().keySet());

            while (!sortedChildren.isEmpty()) {
                Character c = sortedChildren.poll();
                TrieNode<List<String>> child = node.getChild(c);
                collectCandidates(child, prefix + c, candidates);
            }
        }

    private void BFS(Queue<TrieNode<List<String>>> queue, TrieNode<List<String>> node) {
        Map<Character, TrieNode<List<String>>> childrenMap = node.getChildrenMap();
        List<Character> sortedChildren = childrenMap.keySet().stream().sorted().collect(Collectors.toList());

        for (Character c : sortedChildren) {
            TrieNode<List<String>> child = node.getChild(c);
            queue.add(child);
        }
    }

    private String getWord(TrieNode<List<String>> node) {
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
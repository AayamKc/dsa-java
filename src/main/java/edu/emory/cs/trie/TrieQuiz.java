package edu.emory.cs.trie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TrieQuiz extends Trie<Integer> {

    public List<Entity> getEntities(String input) {
        List<Entity> entities = new ArrayList<>();
        TrieNode<Integer> node = getRoot();
        int startIndex = -1;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            TrieNode<Integer> child = node.getChild(c);

            if (child != null) {
                node = child;

                if (node.isEndState()) {
                    entities.add(new Entity(startIndex, i + 1, node.getValue()));
                }
            } else {
                node = getRoot();
                startIndex = -1;
            }

            if (startIndex == -1 && node != getRoot()) {
                startIndex = i;
            }
        }

        Collections.sort(entities, Comparator.comparingInt(e -> e.begin_index));
        return entities;
    }

}
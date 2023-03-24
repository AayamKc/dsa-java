package edu.emory.cs.trie.autocomplete;

import java.util.ArrayList;
import java.util.List;

public class Tester {
    public static void main(String[] args) {
        String dict_file = "/Users/yam/DesktopTwo/253/src/main/java/resources/dict.txt";
        int max_candidates = 13;

        AutocompleteHW autocomplete = new AutocompleteHW(dict_file, max_candidates);
        List<String> candidates = autocomplete.getCandidates("a");
        System.out.println(candidates);

    }
}

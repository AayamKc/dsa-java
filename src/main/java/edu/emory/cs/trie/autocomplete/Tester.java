package edu.emory.cs.trie.autocomplete;

import java.util.ArrayList;
import java.util.List;

public class Tester {
    public static void main(String[] args) {
        String dict_file = "/Users/yam/DesktopTwo/253/src/main/java/resources/dict.txt";
        int max_candidates = 25;

        AutocompleteHW autocomplete = new AutocompleteHW(dict_file, max_candidates);
        List<String> candidates = autocomplete.getCandidates("mon");
        System.out.println(candidates);

        // use the autocomplete object to get candidates and pick a selected candidate
    }
}

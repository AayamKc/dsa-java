package edu.emory.cs.trie.autocomplete;

import java.util.ArrayList;
import java.util.List;

public class Tester {
    public static void main(String[] args) {
        String dict_file = "/Users/yam/DesktopTwo/253/src/main/java/resources/dict.txt";
        int max_candidates = 1;

        AutocompleteHW autocomplete = new AutocompleteHW(dict_file, max_candidates);
        List<String> candidates = autocomplete.getCandidates("m");
        System.out.println(candidates);



    }
}

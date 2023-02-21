package edu.emory.cs.sort.distribution;

import java.util.Arrays;
import java.util.Collections;

public class Test {
    public static void main(String[] args) {
        RadixSortQuiz q = new RadixSortQuiz();


        Integer[] arr1 = {11, 12, 13, 21, 22, 23, 31, 32, 33, 41, 42, 43, 51, 52, 53, 61, 62, 63};
        Collections.shuffle(Arrays.asList(arr1));

        q.sort(arr1);

        System.out.println(Arrays.toString(arr1));
    }



}

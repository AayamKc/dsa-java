package edu.emory.cs.sort.distribution;

import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        RadixSortQuiz q = new RadixSortQuiz();

        Integer[] arr = {1, 94, 538, 67, 194, 200, 672, 1000, 69, 42038, 4390239};

        System.out.println(Arrays.toString(arr));

        q.sort(arr, 0, arr.length);

        System.out.println(Arrays.toString(arr));

    }



}

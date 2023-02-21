/*
 * Copyright 2020 Emory University
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.emory.cs.sort;

import edu.emory.cs.sort.comparison.HeapSort;
import edu.emory.cs.sort.comparison.InsertionSort;
import edu.emory.cs.sort.comparison.SelectionSort;
import edu.emory.cs.sort.comparison.ShellSortKnuth;
import edu.emory.cs.sort.distribution.IntegerBucketSort;
import edu.emory.cs.sort.distribution.LSDRadixSort;
import edu.emory.cs.sort.divide_conquer.AbstractSort;
import edu.emory.cs.sort.divide_conquer.IntroSort;
import edu.emory.cs.sort.divide_conquer.MergeSort;
import edu.emory.cs.sort.divide_conquer.QuickSort;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class SortTest {
    @Test
    public void testRobustness() {

        testRobustness(new SelectionSort<>());
        testRobustness(new InsertionSort<>());
        testRobustness(new HeapSort<>());
        testRobustness(new ShellSortKnuth<>());

        testRobustness(new MergeSort<>());
        testRobustness(new QuickSort<>());
        testRobustness(new IntroSort<>(new HeapSort<Integer>()));
        testRobustness(new IntroSort<>(new ShellSortKnuth<Integer>()));

        testRobustness(new IntegerBucketSort(0, 10000));
        testRobustness(new LSDRadixSort());
    }

    void testRobustness(AbstractSort<Integer> engine) {
        final int iter = 10;
        final int size = 11;
        final Random rand = new Random();
        Integer[] original, sorted;

        for (int i = 0; i < iter; i++) {
            original = Stream.generate(() -> rand.nextInt(size)).limit(size).toArray(Integer[]::new);
            sorted = Arrays.copyOf(original, size);

            engine.sort(original);
            Arrays.sort(sorted);
            assertArrayEquals(original, sorted);
        }
    }
}
package edu.emory.cs.sort;

import edu.emory.cs.sort.comparison.ShellSortKnuth;
import edu.emory.cs.sort.comparison.ShellSortQuiz;
import org.junit.jupiter.api.Test;


/** @author Jinho D. Choi */
public class SortQuizTest extends SortTest {

@Test
public void testRobustness() {
        testRobustness(new ShellSortQuiz<>());
    }
}
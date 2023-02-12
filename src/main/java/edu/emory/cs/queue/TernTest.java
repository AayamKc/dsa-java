package edu.emory.cs.queue;

public class TernTest {
    public static void main(String[] args) {
        TernaryHeapQuiz<Integer> t = new TernaryHeapQuiz<>();
        t.add(5);
        System.out.println(t.keys);
        t.add(7);
        System.out.println(t.keys);
        t.add(97);
        System.out.println(t.keys);
        t.add(100);
        System.out.println(t.keys);
        t.add(95);
        System.out.println(t.keys);
        t.add(92);
        System.out.println(t.keys);
        t.add(93);
        System.out.println(t.keys);

    }
}

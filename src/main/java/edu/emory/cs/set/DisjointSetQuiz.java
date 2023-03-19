package edu.emory.cs.set;

public class DisjointSetQuiz {
    public static void main(String[] args) {
        DisjointSet set = new DisjointSet(5);
        System.out.println(set.printSub());

        set.union(0, 1);
        System.out.println(set.printSub());

        set.union(2, 3);
        System.out.println(set.printSub());

        set.union(4, 3);
        System.out.println(set.printSub());

        set.union(1, 3);
        System.out.println(set.printSub());


        System.out.println(set.find(0));
        System.out.println(set.printSub());
        System.out.println(set.findEf(0));
        System.out.println(set.printSub());
    }
}

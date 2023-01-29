package edu.emory.cs.algebraic;

public interface Numeral<T extends Numeral<T>> {
    void add(T n);

    void flipSign();
}

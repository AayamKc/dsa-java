package edu.emory.cs.algebraic;

public enum Sign {
    POSITIVE('+'),
    NEGATIVE('-');




    private final char value; // constant (not a variable since it can't be updated later)

    Sign(char value){
        this.value = value;
    }

    public char value(){
        return value;
    }

}

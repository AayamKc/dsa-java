package edu.emory.cs.algebraic;

import java.util.Arrays;

public class LongIntegerQuiz extends LongInteger implements Comparable<LongInteger> {

    public LongIntegerQuiz(LongInteger n) {
        super(n);
        digits = Arrays.copyOf(n.digits, n.digits.length);
    }

    public LongIntegerQuiz(String s) {
        super(s);
    }

    public LongIntegerQuiz() {
        super("0");
    }

    /**
     * Adds the specific integer that has a different sign than this integer.
     * @param n the integer to be added with a different sign
     */
    @Override
    protected void addDifferentSign(LongInteger n) {
        int difference = compareAbsolute(n);

        if (difference == 0 && (n.sign == Sign.POSITIVE)){
            sign = Sign.NEGATIVE;
            digits = new byte[]{0};
        }

        else if(difference == 0 && (n.sign == Sign.NEGATIVE)){
            sign = Sign.POSITIVE;
            digits = new byte[]{0};
        }

        else if (difference > 0) {
            subtractAbsolute(n);

        } else {
            LongInteger copy = new LongInteger(this);
            set(String.valueOf(n));
            subtractAbsolute(copy);
        }
    }
    /**
     * Returns the difference between LongIntegers using their absolute value
     * @param n the integer to be compred
     */
    private int compareAbsolute(LongInteger n) {
        if (digits.length != n.digits.length)
            return digits.length - n.digits.length;
        for (int i = digits.length - 1; i >= 0; i--)
            if (digits[i] != n.digits[i])
                return digits[i] - n.digits[i];
        return 0;
    }

    /**
     * Returns a LongInteger that is the result of subtraction between the instance and "n"
     * @param n the integer to be compared
     */
    private void subtractAbsolute(LongInteger n) {
        byte[] result = new byte[digits.length];
        System.arraycopy(digits, 0, result, 0, digits.length);
        for (int i = 0; i < n.digits.length; i++) {
            result[i] -= n.digits[i];
            if (result[i] < 0) {
                result[i] += 10;
                result[i + 1] -= 1;
            }
        }
        int i; for (i = result.length - 1; i > 0; i--)
            if (result[i] != 0) break;
        digits = ++i < result.length ? Arrays.copyOf(result, i) : result;
    }

}

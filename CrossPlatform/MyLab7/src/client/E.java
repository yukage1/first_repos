package client;

import compute.Task;

import java.math.BigDecimal;

public class E implements Task<BigDecimal> {
    private static final BigDecimal ONE = BigDecimal.ONE;
    private final int digits;
    public E(int digits) {
        this.digits = digits;
    }
    public BigDecimal execute() {
        return computeE(digits);
    }
    public static BigDecimal computeE(int digits) {
        int scale = digits + 5;
        BigDecimal result = ONE;
        BigDecimal term = ONE;
        int i = 1;
        while (term.compareTo(BigDecimal.ZERO) != 0) {
            BigDecimal factorial = factorial(i);
            term = ONE.divide(factorial, scale, BigDecimal.ROUND_HALF_UP);
            result = result.add(term);
            i++;
        }
        return result.setScale(digits, BigDecimal.ROUND_DOWN);
    }

    public static BigDecimal factorial(int n) {
        BigDecimal result = BigDecimal.ONE;
        for (int i = 2; i <= n; i++) {
            result = result.multiply(BigDecimal.valueOf(i));
        }
        return result;
    }
}

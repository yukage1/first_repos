package client;

import compute.Task;

import java.math.BigDecimal;

public class Pi implements Task<BigDecimal> {
    private static final long serialVersionUID = 227L;
    private static final BigDecimal FOUR = BigDecimal.valueOf(4);
    private static final int roundingMode = BigDecimal.ROUND_HALF_EVEN;
    private final int digits;
    public Pi(int digits) {
        this.digits = digits;
    }
    public BigDecimal execute() {
        return computePi(digits);
    }

    public static BigDecimal computePi(int digits) {
        int scale = digits + 5;
        BigDecimal arctan1_5 = arctan(5, scale).divide(BigDecimal.valueOf(5), scale, roundingMode);
        BigDecimal arctan1_239 = arctan(239, scale).divide(BigDecimal.valueOf(239), scale, roundingMode);
        BigDecimal pi = arctan1_5.multiply(FOUR).subtract(arctan1_239).multiply(FOUR);
        return pi.setScale(digits, BigDecimal.ROUND_DOWN);
    }

    public static BigDecimal arctan(int X, int scale) {
        BigDecimal result = BigDecimal.ZERO;
        BigDecimal numer = BigDecimal.valueOf(X).setScale(scale + 5);
        BigDecimal term;
        int i = 0;
        while (true) {
            BigDecimal divisor = BigDecimal.valueOf(2 * i + 1);
            BigDecimal dividend = numer.divide(divisor, scale, roundingMode);
            term = dividend.divide(BigDecimal.valueOf(X).pow(2 * i + 1), scale, roundingMode);
            if (i % 2 == 0) {
                result = result.add(term);
            } else {
                result = result.subtract(term);
            }
            if (term.abs().compareTo(BigDecimal.ZERO) == 0) {
                break;
            }
            i++;
        }
        return result;
    }
}



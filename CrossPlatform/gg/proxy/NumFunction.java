package KPP.Lab1.proxy;

public class NumFunction implements Evaluatable {
    @Override
    public double evalf(double a) {
        return Math.sin(a);
    }
}

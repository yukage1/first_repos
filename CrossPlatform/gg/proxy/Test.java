package KPP.Lab1.proxy;

import java.lang.reflect.Proxy;

public class Test {
    public static void main(String[] args) {
        NumFunction fun = new NumFunction();
        Evaluatable e = (Evaluatable) Proxy.newProxyInstance(fun.getClass().getClassLoader(),
                fun.getClass().getInterfaces(), new FunHandler(fun));
        e.evalf(1.0);
    }
}

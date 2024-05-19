package Task1;

import java.io.Serializable;
import java.math.BigInteger;

public class JobOne implements Executable, Serializable {
    private final static long serialVersionUID = -1L;
    private final int n;

    public JobOne(int N){
        n = N;
    }
    @Override
    public Object execute() {
        BigInteger res = BigInteger.ONE;
        for (int i = 2; i <= n; i++){
            res = res.multiply(BigInteger.valueOf(i));
        }
        return res;
    }
}
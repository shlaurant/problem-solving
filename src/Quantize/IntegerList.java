package Quantize;

import java.util.List;

public final class IntegerList {
    private final List<Integer> integerList;

    public IntegerList(List<Integer> integerList) {
        this.integerList = integerList;
    }

    public int sumOfSquares() {
        int sum = 0;
        for(int number : integerList) {
            sum += (number - optimizedQuantizer()) * (number - optimizedQuantizer());
        }
        return sum;
    }

    private int optimizedQuantizer() {
        int sum = 0;
        for(int number : integerList) {
            sum += number;
        }
        return Math.round((float)sum/integerList.size());
    }
}

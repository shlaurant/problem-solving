package quantize;

import java.util.ArrayList;
import java.util.List;

public final class IntegerList {
    private final List<Integer> integerList;

    public IntegerList(List<Integer> integerList) {
        this.integerList = integerList;
    }

    public int sumOfSquares() {
        int sum = 0;
        for (int number : integerList) {
            sum += (number - optimizedQuantizer()) * (number - optimizedQuantizer());
        }
        return sum;
    }

    /**
     * integers should be sorted and contain size of IntegerList;
     *
     * @param integers
     * @return
     */
    public List<IntegerList> divideTo(List<Integer> integers) {
        List<IntegerList> integerLists = new ArrayList<>(integers.size());
        for (int index = 0; index < integers.size(); index++) {
            int inclusive;
            if (index == 0) {
                inclusive = 0;
            } else {
                inclusive = integers.get(index - 1);
            }
            int exclusive = integers.get(index);
            integerLists.add(subList(inclusive, exclusive));
        }
        return integerLists;
    }

    public int size() {
        return integerList.size();
    }

    private int optimizedQuantizer() {
        int sum = 0;
        for (int number : integerList) {
            sum += number;
        }
        return Math.round((float) sum / integerList.size());
    }

    private IntegerList subList(int inclusive, int exclusive) {
        return new IntegerList(integerList.subList(inclusive, exclusive));
    }
}

package quantize;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Divider {
    private final int listSize;
    private final int split;

    public Divider(int listSize, int split) {
        this.listSize = listSize;
        this.split = split;
    }

    private List<PossibleDividing> possibleDividings() {
        List<PossibleDividing> result = new ArrayList<>();
        List<Integer> possibility = possibility();
        addPossibility(result, possibility);
        while(true) {
            for(int index = 0; index < possibility.size() - 1; index++) {
                if(index == 0) {
                    if(dividerCandidate(possibility, index) > 0) {
                        possibility.set(index, dividerCandidate(possibility, index));
                        break;
                    }
                } else {
                    if((dividerCandidate(possibility, index)) > previousDivider(possibility, index)) {
                        possibility.set(index, dividerCandidate(possibility, index));
                        for(int i = 0; i < index; i++) {
                            possibility.set(i, possibility.get(index) - (index - i));
                        }
                        break;
                    }
                }
            }
        }
    }

    private void addPossibility(List<PossibleDividing> result, List<Integer> possibility) {
        result.add(new PossibleDividing(new ArrayList<>(possibility)));
    }

    private Integer previousDivider(List<Integer> possibility, int index) {
        return possibility.get(index-1);
    }

    private int dividerCandidate(List<Integer> possibility, int index) {
        return possibility.get(index) - 1;
    }

    private List<Integer> possibility() {
        List<Integer> possibility = new ArrayList<>(split);
        for(int i = listSize; i > listSize - split ; i--) {
            possibility.add(i);
        }
        Collections.sort(possibility);
        return possibility;
    }

    private final class PossibleDividing {
        private final List<Integer> integerList;

        private PossibleDividing(List<Integer> integerList) {
            this.integerList = integerList;
        }
    }
}

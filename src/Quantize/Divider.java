package quantize;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
        while(true) {
            for(int index = 0; index < possibility.size(); index++) {
                if(index == 0) {
                    if(possibility.get(index) > 1) {

                    }
                } else {

                }
            }
        }
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

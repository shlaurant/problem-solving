package leet877;

import java.util.Arrays;

class Solution {
    private int[] piles;
    private int[][] store;

    public boolean stoneGame(int[] piles) {
        this.piles = piles;
        if (store == null) {
            store = new int[piles.length + 1][piles.length + 1];
        }
        for (int[] ints : store) {
            Arrays.fill(ints, Integer.MAX_VALUE);
        }
        return answerOf(0, piles.length) < 0;
    }

    private int answerOf(int inc, int exc) {
        int answer;
        if (hasAnswer(inc, exc)) {
            answer = lookup(inc, exc);
        } else {
            answer = calcedAnswerOf(inc, exc);
            save(inc, exc, answer);
        }
        return answer;
    }

    private boolean hasAnswer(int inc, int exc) {
        return lookup(inc, exc) != Integer.MAX_VALUE;
    }

    private int lookup(int inc, int exc) {
        return store[inc][exc];
    }

    private int calcedAnswerOf(int inc, int exc) {
        int answer;
        if (isBasic(inc, exc)) {
            answer = Math.abs(piles[inc] - piles[exc - 1]);
        } else {
            if (isAlex(inc, exc)) {
                answer = Math.max(answerOf(inc, exc - 1) + piles[exc - 1],
                        answerOf(inc + 1, exc) + piles[inc]);
            } else {
                answer = Math.min(answerOf(inc, exc - 1) - piles[exc - 1],
                        answerOf(inc + 1, exc) - piles[inc]);
            }
        }
        return answer;
    }

    private void save(int inc, int exc, int answer) {
        store[inc][exc] = answer;
    }

    private boolean isBasic(int inc, int exc) {
        return exc - inc == 2;
    }

    private boolean isAlex(int inc, int exc) {
        return exc - inc % 2 == 0;
    }
}

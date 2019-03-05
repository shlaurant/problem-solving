package leet877;

import java.util.HashMap;
import java.util.Map;

class Solution {
    private int[] piles;
    private Map<State, Answer> store;

    public boolean stoneGame(int[] piles) {
        this.piles = piles;
        if(store == null) {
            store = new HashMap<>();
        } else {
            store.clear();
        }

        return answerFor(new State(0, piles.length)).isAlexWon();
    }

    private Answer answerFor(State state) {
        Answer answer;
        if (store.containsKey(state)) {
            answer = store.get(state);
        } else if(state.exc - state.inc == 2) {
            answer = new Answer(Math.max(piles[state.inc],
                    piles[state.exc-1]), Math.min(piles[state.inc], piles[state.exc-1]));
            store.put(state, answer);
        } else {
            if (state.isAlexFirst()) {
                int right = difRight(state) + piles[state.exc - 1];
                int left = difLeft(state) + piles[state.inc];
                if (right > left) {
                    answer =
                            new Answer(answerFor(state.stateWithoutRight()).alex + piles[state.exc - 1], answerFor(state.stateWithoutRight()).lee);
                } else {
                    answer =
                            new Answer(answerFor(state.stateWithoutLeft()).alex + piles[state.inc], answerFor(state.stateWithoutLeft()).lee);
                }
            } else {
                int right = difRight(state) - piles[state.exc - 1];
                int left = difLeft(state) - piles[state.inc];
                if (right < left) {
                    answer =
                            new Answer(answerFor(state.stateWithoutRight()).alex, answerFor(state.stateWithoutRight()).lee + piles[state.exc - 1]);
                } else {
                    answer =
                            new Answer(answerFor(state.stateWithoutLeft()).alex, answerFor(state.stateWithoutLeft()).lee + piles[state.inc]);
                }
            }
            store.put(state, answer);
        }
        return answer;
    }

    private int difLeft(State state) {
        return answerFor(state.stateWithoutLeft()).difference();
    }

    private int difRight(State state) {
        return answerFor(state.stateWithoutRight()).difference();
    }

    private final class State {
        private final int inc;
        private final int exc;

        private State(int inc, int exc) {
            this.inc = inc;
            this.exc = exc;
        }

        private boolean isAlexFirst() {
            return (exc - inc) % 2 == 0;
        }

        private State stateWithoutRight() {
            return new State(inc, exc - 1);
        }

        private State stateWithoutLeft() {
            return new State(inc + 1, exc);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            State state = (State) o;

            if (inc != state.inc) return false;
            return exc == state.exc;
        }

        @Override
        public int hashCode() {
            int result = inc;
            result = 31 * result + exc;
            return result;
        }
    }

    private class Answer {
        private final int alex;
        private final int lee;

        private Answer(int alex, int lee) {
            this.alex = alex;
            this.lee = lee;
        }

        private boolean isAlexWon() {
            return alex > lee;
        }

        private int difference() {
            return alex - lee;
        }
    }
}

package leet877;

class Solution {
    private int[] piles;
    private Answer[][] arrayStore;

    public boolean stoneGame(int[] piles) {
        this.piles = piles;
        arrayStore = new Answer[piles.length + 1][piles.length + 1];

        return answerFor(new State(0, piles.length)).isAlexWon();
    }

    private Answer answerFor(State state) {
        Answer answer;
        if (hasAnswer(state)) {
            answer = lookup(state);
        } else {
            if (isBaseCase(state)) {
                answer = basicAnswer(state);
            } else {
                answer = answerSolving(state);
            }
            save(state, answer);
        }
        return answer;
    }

    private boolean hasAnswer(State state) {
        return arrayStore[state.inc][state.exc] != null;
    }

    private Answer lookup(State state) {
        return arrayStore[state.inc][state.exc];
    }

    private void save(State state, Answer answer) {
        arrayStore[state.inc][state.exc] = answer;
    }

    private boolean isBaseCase(State state) {
        return state.exc - state.inc == 2;
    }

    private Answer basicAnswer(State state) {
        return new Answer(Math.max(piles[state.inc],
                piles[state.exc - 1]), Math.min(piles[state.inc],
                piles[state.exc - 1]));
    }

    private Answer answerSolving(State state) {
        Answer answer;
        Answer answer1 = answerFor(state.stateWithoutRight());
        Answer answer2 = answerFor(state.stateWithoutLeft());
        int difRight = answer1.difference();
        int difLeft = answer2.difference();
        if (state.isAlexFirst()) {
            int right = difRight + piles[state.exc - 1];
            int left = difLeft + piles[state.inc];
            if (right > left) {
                answer =
                        new Answer(answer1.alex + piles[state.exc - 1],
                                answer1.lee);
            } else {
                answer =
                        new Answer(answer2.alex + piles[state.inc],
                                answer2.lee);
            }
        } else {
            int right = difRight - piles[state.exc - 1];
            int left = difLeft - piles[state.inc];
            if (right < left) {
                answer =
                        new Answer(answer1.alex,
                                answer1.lee + piles[state.exc - 1]);
            } else {
                answer =
                        new Answer(answer2.alex,
                                answer2.lee + piles[state.inc]);
            }
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

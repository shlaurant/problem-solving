package quantize;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public final class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numOfTests = scanner.nextInt();
        for (int i = 0; i < numOfTests; i++) {
            solveProblem(scanner);
        }
    }

    private static void solveProblem(Scanner scanner) {
        int numOfInts = scanner.nextInt();
        int numOfSplits = scanner.nextInt();
        Divider divider = new Divider(numOfInts, numOfSplits);
        IntegerList integerList = new IntegerList(sortedInputList(scanner,
                numOfInts));
        System.out.println(minSum(divider, integerList));
    }

    private static int minSum(Divider divider, IntegerList integerList) {
        int minSum = Integer.MAX_VALUE;
        for (List<Integer> possibility : divider.possibleDividings()) {
            int squareSumOfPossibility = sumforPossibility(integerList,
                    possibility);
            if (squareSumOfPossibility < minSum) {
                minSum = squareSumOfPossibility;
            }
        }
        return minSum;
    }

    private static int sumforPossibility(IntegerList integerList,
                                         List<Integer> possibility) {
        int squareSumOfPossibility = 0;
        for (IntegerList subLists : integerList.divideTo(possibility)) {
            squareSumOfPossibility += subLists.sumOfSquares();
        }
        return squareSumOfPossibility;
    }

    private static List<Integer> sortedInputList(Scanner scanner,
                                                 int numOfInts) {
        List<Integer> originalList = new ArrayList<>(numOfInts);
        for (int i = 0; i < numOfInts; i++) {
            originalList.add(scanner.nextInt());
        }
        Collections.sort(originalList);
        return originalList;
    }

    public static final class IntegerList {
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

    public static final class Divider {
        private final int listSize;
        private final int split;

        public Divider(int listSize, int split) {
            this.listSize = listSize;
            this.split = split;
        }

        public List<List<Integer>> possibleDividings() {
            List<List<Integer>> result = new ArrayList<>();
            List<Integer> possibility = possibility();
            addPossibility(result, possibility);
            while (true) {
                manipulateToNext(possibility);
                if (result.contains(possibility)) {
                    break;
                } else {
                    addPossibility(result, possibility);
                }
            }
            return result;
        }

        private void addPossibility(List<List<Integer>> result,
                                    List<Integer> possibility) {
            result.add(new ArrayList<>(possibility));
        }

        private void manipulateToNext(List<Integer> possibility) {
            for (int index = 0; index < possibility.size() - 1; index++) {
                if (index == 0) {
                    if (dividerCandidate(possibility, index) > 0) {
                        possibility.set(index, dividerCandidate(possibility,
                                index));
                        break;
                    }
                } else {
                    if ((dividerCandidate(possibility, index)) > previousDivider(possibility, index)) {
                        possibility.set(index, dividerCandidate(possibility,
                                index));
                        for (int i = 0; i < index; i++) {
                            possibility.set(i,
                                    possibility.get(index) - (index - i));
                        }
                        break;
                    }
                }
            }
        }

        private Integer previousDivider(List<Integer> possibility, int index) {
            return possibility.get(index - 1);
        }

        private int dividerCandidate(List<Integer> possibility, int index) {
            return possibility.get(index) - 1;
        }

        private List<Integer> possibility() {
            List<Integer> possibility = new ArrayList<>(split);
            for (int i = listSize; i > listSize - split; i--) {
                possibility.add(i);
            }
            Collections.sort(possibility);
            return possibility;
        }
    }
}

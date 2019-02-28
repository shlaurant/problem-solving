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
        List<Integer> inputList = sortedInputList(scanner, numOfInts);
        System.out.println(minSum(divider, inputList));
    }

    private static int minSum(Divider divider, List<Integer> integerList) {
        int minSum = Integer.MAX_VALUE;
        for (List<Integer> possibility : divider.possibleDividings()) {
            int squareSumOfPossibility = sumForPossibility(integerList,
                    possibility);
            if (squareSumOfPossibility < minSum) {
                minSum = squareSumOfPossibility;
            }
        }
        return minSum;
    }

    private static int sumForPossibility(List<Integer> integerList,
                                         List<Integer> possibility) {
        int squareSumOfPossibility = 0;
        for (List<Integer> subLists : divideListWith(integerList,
                possibility)) {
            squareSumOfPossibility += sumOfSquaresOf(subLists);
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

    private static List<List<Integer>> divideListWith(List<Integer> divided,
                                                      List<Integer> divider) {
        List<List<Integer>> integerLists = new ArrayList<>(divider.size());
        for (int index = 0; index < divider.size(); index++) {
            int inclusive;
            if (index == 0) {
                inclusive = 0;
            } else {
                inclusive = divider.get(index - 1);
            }
            int exclusive = divider.get(index);
            integerLists.add(subList(divided, inclusive, exclusive));
        }
        return integerLists;
    }

    private static int sumOfSquaresOf(List<Integer> integers) {
        int sum = 0;
        for (int number : integers) {
            sum += (number - optimizedQuantizerOf(integers)) * (number - optimizedQuantizerOf(integers));
        }
        return sum;
    }

    private static List<Integer> subList(List<Integer> integers, int inclusive,
                                         int exclusive) {
        return integers.subList(inclusive, exclusive);
    }

    private static int optimizedQuantizerOf(List<Integer> integers) {
        int sum = 0;
        for (int number : integers) {
            sum += number;
        }
        return Math.round((float) sum / integers.size());
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

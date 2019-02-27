package quantize;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public final class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numOfTests = scanner.nextInt();
        for(int i = 0; i < numOfTests; i++) {
            solveProblem(scanner);
        }
    }

    private static void solveProblem(Scanner scanner) {
        int numOfInts = scanner.nextInt();
        int numOfSplits = scanner.nextInt();
        Divider divider = new Divider(numOfInts, numOfSplits);
        List<Integer> originalList = new ArrayList<>(numOfInts);
        for(int i = 0; i < numOfInts; i++) {
            originalList.add(scanner.nextInt());
        }
        Collections.sort(originalList);
        IntegerList integerList = new IntegerList(originalList);

        int minSum = Integer.MAX_VALUE;
        for(List<Integer> possibility : divider.possibleDividings()) {
            int squareSumOfPossibility = 0;
            for(IntegerList subLists: integerList.divideTo(possibility)) {
                squareSumOfPossibility += subLists.sumOfSquares();
            }
            if(squareSumOfPossibility < minSum) {
                minSum = squareSumOfPossibility;
            }
        }
        System.out.println(minSum);
    }


}

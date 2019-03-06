package quantize;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main2 {
    public static void main(String[] args) {
        Solution solution = new Solution();
        Scanner scanner = new Scanner(System.in);
        int cases = scanner.nextInt();
        for(int i = 0; i < cases; i++) {
            int[] ints = new int[scanner.nextInt()];
            int split = scanner.nextInt();
            for(int index = 0; index < ints.length; index++) {
                ints[index] = scanner.nextInt();
            }
            Arrays.sort(ints);
            System.out.println(solution.solveQuantize(ints, split));
        }
    }
    private static class Solution {
        private int[] origin;
        private Map<String, Integer> dpMap;

        int solveQuantize(int[] ints, int split) {
            if (ints.length <= split) {
                return 0;
            }
            origin = ints;
            dpMap = new HashMap<>();
            return answerFor(0, ints.length, split);
        }

        private int answerFor(int inc, int exc, int split) {
            if (isSolved(inc, exc, split)) {
                return lookup(inc, exc, split);
            } else {
                int answer = calcFor(inc, exc, split);
                dpMap.put(strState(inc, exc, split), answer);
                return answer;
            }
        }

        private boolean isSolved(int inc, int exc, int split) {
            return dpMap.containsKey(strState(inc, exc, split));
        }

        private int lookup(int inc, int exc, int split) {
            return dpMap.get(strState(inc, exc, split));
        }

        private int calcFor(int inc, int exc, int split) {
            if (split == 1) {
                return squareSum(inc, exc);
            }
            int min = Integer.MAX_VALUE;
            for (int index = inc + 1; index < exc - split + 1; index++) {
                min = Math.min(min, answerFor(inc, index, 1) + answerFor(index,
                        exc, split - 1));
            }
            return min;
        }

        private int squareSum(int inc, int exc) {
            int sum = 0;
            for (int index = inc; index < exc; index++) {
                sum += origin[index];
            }
            int avg = (int) Math.round(((double) sum) / (exc - inc));
            int squareSum = 0;
            for (int index = inc; index < exc; index++) {
                squareSum += (origin[index] - avg) * (origin[index] - avg);
            }
            return squareSum;
        }

        private String strState(int inc, int exc, int split) {
            return inc + "/" + exc + "/" + split;
        }
    }
}

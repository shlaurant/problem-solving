package leet931;

class Solution {
    private int[][] square;

    public int minFallingPathSum(int[][] A) {
        square = A;

        for (int i = 1; i < square.length; i++) {
            for (int j = 0; j < square[0].length; j++) {
                square[i][j]
                        = min(direct(i, j), fromLeft(i, j), fromRight(i, j));
            }
        }


        return min(square[square.length - 1]);
    }

    private int fromLeft(int i, int j) {
        if (j - 1 < 0) {
            return Integer.MAX_VALUE;
        }
        return square[i - 1][j - 1] + square[i][j];
    }

    private int fromRight(int i, int j) {
        if (j + 1 >= square[0].length) {
            return Integer.MAX_VALUE;
        }
        return square[i - 1][j + 1] + square[i][j];
    }

    private int direct(int i, int j) {
        return square[i - 1][j] + square[i][j];
    }

    private int min(int a, int b, int c) {
        int min = Math.min(a, b);
        return Math.min(min, c);
    }

    private int min(int[] ints) {
        int min = Integer.MAX_VALUE;
        for (int i : ints) {
            min = Math.min(min, i);
        }
        return min;
    }
}

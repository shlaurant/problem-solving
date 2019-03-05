package leet877;

class Solution2 {

    public boolean stoneGame(int[] piles) {
        int[][] store = new int[piles.length + 1][piles.length + 1];

        for (int length = 2; length <= piles.length; length++) {
            calcForLength(piles, store, length);
        }
        return store[0][piles.length] > 0;
    }

    private void calcForLength(int[] piles, int[][] store, int length) {
        for (int begin = 0; begin + length <= piles.length; begin++) {
            if (length == 2) {
                store[begin][begin + length] =
                        Math.abs(piles[begin] - piles[begin + 1]);
            } else {
                if (length % 2 == 0) {
                    store[begin][begin + length] =
                            Math.max(store[begin][begin + length - 1] + piles[begin + length - 1],
                                    store[begin + 1][begin + length] + piles[begin]);
                } else {
                    store[begin][begin + length] =
                            Math.min(store[begin][begin + length - 1] - piles[begin + length - 1],
                                    store[begin + 1][begin + length] - piles[begin]);
                }
            }
        }
    }
}

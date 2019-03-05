package leet983;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

class Solution {
    private int[] dp;
    private int[] costs;
    private Set<Integer> matterDays;

    public int mincostTickets(int[] days, int[] costs) {
        dp = new int[days[days.length - 1]];
        Arrays.fill(dp, -1);
        this.costs = costs;
        matterDays = new HashSet<>(days.length);
        for (int day : days) {
            matterDays.add(day);
        }
        return solutionFor(days[days.length-1]);
    }

    private int solutionFor(int day) {
        if(day < 1) {
            return 0;
        }
        int answer;
        if (dp[day-1] != -1) {
            answer = dp[day-1];
        } else {
            answer = solveState(day);
            dp[day-1] = answer;
        }
        return answer;
    }

    private int solveState(int day) {
        int answer;
        if (matterDays.contains(day)) {
            answer = Math.min(solutionFor(day - 1) + costs[0],
                    solutionFor(day - 7) + costs[1]);
            answer = Math.min(answer, solutionFor(day - 30) + costs[2]);
        } else {
            answer = solutionFor(day-1);
        }
        return answer;
    }
}
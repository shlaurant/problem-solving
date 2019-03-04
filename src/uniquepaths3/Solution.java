package uniquepaths3;

import java.util.*;

class Solution {
    private Map<State, Integer> dpMap;
    private int[][] origin;

    public int uniquePathsIII(int[][] grid) {
        dpMap = new HashMap<>();
        origin = grid;
        int[][] temp = new int[grid.length][grid[0].length];
        for(int[] sub : temp) {
            Arrays.fill(sub, -1);
        }
        temp[indexOf(1, grid)[0]][indexOf(1, grid)[1]] = 1;
        dpMap.put(new State(indexOf(1, grid)[0],indexOf(1, grid)[1], temp), 1);
        return answerFor(new State(indexOf(2, grid)[0], indexOf(2, grid)[1],
                grid));
    }

    private int[] indexOf(int value, int[][] grid) {
        int[] index = new int[2];
        for(int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[0].length; y++) {
                if (grid[x][y] == 1) {
                    index[0] = x;
                    index[1] = y;
                    break;
                }
            }
        }
        return index;
    }

    private int answerFor(State state) {
        if(dpMap.containsKey(state)) {
            return dpMap.get(state);
        }
        int answer = 0;
        if(origin[state.x][state.y] == -1) {
            answer = 0;
        } else {
            for(State st : state.possiblePres()) {
                answer += answerFor(st);
            }
        }

        dpMap.put(state, answer);
        return answer;
    }

    private class State {
        private int x;
        private int y;
        private int[][] grid;

        private State(int x, int y, int[][] grid) {
            this.x = x;
            this.y = y;
            this.grid = grid;
        }

        private List<State> possiblePres() {
            List<State> result = new ArrayList<>();
            int[][] temp = new int[grid.length][];
            for(int index = 0; index < grid.length; index++) {
                temp[index] = grid[index];
            }
            temp[x][y] = -1;
            addIfValid(result, new State(x-1, y, temp));
            addIfValid(result, new State(x+1, y, temp));
            addIfValid(result, new State(x, y-1, temp));
            addIfValid(result, new State(x, y+1, temp));
            return result;
        }

        private void addIfValid(List<State> result, State candidate) {
            if(candidate.isValid()) {
                result.add(candidate);
            }
        }

        private boolean isValid() {
            return (x<origin.length) && (y<origin[0].length) && (0<= x) && (0<=y);
        }
    }
}

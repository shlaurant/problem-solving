package validnumber;

import java.util.Stack;

class Solution {
    private static final int NUMBER = 0;
    private static final int EXPONENT = 1;
    private static final int SIGN = 2;
    private static final int POINT = 3;
    private static final int NOTHING = 4;
    private static final int ILLEGAL = -1;

    public boolean isNumber(String s) {
        char[] array = s.toCharArray();
        Stack<Integer> stack = new Stack<>();
        boolean answer = true;
        for (char c : array) {
            switch (whatIs(c)) {
                case NUMBER:
                    stack.push(NUMBER);
                    break;
                case EXPONENT:
                    stack.push(EXPONENT);
                    break;
                case SIGN:

                    break;
                case POINT:
                    break;
                case NOTHING:
                    break;
                case ILLEGAL:
                    answer = false;
                    break;
            }
            if (!answer) {
                break;
            }
        }
        return answer;
    }

    private int whatIs(char c) {
        int answer;
        if (c == 'e') {
            answer = EXPONENT;
        } else if (c == '+' || c == '-') {
            answer = SIGN;
        } else if (c == '.') {
            answer = POINT;
        } else if (isInteger(c)) {
            answer = NUMBER;
        } else if (c == ' ') {
            answer = NOTHING;
        } else {
            answer = ILLEGAL;
        }
        return answer;
    }

    private boolean isInteger(char c) {
        return eq(c, '0') || eq(c, '1') || eq(c, '2') || eq(c, '3')
                || eq(c, '4') || eq(c, '5') || eq(c, '6') || eq(c, '7')
                || eq(c, '8') || eq(c, '9');
    }

    private boolean eq(char c1, char c2) {
        return c1 == c2;
    }
}

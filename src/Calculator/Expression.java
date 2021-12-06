package Calculator;

import java.util.ArrayList;

public class Expression {
    private ArrayList<Character> operatorList;
    private ArrayList<Double> operandList;

    public Expression() {
        this.operatorList = new ArrayList<>();
        this.operandList = new ArrayList<>();
    }

    private boolean isOperator(String o) {
        return "+-*/^()".contains(o);
    }

    private boolean isDoubleNumeric(char str) {
        return Character.isDigit(str) || str == '.';
    }

    private boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);

        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    boolean parenthesisCheck(String str) {
        myStack<Character> stack = new myStack<>();
        char c;
        for (int i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            if (c == '(')
                stack.push(c);
            else if (c == ')')
                if (stack.isEmpty())
                    return false;
                else if (stack.peek() == '(')
                    stack.pop();
                else
                    return false;
        }

        return stack.isEmpty();
    }


    private static int getPriority(char op) {
        return switch (op) {
            case '+', '-' -> 1;
            case '/', '*' -> 2;
            case '^' -> 3;
            case '(', ')' -> 0;
            default -> -1;
        };
    }

    private double calculate(char op, double val1, double val2) {
        return switch (op) {
            case '+' -> val1 + val2;
            case '-' -> val2 - val1;
            case '*' -> val2 * val1;
            case '/' -> val2 / val1;
            case '^' -> Math.pow(val2, val1);
            default -> 0;
        };
    }


    public String infixToPostfix(String infix) {
        myStack<Character> stack = new myStack<>();
        StringBuilder postFix = new StringBuilder();
        StringBuilder temp = new StringBuilder();

        for (int i = 0; i < infix.length(); i++) {
            temp.delete(0, temp.length());
            try {
                while (infix.charAt(i) != ' ') {
                    temp.append(infix.charAt(i));
                    i++;
                }
            } catch (IndexOutOfBoundsException ignored) {
            }

            if (isNumeric(temp.toString())) {
                postFix.append(temp.toString());
                postFix.append(" ");

            } else {
                char operator = temp.charAt(0);

                if (operator == '(') {
                    stack.push(operator);
                } else if (getPriority(operator) > 0) {
                    while (!stack.isEmpty() && getPriority(operator) <= getPriority(stack.peek())) {
                        postFix.append(stack.pop());
                        postFix.append(" ");
                    }
                    stack.push(operator);
                } else if (operator == ')') {
                    if (stack.isEmpty()) {
                        throw new RuntimeException("ERROR: Check Your Parenthesis");
                    } else if (stack.peek() == '(') {
                        stack.pop();
                    } else {
                        while (!stack.isEmpty() && stack.peek() != '(') {
                            postFix.append(stack.pop());
                            postFix.append(" ");
                        }
                        if (stack.isEmpty()) {
                            throw new RuntimeException("ERROR: Check Your Parenthesis");
                        } else if (stack.peek().equals('(')) {
                            stack.pop();
                        }
                    }
                }
            }
        }
        while (!stack.isEmpty()) {
            postFix.append(stack.pop());
            postFix.append(" ");
        }

        return postFix.toString();
    }

    String earlyCheck(String exp) {
        for (int i = 0; i < exp.length(); i++) {
            if (exp.charAt(i) == '(' && i + 1 < exp.length() && (exp.charAt(i + 1) == ')' || exp.charAt(i + 1) == '*' || exp.charAt(i + 1) == '/' || exp.charAt(i + 1) == '^')) {
                return "ERROR: Invalid Expression";
            }
            if (exp.charAt(i) == ')' && i - 1 >= 0 && "+-*/^".contains(String.valueOf(exp.charAt(i - 1)))) {
                return "ERROR: Operator cannot be before ')'";
            }
            if (!isDoubleNumeric(exp.charAt(i)) && !isOperator(String.valueOf(exp.charAt(i)))) {
                return "ERROR: Invalid character";
            }
        }
        return "OK";
    }

    public String getExpression(String in) throws Exception {
        if (!parenthesisCheck(in)) {
            throw new Exception("ERROR: Check Your Parenthesis");
        }
        StringBuilder str = new StringBuilder();
        StringBuilder sb = new StringBuilder();

        //Customize Input
        in = in.replace("(-", "((-1)*");
        in = in.replace("(+", "(");

        if (in.charAt(0) == '-') {
            in = in.replaceFirst("-", "(-1)*");
        }
        if (in.charAt(0) == '+') {
            in = in.replaceFirst("[+]", "");
        }
        for (int i = 0; i < in.length(); i++) {
            Character input = in.charAt(i);
            if (isOperator(input.toString())) {
                sb.append(input);
                if (!input.equals('(') && !input.equals(')')) {
                    try {
                        operatorList.add(input);
                    } catch (Exception ignored) {
                    }
                }
                try {
                    if (in.charAt(i) == '-' && in.charAt(i - 1) == '(') {
                    } else {
                        sb.append(" ");
                    }
                } catch (Exception ignored) {
                }
            } else if (isDoubleNumeric(input)) {
                try {
                    while (isDoubleNumeric(in.charAt(i))) {
                        str.append(in.charAt(i));
                        i++;
                    }
                } catch (Exception ignored) {
                }
                i--;
                try {
                    sb.append(str);
                    operandList.add(Double.parseDouble(str.toString()));
                    sb.append(" ");
                    str = new StringBuilder();
                } catch (Exception exception) {
                    throw new Exception("ERROR: Multiple Points");
                }
            }
        }

        if (!parenthesisCheck(sb.toString())) {
            return "";

        } else if (operandList.size() > operatorList.size() + 1) {
            return "";
        } else {
            System.out.println("Ready: " + sb.toString());
            return sb.toString();
        }
    }

    public double evaluatePostfix(String postfix) {
        myStack<Double> stk = new myStack<>();
        StringBuilder temp = new StringBuilder();

        for (int i = 0; i < postfix.length(); i++) {
            temp.delete(0, temp.length());

            while (postfix.charAt(i) != ' ') {
                temp.append(postfix.charAt(i));
                i++;
            }
            if (isNumeric(temp.toString())) {
                stk.push(Double.valueOf(temp.toString()));
            } else {
                try {
                    stk.push(calculate(temp.charAt(0), stk.pop(), stk.pop()));

                } catch (Exception e) {
                    throw new RuntimeException("ERROR: Check Your Operators");
                }
            }
        }
        return stk.pop();
    }
}
//Test:
//-(-1*(-2*(-9))*3)-4*5*(1-2^3^2)*6-((4/8+3*8-7/2+7)*9)*(4/3*7)
//55+(58*9^2+(665+699)+(954*8*(7/8+59)-(59*955+6))+(65-5/8*(8*(92/((96))))+16)*5)+(-2)^(3^2)
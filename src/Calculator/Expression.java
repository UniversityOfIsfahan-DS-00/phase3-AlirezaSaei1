package Calculator;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;

public class Expression {
    private final String operators = "+-*/^()";
    private ArrayList<Character> operatorList;
    private ArrayList<Double> operandList;

    public Expression() {
        this.operatorList = new ArrayList<>();
        this.operandList = new ArrayList<>();
    }

    private boolean isOperator(String o) {
        return operators.contains(o);
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

    private boolean parenthesisCheck(String str) {
        Stack<Character> stack = new Stack<>();
        char c;

        for (int i = 0; i < str.length(); i++) {
            c = str.charAt(i);

            if (c == '(')
                stack.push(c);
            else if (c == ')')
                if (stack.empty())
                    return false;
                else if (stack.peek() == '(')
                    stack.pop();
                else
                    return false;
        }
        return stack.empty();
    }


    private static int getPriority(char op) {
        switch (op) {
            case '+':
            case '-':
                return 1;

            case '/':
            case '*':
                return 2;

            case '^':
                return 3;
            case '(':
            case ')':
                return 0;

            default:
                return -1;
        }
    }

    private double calculate(char op, double val1, double val2) {
        switch (op) {
            case '+':
                return val1 + val2;

            case '-':
                return val2 - val1;

            case '*':
                return val2 * val1;

            case '/':
                return val2 / val1;

            case '^':
                return Math.pow(val2, val1);

            default:
                return 0;
        }
    }


    public String infixToPostfix(String infix){
        Stack<Character> stack = new Stack<>();
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
                    if (stack.empty()) {
                        throw new RuntimeException("ERROR: Check Your Parenthesis");
                    } else if (stack.peek() == '(') {
                        stack.pop();
                    } else {
                        while (!stack.empty() && stack.peek() != '(') {
                            postFix.append(stack.pop());
                            postFix.append(" ");
                        }
                        if (stack.empty()) {
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

    public String earlyCheck(String exp){
        for(int i=0; i<exp.length(); i++){
            if(exp.charAt(i) == '(' && i+1<exp.length() && (exp.charAt(i+1)==')' || exp.charAt(i+1)=='*' || exp.charAt(i+1)=='/' || exp.charAt(i+1)=='^')){
                return "ERROR: Invalid Expression";
            }
            if(exp.charAt(i)==')' && i-1>=0 && "+-*/^".contains(String.valueOf(exp.charAt(i-1)))){
                return  "ERROR: Operator cannot be before ')'";
            }
            if(!isDoubleNumeric(exp.charAt(i)) && !isOperator(String.valueOf(exp.charAt(i)))){
                return "ERROR: Invalid character";
            }
        }
        return "OK";
    }

    public String getExpression(String in) throws Exception{
        if(!parenthesisCheck(in)){
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
                    } catch (Exception ignored) {}
                }
                try {
                    if (in.charAt(i)== '-' && in.charAt(i - 1) == '(') {
                    } else {
                        sb.append(" ");
                    }
                }catch (Exception ignored){}
            } else if (isDoubleNumeric(input)) {
                try {
                    while (isDoubleNumeric(in.charAt(i))) {
                        str.append(in.charAt(i));
                        i++;
                    }
                }catch (Exception ignored){}
                i--;
                try {
                    sb.append(str);
                    operandList.add(Double.parseDouble(str.toString()));
                    sb.append(" ");
                    str = new StringBuilder();
                }catch (Exception exception){
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
        Stack<Double> stk = new Stack<>();
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
                } catch (EmptyStackException e) {
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
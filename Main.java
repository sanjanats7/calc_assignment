package org.example;

import java.util.Scanner;
import java.util.Stack;

public class Main {

    enum Operations {
        ADD('+', 1),
        SUBTRACT('-', 1),
        MULTIPLY('*', 2),
        DIVIDE('/', 2);

        private final char symbol;
        private final int precedence;

        Operations(char symbol, int precedence) {
            this.symbol = symbol;
            this.precedence = precedence;
        }

        public char getSymbol() {
            return symbol;
        }

        public int getPrecedence() {
            return precedence;
        }

        public static Operations fromChar(char symbol) {
            for (Operations op : values()) {
                if (op.getSymbol() == symbol) {
                    return op;
                }
            }
            throw new IllegalArgumentException("Invalid operator: " + symbol);
        }

        public static boolean isOperator(char symbol) {
            for (Operations op : values()) {
                if (op.getSymbol() == symbol) {
                    return true;
                }
            }
            return false;
        }
    }

    public double evaluate(String input) {
        Stack<Double> values = new Stack<>();
        Stack<Operations> operators = new Stack<>();

        int i = 0;
        while (i < input.length()) {
            char now = input.charAt(i);
            //for space
            if (now == ' ') {
                i++;
                continue;
            }

           //for numbers
            if (Character.isDigit(now) || now == '.') {
                int start = i;
                while (i < input.length() && (Character.isDigit(input.charAt(i)) || input.charAt(i) == '.')) {
                    i++;
                }
                double number = Double.parseDouble(input.substring(start, i));
                values.push(number);
                continue;
            }

            //for brackets
            if (now == '(') {
                int bracketCount = 1;
                int start = i + 1;
                while (bracketCount > 0 && i + 1 < input.length()) {
                    i++;
                    char ch = input.charAt(i);
                    if (ch == '(') bracketCount++;
                    else if (ch == ')') bracketCount--;
                }
                String innerExpression = input.substring(start, i);
                double innerValue = evaluate(innerExpression);
                values.push(innerValue);
            } else if (Operations.isOperator(now)) {
                while (!operators.isEmpty() && operators.peek().getPrecedence() >= Operations.fromChar(now).getPrecedence()) {
                    performOperation(values, operators.pop());
                }
                operators.push(Operations.fromChar(now));
            }
            i++;
        }
        //performing operations
        while (!operators.isEmpty()) {
            performOperation(values, operators.pop());
        }

        return values.pop();
    }

    private static void performOperation(Stack<Double> values, Operations op) {
        double b = values.pop();
        double a = values.pop();
        double result;

        switch (op) {
            case ADD -> result = a + b;
            case SUBTRACT -> result = a - b;
            case MULTIPLY -> result = a * b;
            case DIVIDE -> {
                if (b == 0) throw new ArithmeticException("Division by zero error.");
                result = a / b;
            }
            default -> throw new IllegalArgumentException("Unsupported operation.");
        }

        values.push(result);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Main calculator = new Main();

        while (true) {
            System.out.print("Enter a mathematical expression (or type 'exit' to quit): ");
            String input = sc.nextLine();

            if (input.equalsIgnoreCase("exit")) {
                break;
            }

            try {
                if (input.matches(".*[a-zA-Z].*")) {
                    throw new IllegalArgumentException("Alphabetical characters are not allowed in the expression.");
                }
                double result = calculator.evaluate(input);
                System.out.println("Result: " + result);
            } catch (ArithmeticException e) {
                System.out.println("Math Error: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Input Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Error: Invalid expression.");
            }
        }

        sc.close();
    }


}

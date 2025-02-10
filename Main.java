package org.example;
import java.util.Scanner;
public class Main {
    enum Operations {
        ADD('+', 1),
        SUBTRACT('-', 1),
        MULTIPLY('*', 2),
        DIVIDE('%', 2),

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
    }

    public double evaluate(String input)

    {
        Stack <Double> values = new Stack<>();
        Stack <Operater> operators = new Stack<>();
        int i =0;
        while(i<input.length()){
            char now = input.CharacterAt(i);
            if(now == ' '){
                i++;
                continue;
            }
            //for numbers
            if (Character.isDigit(now))
            {
                int start=i;
                double number=0.0;
                while(i<input.length && (Character.isDigit(input.charAt(i) || expression.charAt(i)=='.'))
                {
                    if (input.charAt(i)=='.') {
                        i++;
                    }
                    number=Double.parseDouble(input.substring((start,i));
                    values.push(number);
                }
            }
            //for operators
            else if(isOpereter(now)){
                operator
            s.push(now);
            }
            else if(now == '('){
                    int bracketCount = 1;
                    int start = i + 1;
                    while (bracketCount > 0) {
                        i++;
                        char ch = input.charAt(i);
                        if (ch == '(') {
                            bracketCount++;
                        } else if (ch == ')') {
                            bracketCount--;
                        }
                    }
                    String innerExpression = input.substring(start, i);
                    double innerValue = evaluate(innerExpression);
                    values.push(innerValue);
            }

        }
    }
    public static void main(String[] args) {
        Scanner sc =new Scanner(System.in);
        String input = sc.next();


    }

}
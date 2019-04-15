import java.util.Arrays;
import java.util.Scanner;

public class MathParser {
    private static double result;
    private static double arg1;
    private static double arg2;


    private static Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) {
        System.out.print(parse(parseExpression(scanner.next())));
    }

    private static double parse(char[] expression) {
        expression = dropBrackets(expression);
        if (countActions(expression) == 0){
            return Double.parseDouble(String.valueOf(expression));
        }
        else if (countActions(expression) == 1) {
            char operation = '0'; // стыдно, надо исправить
            for (int i = 0; i < expression.length; i++){
                if (!Character.isDigit(expression[i])){
                    arg1 = Double.parseDouble(String.valueOf(Arrays.copyOfRange(expression, 0, i)));
                    arg2 = Double.parseDouble(String.valueOf(Arrays.copyOfRange(expression, i+1, expression.length)));
                    operation = expression[i];
                }
            }
            switch (operation){
                case '+': result = arg1 + arg2; break;
                case '-': result = arg1 - arg2; break;
                case '*': result = arg1 * arg2; break;
                case '/': result = arg1 / arg2; break;
            }
            return result;
        }
        else {
            int lastActionPosition = returnLastAction(expression);
            switch (expression[lastActionPosition]){
                case '+': result = parse(Arrays.copyOfRange(expression, 0, lastActionPosition))
                            + parse(Arrays.copyOfRange(expression, lastActionPosition+1, expression.length));
                    break;
                case '-': result = parse(Arrays.copyOfRange(expression, 0, lastActionPosition))
                            - parse(Arrays.copyOfRange(expression, lastActionPosition+1, expression.length));
                    break;
                case '*': result = parse(Arrays.copyOfRange(expression, 0, lastActionPosition))
                            * parse(Arrays.copyOfRange(expression, lastActionPosition+1, expression.length));
                    break;
                case '/': result = parse(Arrays.copyOfRange(expression, 0, lastActionPosition))
                            / parse(Arrays.copyOfRange(expression, lastActionPosition+1, expression.length));
                    break;
            }
            return result;
        }
    }

    private static char[] dropBrackets(char[] expression){
        if(expression[0] == '('){
            return Arrays.copyOfRange(expression, 1, expression.length-1);
        }
        else
            return expression;
    }

    private static char[] parseExpression(String incomingExpression) {
        return incomingExpression.toCharArray();
    }

    private static int countActions(char[] expression) {
        int count = 0;
        for (char c : expression) {
            if (c == '+' || c == '-' || c == '*' || c == '/') {
                count++;
            }
        }
        return count;
    }

    private static int returnLastAction(char[] expression) {
        int lastActionPosition = 0;
        char lastAction = '0';
        for (int i = 0; i < expression.length; i++) {
            if (expression[i] == '(' && i != 0) {
                for (int k = i; k < expression.length; k++) {
                    if (expression[k] == ')') {
                        i = k;
                        break;
                    }
                }
            }
            if (lastAction == '0' && (expression[i] == '+'
                    || expression[i] == '-'
                    || expression[i] == '*'
                    || expression[i] == '/')) {
                lastAction = expression[i];
                lastActionPosition = i;
            } else if ((lastAction == '+' || lastAction == '-')
                    && (expression[i] == '+'
                    || expression[i] == '-')) {
                lastAction = expression[i];
                lastActionPosition = i;
            } else if ((lastAction == '*' || lastAction == '/')
                    && (expression[i] == '+'
                    || expression[i] == '-'
                    || expression[i] == '*'
                    || expression[i] == '/')) {
                lastAction = expression[i];
                lastActionPosition = i;
            }
        }
        return lastActionPosition;
    }
}

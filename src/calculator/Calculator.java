package calculator;

import stack.Stack;

public class Calculator {

    private Stack<Integer> operands;

    public Calculator() {
        operands = new Stack<>();
    }

    public void saveOperand(Integer operand) {
        operands.push(operand);
    }

    public Integer calculate(Character operator) {

        Integer fistOperand = operands.pop();
        Integer secondOperand = operands.pop();
        Integer result = 0;

        switch (operator) {
            case '+':
                result =  secondOperand + fistOperand;
                break;
            case '-':
                result = secondOperand - fistOperand;
                break;
            case '*':
                result = secondOperand * fistOperand;
                break;
            case '/':
                result = secondOperand / fistOperand;
                break;
        }

        operands.push(result);
        return result;
    }

    public Integer getResult() {
        return operands.pop();
    }

}

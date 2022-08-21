import calculator.Calculator;
import utils.Number;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);
        String digit;

        Calculator calculator = new Calculator();
        int result, number;

        while (s.hasNextLine()) {
            digit = s.nextLine();

            if (Number.isStringInt(digit)) {
                number = Integer.parseInt(digit);
                calculator.saveOperand(number);
            } else {
                calculator.calculate(digit.charAt(0));
            }
        }

        result = calculator.getResult();
        System.out.println("Saida: " + result);
    }
}

import calculator.Calculator;
import token.Token;
import token.TokenType;
import utils.Number;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {

        try {
            List<Token> tokens = scan(System.getProperty("user.dir")+ "\\src\\Calc1.stk");
            Calculator calculator = new Calculator();
            int result;

            while (!tokens.isEmpty()) {
                Token token = tokens.remove(0);
                System.out.println(token);

                if (token.type == TokenType.NUM) {
                    calculator.saveOperand(token);
                } else if (token.type != TokenType.NUM) {
                    calculator.calculate(token);
                }
            }

            result = calculator.getResult();
            System.out.println("\nSaida: " + result + "\n");
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private static List<Token> scan(String filename) throws FileNotFoundException {
        File file = new File(filename);

        Scanner scan = new Scanner(file);
        List<Token> tokens = new ArrayList<>();

        while (scan.hasNextLine()) {
            String line = scan.nextLine().trim();

            Token token;

            if (line.equals("+")) {
                token = new Token(TokenType.PLUS, line);
            } else if (line.equals("-")) {
                token = new Token(TokenType.MINUS, line);
            } else if (line.equals("*")) {
                token = new Token(TokenType.STAR, line);
            } else if (line.equals("/")) {
                token = new Token(TokenType.SLASH, line);
            } else if (Number.isStringInt(line)) {
                token = new Token(TokenType.NUM, line);
            } else {
                scan.close();

                throw new RuntimeException("Error: Unexpected character: " + line);
            }

            tokens.add(token);
        }

        scan.close();

        return tokens;
    }
}

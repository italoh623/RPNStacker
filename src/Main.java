import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

import expr.Expr;
import interpreter.Interpreter;
import interpreter.InterpreterError;
import lexer.LexError;
import lexer.Scanner;
import lexer.Token;
import parser.Parser;
import parser.ParserError;

public class Main {

    private static final Interpreter interpreter = new Interpreter(new HashMap<>());
    private static boolean hasError = false;

    public static void main(String[] args) throws IOException {
        args = new String [2];
        args [0] = "C:\\Users\\Antonio\\IdeaProjects\\RPNStacker Adhoc\\src\\Calc1.stk";
        args [1] = "C:\\Users\\Antonio\\IdeaProjects\\RPNStacker Adhoc\\src\\Calc2.stk";

        run(args);
    }

    private static void runFile(String sourceFilePath) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(sourceFilePath));
        String sourceProgram =
                new String(bytes, Charset.defaultCharset());
        run(sourceProgram);

        if (hasError) System.exit(65);
    }

    private static void run(String source) {
        try {
            Scanner scanner = new Scanner(source);
            List<Token> tokens = scanner.scan();

            printTokens(tokens);

            Parser parser = new Parser(tokens);
            Expr expr = parser.parse();

            interpreter.env.put("y", "10");
            interpreter.env.put("x", "3");

            System.out.println(interpreter.interp(expr));
        } catch (LexError e) {
            error("Lex", e.getMessage());
            hasError = true;
        }
        catch (ParserError e) {
            error("Parser", e.getMessage());
            hasError = true;
        }
        catch (InterpreterError e) {
            error("Interpreter", e.getMessage());
            hasError = true;
        }
    }

    private static void run(String[] args) throws IOException {
        if(args.length > 0) {
            for (String arg : args) {
                runFile(arg);
            }
        }
    }

    private static void printTokens(List<Token> tokens) {
        for (Token token : tokens) {
            System.out.println(token);
        }
        System.out.println();
    }

    private static void error(String typeError, String message) {
        System.err.println("[" + typeError + "] Error: " + message);
        hasError = true;
    }
}
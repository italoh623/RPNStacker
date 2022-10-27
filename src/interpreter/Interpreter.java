package interpreter;

import java.util.HashMap;

import expr.Expr;

/**
 * @author Henrique Rebelo
 */
public class Interpreter implements Expr.Visitor<Integer> {

    public final HashMap<String, String> env;

    public Interpreter(HashMap<String, String> env) {
        this.env = env;
    }

    public int interp(Expr expression) {
        int value = evaluate(expression);

        return value;
    }

    @Override
    public Integer visitNumberExpr(Expr.Number expr) {
        return Integer.parseInt(expr.value);
    }

    @Override
    public Integer visitIdentifierExpr(Expr.Identifier expr) {
        if (!this.env.containsKey(expr.key)) {
            throw new InterpreterError(expr.key + " cannot be resolved");
        }

        return Integer.parseInt(this.env.get(expr.key));
    }

    @Override
    public Integer visitBinopExpr(Expr.Binop expr) {
        int left = evaluate(expr.left);
        int right = evaluate(expr.right);
        int result = 0;

        switch (expr.operator.type) {
            case PLUS:
                result = left + right;
                break;
            case MINUS:
                result = left - right;
                break;
            case SLASH:
                result = left / right;
                break;
            case STAR:
                result = left * right;
                break;
            default:
                break;
        }

        return result;
    }

    private int evaluate(Expr expr) {
        return expr.accept(this);
    }
}
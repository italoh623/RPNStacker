package parser;

import java.util.List;
import java.util.Stack;

import expr.Expr;
import lexer.Token;
import lexer.TokenType;

public class Parser {

    private final List<Token> tokens;
    // The internal stack used for shift-reduce parsing
    private Stack<Expr> stack = new Stack<>();
    private int current = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    //Parsing Expressions
    public Expr parse() {
        try {
            return expression();
        } catch (java.util.EmptyStackException error) {
            throw new ParserError("incomplete binop expression");
        }
    }

    // -------------------------------------------------------------
    // HELPERS METHODS
    // -------------------------------------------------------------
    private Expr expression() {
        while (!isAtEnd()) {
            if(this.match(TokenType.NUM)) {
                this.stack.push(this.number());
            } else if (this.match(TokenType.ID)) {
                this.stack.push(this.identifier());
            } else if(this.match(TokenType.PLUS, TokenType.MINUS,
                    TokenType.SLASH, TokenType.STAR)) {
                this.stack.push(this.binop());
            }
            this.advance();
        }
        return this.stack.pop();
    }

    private Expr number() {
        return new Expr.Number(peek().lexeme);
    }

    private Expr identifier() {
        return new Expr.Identifier(peek().lexeme);
    }

    private Expr binop() {
        return new Expr.Binop(this.stack.pop(), this.stack.pop(), this.peek());
    }

    private boolean match(TokenType... types) {
        for (TokenType type : types) {
            if (check(type)) {
                return true;
            }
        }

        return false;
    }

    private boolean check(TokenType type) {
        if (isAtEnd()) return false;
        return peek().type == type;
    }

    private Token advance() {
        if (!isAtEnd()) current++;
        return previous();
    }

    private boolean isAtEnd() {
        return peek().type == TokenType.EOF;
    }

    private Token peek() {
        return tokens.get(current);
    }

    private Token previous() {
        return tokens.get(current - 1);
    }
}
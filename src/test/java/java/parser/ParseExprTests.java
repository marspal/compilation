package java.parser;

import lexer.Lexer;
import lexer.LexicalException;
import org.junit.jupiter.api.Test;
import parser.ast.ASTNode;
import parser.ast.Expr;
import parser.util.NotImplementedException;
import parser.util.ParseException;
import parser.util.ParserUtils;
import parser.util.PeekTokenIterator;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParseExprTests {
    @Test
    public void simple() throws LexicalException, ParseException, NotImplementedException {
        var expr = createExpr("1+1+1");
        System.out.println(expr);
        expr.print(0);
        assertEquals("1 1 1 + +", ParserUtils.toPostfixExpression(expr));

    }

    @Test
    public void simple1() throws LexicalException, ParseException, NotImplementedException {
        var expr = createExpr("\"1\" == \"\"");
        assertEquals("\"1\" \"\" ==", ParserUtils.toPostfixExpression(expr));
    }

    @Test
    public void  complex() throws LexicalException, ParseException, NotImplementedException {
        var expr1 = createExpr("1+2*3");
        var expr2 = createExpr("1*2+3");
        var expr3 = createExpr("10*(7+4)");
        var expr4 = createExpr("(1*2!=7)==3!=4*5+6");

        assertEquals("1 2 3 * +", ParserUtils.toPostfixExpression(expr1));
        assertEquals("1 2 * 3 +", ParserUtils.toPostfixExpression(expr2));
        assertEquals("10 7 4 + *", ParserUtils.toPostfixExpression(expr3));
        assertEquals("1 2 * 7 != 3 4 5 * 6 + != ==", ParserUtils.toPostfixExpression(expr4));
        expr4.print(0);
    }

    private ASTNode createExpr(String src) throws LexicalException, ParseException {
        var lexer = new Lexer();
        var tokens = lexer.analyse(src.chars().mapToObj(x -> (char)x));
        var tokenIt = new PeekTokenIterator(tokens.stream());
        return Expr.parse(tokenIt);
    }
}

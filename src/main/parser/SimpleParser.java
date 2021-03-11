package parser;

import parser.ast.*;
import parser.util.ParseException;
import parser.util.PeekTokenIterator;

/**
 * 优先级:
 * 1: & | ^
 * 2: == != > < >= <=
 * 3: + -
 * 4: / *
 * 5: >> <<
 * 5: () ++ -- !
 */

/**
 * 产生式的关系对应一个高阶函数
 * 合并关系: E() E_() -> combine(() -> E(), () -> E_());
 * 竞争关系: E|F -> race(() -> E(), () -> F());
 */
public class SimpleParser {
    // Expr -> digit + Expr | digit
    // digit -> 0|1|2|3|4|5|...|9
    public static ASTNode parse(PeekTokenIterator it) throws ParseException {
        var expr = new Expr(null);
        var scalar = Factor.parse(null, it);
        if(!it.hasNext()){
            return scalar;
        }

        expr.setLexeme(it.peek());
        it.nextMatch("+");
        expr.setLabel("+");
        expr.setType(ASTNodeTypes.BINARY_EXPR);
        expr.addChild(scalar);
        var rightExpr = parse(it);
        expr.addChild(rightExpr);
        return expr;
    }
}

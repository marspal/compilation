package parser.ast;


import parser.util.ParseException;
import parser.util.PeekTokenIterator;

public class CallExpr extends Expr {
    public CallExpr(){
        super();
        this.label = "call";
        this.type = ASTNodeTypes.CALL_EXPR;
    }

    public static ASTNode parse(ASTNode factor, PeekTokenIterator it) throws ParseException {
        var expr = new CallExpr();

        expr.addChild(factor);
        it.nextMatch("(");
        var args = FunctionArgs.parse(it);
        expr.addChild(args);
        it.nextMatch(")");
        return expr;
    }
}

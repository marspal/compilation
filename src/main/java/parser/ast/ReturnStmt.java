package parser.ast;


import parser.util.ParseException;
import parser.util.PeekTokenIterator;

public class ReturnStmt extends Stmt{
    public ReturnStmt(){
        super(ASTNodeTypes.RETURN_STMT, "return");
    }

    public static ASTNode parse(PeekTokenIterator it) throws ParseException {
        var lexeme = it.nextMatch("return");
        var returnStmt = new ReturnStmt();
        var expr = Expr.parse(it);
        returnStmt.setLexeme(lexeme);
        returnStmt.addChild(expr);
        return returnStmt;
    }
}

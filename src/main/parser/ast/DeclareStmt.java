package parser.ast;

import parser.util.ParseException;
import parser.util.PeekTokenIterator;

public class DeclareStmt extends Stmt {
    public DeclareStmt(ASTNode _parent) {
        super(_parent, ASTNodeTypes.DECLARE_STMT, "declare");
    }

    public static ASTNode parse(ASTNode parent, PeekTokenIterator it) throws ParseException {
        var stmt = new DeclareStmt(parent);
        it.nextMatch("var");
        var tkn = it.peek();
        var factor = Factor.parse(parent, it);
        if(factor == null){
            throw new ParseException(tkn);
        }
        stmt.addChild(factor);
        var lexeme = it.nextMatch("=");
        var expr = Expr.parse(parent, it);
        stmt.addChild(expr);
        stmt.setLexeme(lexeme)  ;
        return stmt;
    }

//    public static ASTNode parse(ASTNode parent, PeekTokenIterator it) throws ParseException {
//        var stmt = new DeclareStmt(parent);
//        it.nextMatch("var");
//        var tkn = it.peek();
//        var factor = Factor.parse(parent, it);
//        if(factor == null){
//            throw new ParseException(tkn);
//        }
//        stmt.addChild(factor);
//        var lexeme = it.nextMatch("=");
//        var expr = Expr.parse(parent, it);
//        stmt.addChild(expr);
//        stmt.setLexeme(lexeme);
//        return stmt;
//    }
}

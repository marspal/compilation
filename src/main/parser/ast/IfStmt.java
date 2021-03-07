package parser.ast;

import parser.util.ParseException;
import parser.util.PeekTokenIterator;

public class IfStmt extends Stmt {
    public IfStmt(ASTNode _parent) {
        super(_parent, ASTNodeTypes.IF_STMT, "if");
    }

//    public static ASTNode parse(ASTNode parent, PeekTokenIterator it) throws ParseException {
//        return parseIF(parent, it);
//    }
//
//    public static ASTNode parseIF(ASTNode parent, PeekTokenIterator it) throws ParseException {
//        var lexeme = it.nextMatch("if");
//        it.nextMatch("(");
//        var ifStmt = new IfStmt(parent);
//        ifStmt.setLexeme(lexeme);
//        var expr = Expr.parse(parent, it);
//        ifStmt.addChild(expr);
//        it.nextMatch(")");
//        var block = Block.parse(parent, it);
//        ifStmt.addChild(block);
//
//        var tail = parseTail(parent, it);
//        if(tail != null){
//            ifStmt.addChild(tail);
//        }
//        return ifStmt;
//    }
//
//    public static ASTNode parseTail(ASTNode parent, PeekTokenIterator it) throws ParseException {
//        if(!it.hasNext() || it.peek().getValue().equals("else")){
//            return null;
//        }
//        it.nextMatch("else");
//        var lookahead = it.peek();
//        if(lookahead.getValue().equals("{")){
//            return Block.parse(parent, it);
//        } else if(lookahead.getValue().equals("if")){
//            return parseIF(parent, it);
//        } else {
//            return null;
//        }
//    }
}

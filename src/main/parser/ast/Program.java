package parser.ast;

import parser.util.ParseException;
import parser.util.PeekTokenIterator;

public class Program extends Block {
    public Program(ASTNode _parent){
        super(_parent);
    }
//    private  static ASTNode parse(ASTNode parent, PeekTokenIterator it) throws ParseException, ParseException {
//        var program = new Block(parent);
//        ASTNode stmt = null;
//
//        while ((stmt = Stmt.parseStmt(parent, it)) != null){
//            program.addChild(stmt);
//        }
//
//        return program;
//    }
}

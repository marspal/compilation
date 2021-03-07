package parser.ast;

import lexer.Token;
import parser.util.PeekTokenIterator;

public class Variable extends Factor {
    public Variable(ASTNode _parent, PeekTokenIterator it){
        super(_parent, it);
    }
//    public Variable(ASTNode _parent, Token it) {
//        super(_parent, it);
//    }
}

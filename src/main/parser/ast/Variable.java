package parser.ast;

import lexer.Token;
import parser.util.PeekTokenIterator;

public class Variable extends Factor {
    public Variable(ASTNode _parent){
        super(_parent);
    }

    public Variable(ASTNode _parent, Token token) {
        super(_parent, token, ASTNodeTypes.VARIABLE);
    }
}

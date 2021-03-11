package parser.ast;

import lexer.Token;
import parser.util.PeekTokenIterator;

public class Scalar extends Factor {
    public Scalar(ASTNode _parent){
        super(_parent);
    }

    public Scalar(ASTNode _parent, Token token) {
        super(_parent, token, ASTNodeTypes.SCALAR);
    }

}

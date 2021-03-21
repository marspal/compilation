package parser.ast;

import lexer.Token;

public class Scalar extends Factor {
    public Scalar(){
        super();
    }

    public Scalar(Token token) {
        super(token, ASTNodeTypes.SCALAR);
    }

}

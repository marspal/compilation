package parser.ast;

import lexer.Token;

public class Variable extends Factor {
    public Variable(){
    }

    public Variable(Token token) {
        super(token, ASTNodeTypes.VARIABLE);
    }
}

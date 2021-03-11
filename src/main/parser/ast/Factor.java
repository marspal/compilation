package parser.ast;

import lexer.Token;
import lexer.TokenType;
import parser.util.PeekTokenIterator;

/**
 * 因子: 操作符两边可以计算的东西
 */
public abstract class Factor extends ASTNode {
    public Factor(ASTNode _parent){
        super(_parent);
    }

    public Factor(ASTNode _parent, Token token, ASTNodeTypes type){
        super(_parent);
        this.lexeme = token;
        this.label = token.getValue();
        this.setType(type);
    }

    public static ASTNode parse(ASTNode parent, PeekTokenIterator it){
        var token = it.peek();
        var type = token.getType();
        if(type == TokenType.VARIABLE){
            it.next();
            return new Variable(parent, token);
        } else if(token.isScalar()) {
            it.next();
            return new Scalar(parent, token);
        }
        return null;
    }

}

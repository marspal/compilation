package parser.ast;

import lexer.Token;
import lexer.TokenType;
import parser.util.PeekTokenIterator;

/**
 * 因子: 操作符两边可以计算的东西
 */
public abstract class Factor extends ASTNode {
    public Factor(){
        super();
    }

    public Factor(Token token, ASTNodeTypes type){
        this.lexeme = token;
        this.label = token.getValue();
        this.setType(type);
    }

    public static ASTNode parse(PeekTokenIterator it){
        var token = it.peek();
        var type = token.getType();
        if(type == TokenType.VARIABLE){
            it.next();
            return new Variable(token);
        } else if(token.isScalar()) {
            it.next();
            return new Scalar(token);
        }
        return null;
    }

}

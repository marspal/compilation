package parser.ast;

import lexer.Token;
import lexer.TokenType;
import parser.util.PeekTokenIterator;

/**
 * 因子: 操作符两边可以计算的东西
 */
public abstract class Factor extends ASTNode {
    public Factor(ASTNode _parent, PeekTokenIterator it){
        super(_parent);
        var token = it.next();
        var type = token.getType();
        if(type == TokenType.VARIABLE){
            this.type = ASTNodeTypes.VARIABLE;
        } else if(token.isScalar()){
            this.type = ASTNodeTypes.SCALAR;
        }
        this.lexeme = token;
        this.label = token.getValue();

//        this.lexeme = token;
//        this.label = token.getValue();
    }

//    public static ASTNode parse(ASTNode parent, PeekTokenIterator it){
//        var token = it.peek();
//        var type = token.getType();
//        if(type == TokenType.VARIABLE){
//            it.next();
//            return new Variable(parent, token);
//        } else if(token.isScalar()) {
//            it.next();
//            return new Scalar(parent, token);
//        }
//        return null;
//    }

}

package parser.ast;

import lexer.TokenType;
import parser.util.ParseException;
import parser.util.PeekTokenIterator;

public class FunctionDeclareStmt extends Stmt {
    public FunctionDeclareStmt(ASTNode _parent) {
        super(_parent, ASTNodeTypes.FUNCTION_DECLARE_STMT, "func");
    }

//    public static ASTNode parse(ASTNode parent, PeekTokenIterator it) throws ParseException {
//        it.nextMatch("func");
//
//        var func = new FunctionDeclareStmt(parent);
//        var lexeme = it.peek();
//        var functionVariable = (Variable)Factor.parse(parent, it);
//
//        func.setLexeme(lexeme);
//        func.addChild(functionVariable);
//        it.nextMatch("(");
//        var args = FunctionArgs.parse(parent, it);
//        it.nextMatch(")");
//
//        func.addChild(args);
//        var keyword = it.nextMatch(TokenType.KEYWORD);
//
//        if(!keyword.isType()){
//            throw new ParseException(keyword);
//        }
//
//        functionVariable.setLexeme(keyword);
//        var block = Block.parse(parent, it);
//
//        func.addChild(block);
//        return func;
//    }
//
//    public ASTNode getArgs(){
//        return this.getChild(1);
//    }
//
//    public Variable getFunctionVariable(){
//        return (Variable)this.getChild(0);
//    }
//
//    public String getFuncType(){
//        return this.getFunctionVariable().getTypeLexeme().getValue();
//    }
//
//    public Block getBlock(){
//        return (Block)this.getChild(2);
//    }
}

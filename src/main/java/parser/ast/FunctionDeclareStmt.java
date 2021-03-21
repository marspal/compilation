package parser.ast;

import lexer.TokenType;
import parser.util.ParseException;
import parser.util.PeekTokenIterator;

public class FunctionDeclareStmt extends Stmt {
    public FunctionDeclareStmt() {
        super(ASTNodeTypes.FUNCTION_DECLARE_STMT, "func");
    }

    public static ASTNode parse(PeekTokenIterator it) throws ParseException {
        it.nextMatch("func");
        var func  = new FunctionDeclareStmt();
        var lexeme = it.peek();
        var functionVariable = (Variable)Factor.parse(it);
        func.setLexeme(lexeme);
        func.addChild(functionVariable); // func add() functionVariable: add
        it.nextMatch("(");
        var args = FunctionArgs.parse(it);
        func.addChild(args);
        it.nextMatch(")");
        var keyword = it.nextMatch(TokenType.KEYWORD);
        if(!keyword.isType()){
            throw new ParseException(keyword);
        }
        functionVariable.setLexeme(keyword);
        var block = (Block)Block.parse(it);
        func.addChild(block);
        return func;
    }

    public ASTNode getArgs(){
        return this.getChild(1);
    }

    public Variable getFunctionVariable(){
        return (Variable) this.getChild(0);
    }

    public String getFuncType(){
        return this.getFunctionVariable().getLexeme().getValue();
    }

    public Block getBlock(){
        return (Block) this.getChild(2);
    }
}

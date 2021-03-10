package parser.ast;

import lexer.TokenType;
import parser.util.ParseException;
import parser.util.PeekTokenIterator;

public class FunctionDeclareStmt extends Stmt {
    public FunctionDeclareStmt(ASTNode _parent) {
        super(_parent, ASTNodeTypes.FUNCTION_DECLARE_STMT, "func");
    }

//
}

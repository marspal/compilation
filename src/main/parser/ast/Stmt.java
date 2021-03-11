package parser.ast;

import parser.util.ParseException;
import parser.util.PeekTokenIterator;

/**
 * Program -> Stmts -> Stmt Stmts | null
 *
 * Stmt -> IfStmt | WhileStmt | ForStmt | Function | Block
 *
 * Block -> {Stmts}
 *
 * 变量定义:
 * DeclareStmt -> var Variable = Expr;
 * 执行: DeclareStmt.parse -> match(var) -> Factor.parse -> match(=) -> Expr.parse
 *
 * 变量赋值:
 * AssignStmt -> Variable = Expr
 * 执行: AssignStmt.parse -> Factor.parse -> match(=) -> Expr.parse
 *
 * If语句
 * IfStmt -> If(Expr)Block | If(Expr)Block else Block | If(Expr)Block else IfStmt
 * 简化:
 * IfStmt -> If(Expr)Block Tail
 * Tail -> else {Block} | else IfStmt | null
 * 执行: IFStmt.parse -> match(if() -> Expr.parse -> match())->Block.parse->switch(lookahead) -> else -> IfStmt.parseTail
 *                                                                                               null -> return
 *
 *
 * Function定义
 *
 * Function -> func(Args) Type Block
 * Args -> Type Variable, Args | Type Variable | null
 * ReturnType -> Type | null
 * Type: int|string|void|bool|string
 *
 *
 *
 */
public abstract class Stmt extends ASTNode {
    public Stmt(ASTNode _parent, ASTNodeTypes _type, String _label) {
        super(_parent, _type, _label);
    }

    public static ASTNode parseStmt(ASTNode parent, PeekTokenIterator it) throws ParseException {
        var token = it.next();
        var lookahead = it.peek();
        it.putBack();
        if(token.isVariable() && lookahead.getValue().equals('=')){
            return AssignStmt.parse(parent, it);
        } else if(token.getValue().equals("var")){
            return DeclareStmt.parse(parent, it);
        }
        return null;
    }

//    public static ASTNode parseStmt(ASTNode parent, PeekTokenIterator it) throws ParseException {
//        var token = it.next();
//        var lookahead =it.peek();
//        it.putBack();
//
//        if(token.isVariable() && lookahead.getValue().equals("=")){
//            return AssignStmt.parse(parent, it);
//        } else if(token.getValue().equals("var")){
//            return DeclareStmt.parse(parent, it);
//        } else if(token.getValue().equals("func")){
//            return FunctionDeclareStmt.parse(parent, it);
//        } else if(token.getValue().equals("return")){
//            return ReturnStmt.parse(parent, it);
//        } else if(token.getValue().equals("if")){
//            return IfStmt.parse(parent, it);
//        }
//        return null;
//    }
}

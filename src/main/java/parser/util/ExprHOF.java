package parser.util;


import parser.ast.ASTNode;

// 高阶函数的特性: 延迟计算
@FunctionalInterface
public interface ExprHOF {
    ASTNode hoc() throws ParseException;
}

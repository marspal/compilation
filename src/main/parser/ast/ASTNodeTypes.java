package parser.ast;

public enum ASTNodeTypes {
    BLOCK,
    BINARY_EXPR, // 1 + 1: 二项表达式
    UNARY_EXPR, // i++: 一元表达式
    VARIABLE, // 变量
    SCALAR, // 标量: 1.0 true
    IF_STMT,
    WHILE_STMT,
    FOR_STMT,
    ASSIGN_STMT,
    FUNCTION_DECLARE_STMT,
    DECLARE_STMT
}

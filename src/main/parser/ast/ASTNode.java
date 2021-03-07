package parser.ast;

import lexer.Token;
import parser.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

// 语法分析器: 根据语法规则,将符号(词法单元lexeme、token)流转化成抽象语法树

/**
 * 抽象语法树:
 *
 * 1. 每一个节点是源代码中的一种结构
 * 2. 每一个节点携带源码中的一些关键信息
 * 3. 每个节点的字节点代表着语言上的关系
 *
 * 语法规则: 通常用产生式描述语法规则
 *
 * 产生式: Expr -> Expr + 1 | 1 读成Expr可以推导出Expr+1或1
 * 实例: 可以推导出 1+1+1或1+1+1+1的句子
 * Expr是非终结符, 1是终结符, 终结符对应词法单元
 *
 * 拆分分成两步:
 * - 非终结符(递归)函数 parseExpr(生成一个非叶子节点)
 * - 终结符函数: parseNumber(生成一个叶子节点)
 *
 * 变成 Expr -> 1 + Expr | 1
 *
 * 例如: 解析 1+1+1+1过程
 *
 * parseExpr(1+1+1+1)
 *   eat(1);eat(+);
 *   parseExpr(1+1+1)
 *      eat(1);eat(+);
 *      parseExpr(1+1);
 *          eat(1);eat(+);
 *          parseNumber(1);
 * 生成抽象语法树 右结合、左结合
 *          +                          +
 *      1       +                 +          1
 *          1       1         1       1
 *
 *
 * 实现: 1 + 2 + 3 + 4的Parse
 * - ParseException
 * - 封装PeekTokenIterator
 * - 实现1+2+3+4抽象语法树的解析
 *
 *
 * 产生式: A -> Aa|B  转化成两种形式: A -> BA' A' -> aA' | e
 *
 */

public abstract class ASTNode {
    // 树
    protected ArrayList<ASTNode> children = new ArrayList<>();
    protected ASTNode parent;

    // 关键信息
    protected Token lexeme; // 词法单元
    protected String label; // 备注标签
    protected ASTNodeTypes type; // 当前ASTNode类型

    public ASTNode(ASTNode _parent){
        this.parent = _parent;
    }

    public ASTNode(ASTNode _parent, ASTNodeTypes _type, String _label){
        this.parent = _parent;
        this.type = _type;
        this.label = _label;
    }

    public ASTNode getChild(int index){
        return this.children.get(index);
    }

    public void addChild(ASTNode child){
        child.parent = this;
        children.add(child);
    }

    public Token getLexeme(){
        return lexeme;
    }
    public void setLexeme(Token lexeme){this.lexeme = lexeme;}
    public List<ASTNode> getChildren(){
        return children;
    }

    public void setType(ASTNodeTypes type){
        this.type = type;
    }

    public ASTNodeTypes getType(){
        return this.type;
    }

    public void setLabel(String s) {
        this.label = s;
    }

    public void print(int indent){
        System.out.println(StringUtils.LeftPad(" ", indent * 2) + label);
        for(var child: children){
            child.print(indent + 1);
        }
    }

}

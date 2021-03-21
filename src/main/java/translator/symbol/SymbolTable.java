package translator.symbol;

import lexer.Token;
import lexer.TokenType;

import java.util.ArrayList;

public class SymbolTable  {
    public SymbolTable parent = null;

    private ArrayList<SymbolTable> children;
    private ArrayList<Symbol> symbols;

    // 中间变量的index p0 p1 p2
    private int tempIndex = 0;

    // offsetIndex: 0 1 2
    // var a = 1; var b = 2; var c = 3;
    private int offsetIndex = 0;

    // 当前节点距根节点的距离
    private int level = 0;

    public SymbolTable(){
        this.children = new ArrayList<>();
        this.symbols = new ArrayList<>();
    }

    public void addSymbol(Symbol symbol){
        this.symbols.add(symbol);
        symbol.parent = this;
    }

    public boolean exists(Token lexeme){
        var _symbol = this.symbols.stream().filter(x -> x.lexeme.getValue().equals(lexeme.getValue())).findFirst();
        if(!_symbol.isEmpty()){
            return true;
        }

        if(this.parent != null){
            return this.parent.exists(lexeme);
        }
        return false;
    }

    /**
     *
     * var a = 1;
     * {
     *     {
     *         {
     *             var b = a;
     *         }
     *     }
     * }
     */
    public Symbol cloneFromSymbolTree(Token lexeme, int layoutOffset){
        var _symbol = this.symbols.stream()
                .filter(x -> x.lexeme.getValue().equals(lexeme.getValue()))
                .findFirst();

        // 当前作用域找到它了
        if(!_symbol.isEmpty()){
            var symbol = _symbol.get().copy();
            symbol.setLayerOffset(layoutOffset);
            return symbol;
        }

        if(this.parent != null){
            return this.parent.cloneFromSymbolTree(lexeme, layoutOffset + 1);
        }
        return null;
    }

    public Symbol createSymbolByLexeme(Token lexeme){
        Symbol symbol = null;

        if(lexeme.isScalar()){
            symbol = Symbol.createImmediateSymbol(lexeme);
        } else {
            symbol = cloneFromSymbolTree(lexeme, 0);
            if(symbol == null){
                symbol = Symbol.createAddressSymbol(lexeme, this.offsetIndex ++);
            }
        }
        this.symbols.add(symbol);
        return symbol;
    }

    // 创建零食变量
    public Symbol createVariable() {
//        var a = 1 + 2 * 3;
//        p0 = 2 * 3
        var lexeme = new Token(TokenType.VARIABLE, "p"+this.tempIndex ++);
        var symbol = Symbol.createAddressSymbol(lexeme, this.offsetIndex ++);
        this.addSymbol(symbol);
        return symbol;
    }

    public void addChild(SymbolTable child){
        child.parent = this;
        child.level = this.level + 1;
        this.children.add(child);
    }

    public int localSize(){
        return this.offsetIndex;
    }

    public ArrayList<Symbol> getSymbols(){
        return this.symbols;
    }

    public ArrayList<SymbolTable> getChildren(){
        return this.children;
    }

    // lexeme: 函数名
    public void createLabel(String label, Token lexeme){
        var labelSymbol = Symbol.createLabelSymbol(label, lexeme);
        this.addSymbol(labelSymbol);
    }
}

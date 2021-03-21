package java.translator;

import lexer.Token;
import lexer.TokenType;
import org.junit.jupiter.api.Test;
import translator.symbol.SymbolTable;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SymbolTests {

    @Test
    public void symbolTable(){
        var symbolTable = new SymbolTable();
        symbolTable.createLabel("L0", new Token(TokenType.VARIABLE, "foo"));
        symbolTable.createVariable();
        symbolTable.createSymbolByLexeme(new Token(TokenType.VARIABLE, "a"));
        assertEquals(2, symbolTable.localSize());
    }
}

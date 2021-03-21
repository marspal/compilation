package java.parser;

import lexer.Lexer;
import lexer.LexicalException;
import org.junit.jupiter.api.Test;
import parser.ast.*;
import parser.util.NotImplementedException;
import parser.util.ParseException;
import parser.util.ParserUtils;
import parser.util.PeekTokenIterator;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StmtTests {
    @Test
    public void declare() throws LexicalException, ParseException, NotImplementedException {
        var it = createTokenIt("var i = 100 * 2");
        var stmt = DeclareStmt.parse(it);
        stmt.print(0);
        assertEquals("i 100 2 * =", ParserUtils.toPostfixExpression(stmt));
    }

    @Test
    public void assign() throws LexicalException, ParseException, NotImplementedException {
        var it = createTokenIt("i = 100 * 2");
        var stmt = AssignStmt.parse(it);
        stmt.print(0);
        assertEquals(ParserUtils.toPostfixExpression(stmt), "i 100 2 * =");
    }

    @Test
    public void ifStmt() throws LexicalException, ParseException {
        var it = createTokenIt("if(a){\n"+
                "a = 1\n"+
                "}"
        );
        var stmt = IfStmt.parse(it);
        var expr = stmt.getChild(0);
        var block = stmt.getChild(1);
        var assignStmt = block.getChild(0);

        assertEquals("a", expr.getLexeme().getValue());
        assertEquals("=", assignStmt.getLexeme().getValue());
        stmt.print(0);
    }

    @Test
    public void ifElseStmt() throws LexicalException, ParseException {
        var it = createTokenIt("if(a){\n"+
                    "a = 1\n"+
                "} else {\n"+
                    "a=2\n"+
                    "a=a*3\n"+
                "}"
        );
        var stmt = IfStmt.parse(it);
        var expr = stmt.getChild(0);
        var block = stmt.getChild(1);
        var assignStmt = block.getChild(0);
        var elseBlock = stmt.getChild(2);
        var assignStmt2 = elseBlock.getChild(0);

        assertEquals("a", expr.getLexeme().getValue());
        assertEquals("=", assignStmt.getLexeme().getValue());
        assertEquals("=", assignStmt2.getLexeme().getValue());
        assertEquals(2, elseBlock.getChildren().size());
        stmt.print(0);
    }

    @Test
    public void function() throws ParseException, FileNotFoundException, UnsupportedEncodingException, LexicalException {
        var tokens = Lexer.fromFile("./example/function.ts");

        var functionStmt = (FunctionDeclareStmt) Stmt.parseStmt(new PeekTokenIterator(tokens.stream()));

        functionStmt.print(0);
        var args = functionStmt.getArgs();
        assertEquals("a", args.getChild(0).getLexeme().getValue());
        assertEquals("b", args.getChild(1).getLexeme().getValue());

        var type = functionStmt.getFuncType();
        assertEquals("int", type);

        var functionVariable = functionStmt.getFunctionVariable();
        assertEquals("add", functionVariable.getLexeme().getValue());

        var block = functionStmt.getBlock();
        assertEquals(true, block.getChild(0) instanceof ReturnStmt);

        functionStmt.print(0);
    }

    @Test
    public void function1() throws ParseException, FileNotFoundException, UnsupportedEncodingException, LexicalException {
        var tokens = Lexer.fromFile("./example/recursion.ts");

        var functionStmt = (FunctionDeclareStmt)Stmt.parseStmt(new PeekTokenIterator(tokens.stream()));
        assertEquals("func fact args block", ParserUtils.toBFSString(functionStmt, 4));
        assertEquals("args", ParserUtils.toBFSString(functionStmt.getArgs(), 2));
        assertEquals("block if return", ParserUtils.toBFSString(functionStmt.getBlock(), 3));
        functionStmt.print(0) ;
    }
    private PeekTokenIterator createTokenIt(String src) throws LexicalException {
        var lexer = new Lexer();
        var tokens = lexer.analyse(src.chars().mapToObj(x -> (char)x));
        var tokenIt = new PeekTokenIterator(tokens.stream());
        return tokenIt;
    }
}

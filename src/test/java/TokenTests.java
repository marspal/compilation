import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import common.*;
import lexer.*;
public class TokenTests {
    public static void assertToken(Token token, String value, TokenType tokenType){
        assertEquals(value, token.getValue());
        assertEquals(tokenType, token.getType());
    }
    @Test
    public void test_extractVarOrKeyword(){
        var it1 = new PeekIterator<Character>("if abc".chars().mapToObj(x -> (char)x));
        var it2 = new PeekIterator<Character>("true abc".chars().mapToObj(x -> (char)x));

        var token1 = Token.extractVarOrKeyword(it1);
        var token2 = Token.extractVarOrKeyword(it2);
        assertToken(token1, "if", TokenType.KEYWORD);
        assertToken(token2, "true", TokenType.BOOLEAN);

        it1.next();
        var token3 = Token.extractVarOrKeyword(it1);
        assertToken(token3, "abc", TokenType.VARIABLE);
    }

    @Test
    public void test_extractString() throws LexicalException {
        String[] tests = {
            "\"123\"",
            "\'123\'"
        };

        for(String test: tests){
            PeekIterator<Character> it = new PeekIterator<>(test.chars().mapToObj(c -> (char)c));
            Token token = Token.extractString(it);
            assertToken(token, test, TokenType.STRING);
        }
    }

    @Test
    public void test_extractOperator() throws LexicalException{
        String[] tests = {
            "+ XXX",
            "++ mmm",
            "/=g",
            "==1",
            "&=3982",
            "&777",
            "||xxx",
            "^=111",
            "%7"
        };
        String[] results = {"+","++","/=","==","&=","&","||","^=","%"};
        int i = 0;
        for(String test: tests){
            var it = new PeekIterator<Character>(test.chars().mapToObj(c->(char)c));
            var token = Token.extractOperator(it);
            assertToken(token, results[i++], TokenType.OPERATOR);
        }
    }

    @Test
    public void test_extractNumber() throws LexicalException{
        String[] tests = {
                "+0 aaa",
                "-0 aa",
                ".3 ccc",
                ".5555 ddd",
                "7789.8888 000",
                "-1000.123123*123123"
        };
        for(String test: tests) {
            var it = new PeekIterator<Character>(test.chars().mapToObj(c -> (char) c));
            var token = Token.extractNumber(it);
            var splitValue = test.split("[* ]+");
            assertToken(token, splitValue[0], (test.indexOf('.') != -1 ? TokenType.FLOAT : TokenType.INTEGER));
        }
    }
}

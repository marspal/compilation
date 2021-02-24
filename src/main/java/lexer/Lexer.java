package lexer;

import java.util.ArrayList;
import java.util.stream.Stream;
import common.*;

// 词法分析器
public class Lexer {
    public ArrayList<Token> analyse(Stream source) throws LexicalException {
        var tokens = new ArrayList<Token>();
        var it = new PeekIterator<Character>(source, (char)0);

        while (it.hasNext()) {
            char c = it.next();

            if(c == 0){
                break;
            }
            // look
            char lookahead = it.peek();
            if(c == ' ' || c == '\n'){
                continue;
            }
            if(c == '{' || c=='}' || c == '(' || c== ')'){
                tokens.add(new Token(TokenType.BRACKET, c + ""));
                continue;
            }
            if(c == '"' || c == '\''){
                it.putBack();
                tokens.add(Token.extractString(it));
                continue;
            }
            if(AlphabetHelper.isLetter(c)){
                it.putBack();
                tokens.add(Token.extractVarOrKeyword(it));
                continue;
            }
            if(AlphabetHelper.isNumber(c)){
                it.putBack();
                tokens.add(Token.extractNumber(it));
                continue;
            }
            // + - 3 + 5, + 3 3 * -5;
            if((c == '+' || c == '-') && AlphabetHelper.isNumber(lookahead)){

            }
        }
        return null;
    }
}

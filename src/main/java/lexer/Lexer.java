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

            // 删除注释
            if(c == '/'){
                if(lookahead == '/'){
                    while (it.hasNext() && (c = it.next()) != '\n');
                } else if(lookahead == '*'){
                    boolean valid = false;
                    while (it.hasNext()) {
                        char p = it.next();
                        if(p == '*' && it.peek() == '/'){
                            it.next();
                            valid = true;
                            break;
                        }
                    }
                    if(!valid){
                        throw new LexicalException("comments not match");
                    }
                    continue;
                }

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
            if((c == '+' || c == '-' || c == '.') && AlphabetHelper.isNumber(lookahead)){
                var lastToken = tokens.size() == 0 ? null : tokens.get(tokens.size() - 1);
                // lastToken.isNumber() 估计有问题
                if(lastToken == null || !lastToken.isNumber() || lastToken.isOperator()){
                    it.putBack();
                    tokens.add(Token.extractNumber(it));
                    continue;
                }
            }

            if(AlphabetHelper.isOperator(c)){
                it.putBack();
                tokens.add(Token.extractOperator(it));
                continue;
            }

            throw new LexicalException(c);
        }
        return tokens;
    }
}

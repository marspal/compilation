package lexer;
import common.PeekIterator;
import common.AlphabetHelper;

public class Token {

    TokenType _type;
    String _value;

    public Token(TokenType type, String value){
        this._type = type;
        this._value = value;
    }

    public TokenType getType(){
        return _type;
    }
    public String getValue() { return  _value;}

    public boolean isVariable(){
        return _type == TokenType.VARIABLE;
    }

    // 判断是值类型
    public boolean isScalar(){
        return _type == TokenType.INTEGER || _type == TokenType.FLOAT
                || _type == TokenType.STRING || _type == TokenType.BOOLEAN;
    }
    @Override
    public String toString(){
        return String.format("type %s, value %s", _type, _value);
    }

    // 提取变量Or关键字
    public static Token extractVarOrKeyword(PeekIterator<Character> it){
        String s = "";
        while(it.hasNext()){
            var lookHead = it.peek();
            if(AlphabetHelper.isLiteral(lookHead)) {
                s += lookHead;
            } else {
                break;
            }
            it.next();
        }
        if(Keywords.isKeyword(s)){
            return new Token(TokenType.KEYWORD, s);
        }
        if(s.equals("true") || s.equals("false")){
            return new Token(TokenType.BOOLEAN, s);
        }
        return new Token(TokenType.VARIABLE, s);
    }

    public static Token extractString(PeekIterator<Character> it) throws LexicalException {
        String s = "";
        int state = 0;
        while(it.hasNext()) {
            char c = it.next();
            switch (state) {
                case 0:
                    if (c == '\'') {
                        state = 2;
                    } else {
                        state = 1;
                    }
                    s += c;
                    break;
                case 1:
                    if (c == '"') {
                        return new Token(TokenType.STRING, s + c);
                    }
                    s += c;
                    break;
                case 2:
                    if (c == '\'') {
                        return new Token(TokenType.STRING, s + c);
                    }
                    s += c;
                    break;
            }
        }
        // endLine
        throw new LexicalException("unexpected Error");
    }

    public static Token extractOperator(PeekIterator<Character> it) throws LexicalException{
        int state = 0;
        String s = "";
        while (it.hasNext()){
            char lookahead = it.next();
            switch (state) {
                case 0:
                    switch (lookahead) {
                        case '+':
                            state = 1;
                            break;
                        case '-':
                            state = 2;
                            break;
                        case '*':
                            state = 3;
                            break;
                        case '/':
                            state = 4;
                            break;
                        case '>':
                            state = 5;
                            break;
                        case '<':
                            state = 6;
                            break;
                        case '=':
                            state = 7;
                            break;
                        case '!':
                            state = 8;
                            break;
                        case '&':
                            state = 9;
                            break;
                        case '|':
                            state = 10;
                            break;
                        case '^':
                            state = 11;
                            break;
                        case '%':
                            state = 12;
                            break;
                        case ',':
                            return new Token(TokenType.OPERATOR, ",");
                        case ';':
                            return new Token(TokenType.OPERATOR, ";");
                    }
                    break;
                case 1:
                    if (lookahead == '+') {
                        return new Token(TokenType.OPERATOR, "++");
                    } else if(lookahead == '='){
                        return new Token(TokenType.OPERATOR, "+=");
                    }
                    it.putBack();
                    return new Token(TokenType.OPERATOR, "+");
                case 2:
                    if (lookahead == '-') {
                        return new Token(TokenType.OPERATOR, "--");
                    }else if(lookahead == '='){
                        return new Token(TokenType.OPERATOR, "-=");
                    }
                    it.putBack();
                    return new Token(TokenType.OPERATOR, "-");
                case 3:
                    if(lookahead == '='){
                        return new Token(TokenType.OPERATOR, "*=");
                    }
                    it.putBack();
                    return new Token(TokenType.OPERATOR, "*");
                case 4:
                    if(lookahead == '='){
                        return new Token(TokenType.OPERATOR, "/=");
                    }
                    it.putBack();
                    return new Token(TokenType.OPERATOR, "/");
                case 5:
                    if(lookahead == '='){
                        return new Token(TokenType.OPERATOR, ">=");
                    } else if(lookahead == '>'){
                        return new Token(TokenType.OPERATOR, ">>");
                    }
                    it.putBack();
                    return new Token(TokenType.OPERATOR, ">");
                case 6:
                    if(lookahead == '='){
                        return new Token(TokenType.OPERATOR, "<=");
                    } else if(lookahead == '>'){
                        return new Token(TokenType.OPERATOR, "<<");
                    }
                    it.putBack();
                    return new Token(TokenType.OPERATOR, "<");
                case 7:
                    if(lookahead == '='){
                        return new Token(TokenType.OPERATOR, "==");
                    }
                    it.putBack();
                    return new Token(TokenType.OPERATOR, "=");
                case 8:
                    if(lookahead == '='){
                        return new Token(TokenType.OPERATOR, "!=");
                    }
                    it.putBack();
                    return new Token(TokenType.OPERATOR, "!");
                case 9:
                    if(lookahead == '&'){
                        return new Token(TokenType.OPERATOR, "&&");
                    } else if(lookahead == '='){
                        return new Token(TokenType.OPERATOR, "&=");
                    }
                    it.putBack();
                    return new Token(TokenType.OPERATOR, "&");
                case 10:
                    if(lookahead == '|'){
                        return new Token(TokenType.OPERATOR, "||");
                    }
                    it.putBack();
                    return new Token(TokenType.OPERATOR, "|");
                case 11:
                    if(lookahead == '='){
                        return new Token(TokenType.OPERATOR, "^=");
                    }
                    it.putBack();
                    return new Token(TokenType.OPERATOR, "^");
                case 12:
                    if(lookahead == '='){
                        return new Token(TokenType.OPERATOR, "%=");
                    }
                    it.putBack();
                    return new Token(TokenType.OPERATOR, "%");
            }
        }
        throw new LexicalException("Unexpected Error");
    }

    public static Token extractNumber(PeekIterator<Character> it) throws  LexicalException{
        String s = "";
        int state = 0;

        while(it.hasNext()){
            char lookahead = it.peek();
            switch (state) {
                case 0:
                    if(lookahead == '0'){
                        state = 1;
                    } else if(AlphabetHelper.isNumber(lookahead)){
                        state = 2;
                    } else if(lookahead == '+' || lookahead == '-'){
                        state = 3;
                    } else if(lookahead == '.'){
                        state = 5;
                    }
                    break;
                case 1:
                    if(lookahead == '0'){
                        state = 1;
                    } else if(AlphabetHelper.isNumber(lookahead)){
                        state = 2;
                    } else if(lookahead == '.'){
                        state = 4;
                    }else {
                        return new Token(TokenType.INTEGER, s);
                    }
                    break;
                case 2:
                    if(lookahead == '.'){
                        state = 4;
                    } else if(AlphabetHelper.isNumber(lookahead)){
                        state = 2;
                    } else {
                        return new Token(TokenType.INTEGER, s);
                    }
                    break;
                case 3:
                    if(AlphabetHelper.isNumber(lookahead)){
                        state = 2;
                    } else if(lookahead == '.'){
                        state = 5;
                    } else {
                        throw new LexicalException(lookahead);
                    }
                    break;
                case 4:
                    if(lookahead == '.'){
                        throw new LexicalException(lookahead);
                    } else if(AlphabetHelper.isNumber(lookahead)){
                        state = 20;
                    } else {
                        return new Token(TokenType.FLOAT, s);
                    }
                    break;
                case 5:
                    if(AlphabetHelper.isNumber(lookahead)){
                        state = 20;
                    } else {
                        throw new LexicalException(lookahead);
                    }
                    break;
                case 20:
                    if(AlphabetHelper.isNumber(lookahead)){
                        state = 20;
                    } else if(lookahead == '.'){
                        throw new LexicalException(lookahead);
                    } else {
                        return new Token(TokenType.FLOAT, s);
                    }

            }
            // 消耗值
            it.next();
            s += lookahead;
        }
        throw new LexicalException("unexpected error");
    }

    public boolean isNumber() {
        return this._type == TokenType.INTEGER || this._type == TokenType.FLOAT;
    }

    public boolean isOperator() {
        return this._type == TokenType.OPERATOR;
    }

    public boolean isType() {
        return this._value.equals("boolean")
                || this._value.equals("int")
                || this._value.equals("float")
                || this._value.equals("void")
                || this._value.equals("string");
    }

    public boolean isValue(){
        return isVariable() || isScalar();
    }

}

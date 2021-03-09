package parser.ast;

import parser.util.ParseException;
import parser.util.PeekTokenIterator;

public class FunctionArgs extends ASTNode {
    public FunctionArgs(ASTNode _parent){
        super(_parent);
        this.label = "args";
    }
//
//    public static ASTNode parse(ASTNode parent, PeekTokenIterator it) throws ParseException {
//        var args = new FunctionArgs(parent);
//        while (it.peek().isType()) {
//            var type = it.next();
//            var variable = (Variable) Factor.parse(parent, it);
//            variable.setLexeme(type);
//            args.addChild(variable);
//            if(!it.peek().getValue().equals(")")){
//                it.nextMatch(",");
//            }
//        }
//        return args;
//    }
}

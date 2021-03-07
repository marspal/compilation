package parser.util;

import org.junit.platform.commons.util.StringUtils;
import parser.ast.ASTNode;

import java.util.ArrayList;
import java.util.LinkedList;

public class ParserUtils {
    public static String toPostfixExpression(ASTNode node) throws NotImplementedException {
        // left op right -> left right op
        String leftStr = "";
        String rightStr = "";

        switch (node.getType()) {
            case BINARY_EXPR:
                leftStr = toPostfixExpression(node.getChild(0));
                rightStr = toPostfixExpression(node.getChild(1));
                return leftStr + " " + rightStr + " " + node.getLexeme().getValue();
            case VARIABLE:
            case SCALAR:
                return node.getLexeme().getValue();
        }
        throw new NotImplementedException("not impl. ");
    }

//    public static String toBFSString(ASTNode root, int max){
//        var  queue = new LinkedList<ASTNode>();
//        var  list = new ArrayList<String>();
//
//        queue.add(root);
//
//        int c = 0;
//        while(queue.size() > 0 && c++ < max){
//            var node = queue.poll();
//            list.add(node.getLabel());
//            for(var child: node.getChildren()){
//                queue.add(child);
//            }
//        }
//        return StringUtils.join(list, " ");
//    }
}

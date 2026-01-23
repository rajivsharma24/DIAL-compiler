package ast.expressions;
import java.util.List;
import ast.ASTNode;
import ast.NodeKind;
import ast.types.T;

public abstract class E extends ASTNode {
    public NodeKind nodeKind() {return NodeKind.EXPRESSION;}
    public String toString() {return "";}
}
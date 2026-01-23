package ast.instructions;
import ast.ASTNode;
import ast.NodeKind;

public abstract class I extends ASTNode {
    public NodeKind nodeKind() {return NodeKind.INSTRUCTION;}
    public String toString() {return "";}

}

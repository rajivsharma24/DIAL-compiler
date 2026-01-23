package ast.declarations;
import ast.ASTNode;
import ast.NodeKind;
import ast.PilaTablaSimbolos;

public abstract class DEC extends ASTNode {
	protected Boolean export = Boolean.FALSE;
	public void isExport() {this.export = Boolean.TRUE;}
    public NodeKind nodeKind() {return NodeKind.DECLARATION;}
    public String toString() {return "";}
	public int getMemory() {return 0;}
	public abstract void bindM();
}

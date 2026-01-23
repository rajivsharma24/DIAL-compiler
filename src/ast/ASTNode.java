package ast;
import ast.types.T;

public abstract class ASTNode {
	
	protected ASTNode nodo; 
	protected T tipoAST;
	
    public void type() {}
    public void bind() {}
    public T getT() {
    	return tipoAST;
    }
    public void generateCode() {} 
    public abstract NodeKind nodeKind();
    public abstract String toString();
    public int setDelta(int delta) {return delta;}
}

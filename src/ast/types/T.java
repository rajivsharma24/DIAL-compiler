package ast.types;
import java.util.ArrayList;

import ast.ASTNode;
import ast.NodeKind;

public abstract class T extends ASTNode {
    public abstract KindT kind();
    public NodeKind nodeKind() {return NodeKind.TYPE;}
    public String toString() {return "";}
    public boolean compatible(T t) {return false;}
    public abstract int size();
    protected ArrayList<Kind32> kind32;
    public ArrayList<Kind32> getKind32() { 
    	if(kind32 == null) {
    		setKind32();
    	}
    	return kind32; 
    }
	protected abstract void setKind32();
}

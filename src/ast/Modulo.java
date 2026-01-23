package ast;
import java.util.ArrayList;
import ast.declarations.DEC;
import ast.declarations.DECVar;

public class Modulo extends ASTNode{
	
	private ArrayList<DEC> decs;
	
	private String id = null;
    
    public Modulo(ArrayList<DEC> decs, String id) {
        this.decs = decs;
        this.id = id;
    }

    @Override
    public NodeKind nodeKind() {
        return NodeKind.MODULE;
    }

    @Override
    public String toString() {
    	return "MODULE_" + id + "(" + decs + ")";
    }

    
    public void bind() {
    	for(DEC d : decs) {
    		d.bindM();
    	}
    }
    
    public void type() {
    	for(DEC d : decs) {
    		d.type();
    	}
    }
    
	@Override
	public int setDelta(int delta) {
		int deltaLocal = delta;
		for(DEC dec : this.decs) {
			deltaLocal = dec.setDelta(deltaLocal);
		}
		return deltaLocal;
	}
	
	public int getMemory() {
		int m = 0;
		for(DEC dec : decs) {
			m += dec.getMemory();
		}
		return m;
	}
	
	public void generaCodigoGlobal() {
		for(DEC dec : decs) {
			if(dec instanceof DECVar) {
				dec.generateCode();
			}
		}
	}
	
	public void generateCode() {
		for(DEC dec : decs) {
			if(!(dec instanceof DECVar)) {
				dec.generateCode();
			}
		}
	}

	

	public void bindInterno() {
		Programa.getPila().abreBloque();
    	for(DEC d : decs) {
    		d.bind();
    	}
    	Programa.getPila().cierraBloque();
	}
}

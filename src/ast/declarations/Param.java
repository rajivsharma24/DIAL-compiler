package ast.declarations;
import ast.types.T;
import ast.Programa;

public class Param extends DEC{
	
	private T type;
	private Boolean isRef;
	private String id;
	private int delta;
	
	public Param(T type, boolean isRef, String id) {
		this.type = type;
		this.isRef = isRef;
		this.id = id;
	}
	
	@Override
	public String toString() {
		if(isRef) {
			return type + "*REF*" + id; 
		}
		return type + " " + id;
	}
	
	public void bind() {
		type.bind();
		Programa.getPila().insertaId(id,this);
	}
	
	public void type() {
		type.type();
		this.tipoAST = type.getT();
    }
	
	
	public boolean isRef() {
		return this.isRef;
	}
	
	public String getId() {
		return id;
	}

	@Override
	public int setDelta(int delta) {
		this.delta = delta;
		return delta + (isRef ? 4 : this.tipoAST.size());
	}

	public void calculateAddress() {
		Programa.getCode().println(" i32.const " + delta);
		Programa.getCode().println(" local.get $localsStart"); 
		Programa.getCode().println(" i32.add");
		if(isRef) {
			Programa.getCode().println(" i32.load");  //importante
		}
		
	}
	
	public int getDelta() {
		return delta;
	}

	@Override
	public void bindM() {
		// TODO Auto-generated method stub
		
	}
}

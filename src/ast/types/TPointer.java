package ast.types;

import java.util.ArrayList;

public class TPointer extends T{
	
	private T tipo;
	
	
	public TPointer(T tipo) {
		this.tipo  = tipo;
	}
	
	public KindT kind() {
		return KindT.POINTER;
	}
	   
	public String toString() {return tipo + "@";}
	
	public void bind() {
		tipo.bind();
	}
	
	public void type() {
		tipo.type();
		tipo = tipo.getT();
		this.tipoAST = this;
	}
	
	public T getTPointer() {
		return tipo;
	}
	
	public boolean compatible(T t) {
		   if(t.kind() == KindT.NULL) {
			   return true;
		   }
		   else if(t.kind() != KindT.POINTER) {
			   return false;
		   }
		   else {
			   TPointer t2 = (TPointer)t;
			   return this.tipo.compatible(t2.getTPointer()) && t2.getTPointer().compatible(this.tipo); //no permitimos compatibilidad en punteros por lios wasm
		   }
	}
	
	public int size() {
		return 4;
	}

	@Override
	protected void setKind32() {
		this.kind32 = new ArrayList<>();
		kind32.add(Kind32.I32);
	}
}
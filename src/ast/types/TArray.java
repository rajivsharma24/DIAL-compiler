package ast.types;
import ast.expressions.E;
import ast.types.T;

import java.util.ArrayList;

import ast.Programa;
import ast.expressions.consts.EInt;

public class TArray extends T{
	
	private EInt exp = null; //de momento me restrinjo a EInt
	private T type;
	
	public TArray(T type, EInt exp) {
		this.type = type;
		this.exp = exp;
	}
	
	public TArray(T type) {
		this.type = type;
	}
	

	public KindT kind() {
		return KindT.ARRAY;
	}
	
	public T getTArr() {
		if(this.type.kind() != KindT.ARRAY) {
			return this.type;
		}
		else {
			TArray t = (TArray) this.type;
			return(new TArray(t.getTArr(),exp));
		}
	}
	   
	public String toString() {
		if(exp != null) {
			return type + "[" + exp + "]";
		}
		return type + "[]";
	}
	
	public void bind() {
		type.bind();
		if(exp != null) {
			exp.bind();
		}
	}
	
	public void type() {
		type.type();
		if(exp != null) {
			exp.type();
			if (exp.getT().kind() != KindT.INT) {
				Programa.addError("La expresion '" + exp + "' no es un tamaño valido");
			}
		}
		this.type = type.getT();
		this.tipoAST = this;
    }
	
	public int getN() {
		return exp.getN();
	}
	
	public EInt getE() {
		return exp;
	}

	
	public boolean compatible(T t) {
		if(t.kind() != KindT.ARRAY) {
			return false;
		}
		else {
			TArray t2 = (TArray)t;
			return (this.getTArr().compatible(t2.getTArr()) && this.getN() == t2.getN());
		}
	}
	
	public int size() { //de momento para arrays estaticos
		return this.type.size()*this.getN();
	}

	@Override
	protected void setKind32() { //revisar accesos parciales y records
		this.kind32 = new ArrayList<>();
		ArrayList<Kind32> kind32d = this.type.getKind32();
		for(int i = 0; i < this.getN(); i++) {
			kind32.addAll(kind32d);
		}
	}

	public TArray daLaVuelta(EInt e) {
		TArray t = this;
		while(t.getTArr().kind() == KindT.ARRAY) {
			t = (TArray)t.getTArr();
		}
		return new TArray(new TArray(t.getTArr(), e), t.getE());
		
	}

}

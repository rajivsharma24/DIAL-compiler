package ast.instructions;
import java.util.ArrayList;
import ast.expressions.E;
import ast.Programa;
import ast.types.KindT;
import ast.types.T;

public class When {
	
	private E exp = null;
	private ArrayList<I> inst;
	
	public When(E exp, ArrayList<I> inst) {
		this.exp = exp;
		this.inst = inst;
	}
	
	public When(ArrayList<I> inst) {
		this.inst = inst;
	}
	
	@Override
	public String toString() {
		if(exp != null) {
			return "WHEN(" + exp + "," + inst + ")"; 
		}
		return "DEFAULT(" +  inst + ")"; 
	}
	
	public void bind() {
		if(exp != null) {
			exp.bind();
		}
		for(I i : inst) {
			i.bind();
		}
	}
	
	public void type(T t) {
		if(exp != null) {
			exp.type();
			if(!t.compatible(exp.getT())) {
				Programa.addError("Valor de When tiene tipo distinto al Case: " + this);
			}
		}
		for(I i : inst) {
			i.type();
		}
	}

	public int setDelta(int delta) {
		for(I i : inst) {
			delta = i.setDelta(delta);
		}
		return delta;
	}

	public ArrayList<I> getInst() {
		return inst;
	}
	
	public E getExp() {
		return exp;
	}
	
	
}


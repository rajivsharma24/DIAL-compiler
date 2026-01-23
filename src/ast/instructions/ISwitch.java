package ast.instructions;
import java.util.ArrayList;
import ast.Programa;
import ast.expressions.E;
import ast.Programa;
import ast.types.KindT;
import ast.types.T;

public class ISwitch extends IBloque{
	
    private E exp;
    ArrayList<When> whens;
    
    public ISwitch(E exp, ArrayList<When> whens) {
        this.exp = exp;
        this.whens = whens;
    }

    public String toString(){
        return "SWITCH(" + exp + "," + whens + ")";
    }
    
    @Override
    public void bind() {
    	exp.bind(); //PROVISIONAL: mismo bloque para todos los cases
    	Programa.getPila().abreBloque();
    	for(When w : whens) {
    		w.bind();
    	}
    	Programa.getPila().cierraBloque();
    	
    }
    
    public void type() { 
    	exp.type();
    	T t = exp.getT();
    	if(t.kind() != KindT.INT && t.kind() != KindT.ENUM) {
    		Programa.addError("Case requiere enumerado o entero: " + this);
    	}
    	for(When w : whens) {
    		w.type(t);
    	}
    }

	@Override
	public int setDelta(int delta) {
		int deltaLocal = delta;
		for(When w : whens) {
			deltaLocal = w.setDelta(deltaLocal);
		}
		return delta;
	}
	
	@Override
	public int getMemory() {
		int max = 0;
		int c = 0;
		for(When w : whens) {
			for(I i : w.getInst()) {
				if(i instanceof IDec idec) {
					c += idec.getMemory();
				}
				else if(i instanceof IReturn ir) {
					c += ir.getMemory();
				}
				else if(i instanceof IBloque ib){
					int bs = ib.getMemory();
					if(c + bs > max){
						max = c + bs;
					}
				}
			}
		}
		if(c > max) {
			max = c;
		}
		return max;
	}

	@Override
	public void generateCode() {
		Programa.getCode().println(" block");
		for(int i = 0; i < this.whens.size(); i++) {
			Programa.getCode().println(" block");
		}
		int posDef = this.whens.size();
		for(int i = 0; i < this.whens.size(); i++) {
			
			this.exp.generateCode();
			E expw = this.whens.get(i).getExp();
			if(expw != null) {
				expw.generateCode();
				Programa.getCode().println(" i32.eq");
				Programa.getCode().println(" br_if " + i);
			}
			else {
				posDef = i;
			}
		}
		
		Programa.getCode().println(" br " + posDef);
		
		for(int i = 0; i < this.whens.size(); i++) {
			Programa.getCode().println(" end");
			
			for(I inst : this.whens.get(i).getInst()) {
				if(inst instanceof IBreak) {
					Programa.getCode().println(" br " + (this.whens.size() - i - 1));
				}
				else {
					inst.generateCode();
				}
			}
		}
		Programa.getCode().println(" end");
	}
}
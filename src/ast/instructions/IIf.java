package ast.instructions;
import ast.Programa;
import java.util.ArrayList;
import ast.expressions.E;
import ast.types.KindT;

public class IIf extends IBloque{
	
    private E exp;
    ArrayList<I> inst;
    Ramas ramas;
    
    public IIf(E exp, ArrayList<I> inst, Ramas ramas) {
        this.exp = exp;
        this.inst = inst;
        this.ramas = ramas;
    }

    public String toString(){
        return "IF(" + exp + "," + inst + "," + ramas + ")";
    }
    
    @Override
    public void bind() {
    	exp.bind();
    	Programa.getPila().abreBloque();
    		for(I i : inst) {
    			i.bind();
    		}
    	Programa.getPila().cierraBloque();
    	ramas.bind();
    }
    
    public void type() {
    	exp.type();
    	if(exp.getT().kind() != KindT.BOOL){ //pensar en añadir kind a todos los T
            Programa.addError("Condicion en if no booleana: " + this);
        }
    	for(I i : inst) {
    		i.type();
    	}
    	ramas.type();
    }

	@Override
	public int setDelta(int delta) {
		int deltaLocal = delta;
		for(I i : inst) {
			deltaLocal = i.setDelta(deltaLocal);
		}
		ramas.setDelta(delta);
		return delta;
	}
	
	@Override
	public int getMemory() {
		int max = 0;
		int c = 0;
		for(I i : inst){
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
		if(c > max) {
			max = c;
		}
		
		max = ramas.getMemory(max);
		
		return max;
	}

	@Override
	public void generateCode() {
	    
	    exp.generateCode();
	    Programa.getCode().println(" if");  // if (cond)

	    for (I i : inst) {
	        i.generateCode();
	    }

	   
	    ramas.generateCode();

	    Programa.getCode().println(" end");
	}

}
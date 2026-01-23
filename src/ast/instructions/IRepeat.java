package ast.instructions;
import java.util.ArrayList;
import ast.expressions.E;
import ast.Programa;
import ast.types.KindT;

public class IRepeat extends IBloque{
	
    private E exp;
    ArrayList<I> inst;
    
    public IRepeat(E exp, ArrayList<I> inst) {
        this.exp = exp;
        this.inst = inst;
    }

    public String toString(){
        return "REPEAT(" + exp + "," + inst + ")";
    }
    
    @Override
    public void bind() {
    	exp.bind(); //no se si dentro o fuera
    	Programa.getPila().abreBloque();
		for(I i : inst) {
			i.bind();
		}
		Programa.getPila().cierraBloque();
    }
    
    public void type() {
    	exp.type();
    	if(exp.getT().kind() != KindT.BOOL){ //pensar en añadir kind a todos los T
            Programa.addError("Condicion en if no booleana: " + this);
        }
    	for(I i : inst) {
    		i.type();
    	}
    }

	@Override
	public int setDelta(int delta) {
		int deltaLocal = delta;
		for(I i : inst) {
			deltaLocal = i.setDelta(deltaLocal);
		}
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
		return max;
	}
	
	@Override
	public void generateCode() {

	    Programa.getCode().println(" block");
	    Programa.getCode().println(" loop");

	    for (I i : inst) {
	        i.generateCode(); // cuerpo del repeat
	    }

	    exp.generateCode(); // condición del until
	    Programa.getCode().println(" i32.eqz");
	    Programa.getCode().println(" br_if 0"); // repetir si condición aún no se cumple

	    Programa.getCode().println(" end");
	    Programa.getCode().println(" end");
	}

}
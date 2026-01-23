package ast.instructions;
import ast.Programa;

import java.util.ArrayList;
import ast.expressions.E;
import ast.types.KindT;

public class IFor extends IBloque{
	
    private E exp;
    private IAsig asig;
    private IDec dec;
    ArrayList<I> inst;
    
    public IFor(IDec dec, E exp, IAsig asig, ArrayList<I> inst) {
        this.asig = asig;
        this.exp = exp;
        this.dec = dec;
        this.inst = inst;
    }
    
    @Override
    public String toString(){
        return "FOR(" + dec + "," + exp + "," + asig + "," + inst + ")";
    }
    
    @Override
    public void bind() {
    	Programa.getPila().abreBloque();
    	dec.bind();
        exp.bind();
        asig.bind(); //no se si abrir bloque
        for(I i : inst) {
        	i.bind();
        }
        Programa.getPila().cierraBloque();
    }
    
    public void type() {
    	dec.type();
    	exp.type();
    	asig.type();
    	if(exp.getT().kind() != KindT.BOOL){ //pensar en añadir kind a todos los T
            Programa.addError("Condicion en bucle for no booleana: " + this);
        }
    	for(I i : inst) {
    		i.type();
    	}
    	
    }

	@Override
	public int setDelta(int delta) {
		int deltaLocal = this.dec.setDelta(delta);
		for(I i : inst) {
			deltaLocal = i.setDelta(deltaLocal);
		}
		return delta;
	}

	@Override
	public int getMemory() {
		int max = dec.getMemory();
		int c = dec.getMemory();
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
	    if (dec != null) {
	        dec.generateCode();
	    }

	    Programa.getCode().println(" block");     
	    Programa.getCode().println(" loop");     

	    if (exp != null) {
	        exp.generateCode();
	        Programa.getCode().println(" i32.eqz");
	        Programa.getCode().println(" br_if 1");  // salir si condición es falsa
	    }

	    // Cuerpo del bucle
	    for (I i : inst) {
	        i.generateCode();
	    }

	    // Asignación final (asig)
	    if (asig != null) {
	        asig.generateCode();
	    }

	    Programa.getCode().println(" br 0");    // volver al inicio del bucle

	    Programa.getCode().println(" end");      // fin del loop
	    Programa.getCode().println(" end");       // fin del block
	}


}
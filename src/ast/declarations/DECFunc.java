package ast.declarations;
import java.util.ArrayList;
import ast.Programa;
import ast.types.T;
import ast.instructions.I;
import ast.instructions.IBloque;
import ast.instructions.IDec;
import ast.instructions.IReturn;
import ast.types.KindT;

public class DECFunc extends DEC {
	
	protected T type;
	private String id;
	private ArrayList<Param> params;
	protected ArrayList<I> inst;
	protected boolean hasReturn = false;
	
	public DECFunc (T type, String id, ArrayList<Param> params, ArrayList<I> inst){
		this.type = type;
		this.id = id;
		this.params = params;
		this.inst = inst;
	}
	
	@Override
    public String toString() {
		if(export) {
			return "EXPORT_DECFunc(" + type + "," + id + "," + params + "," + inst + ")";
		}
    	return "DECFunc(" + type + "," + id + "," + params + "," + inst + ")";
    }

	@Override
	public void bind() {
		type.bind();
		Programa.getPila().insertaId(id,this);
		Programa.getPila().abreBloque();
		Programa.setFuncActual(this);
		for(Param p : params) {
			p.bind();
		}
		for(I i : inst) {
			i.bind();
		}
		Programa.setFuncActual(null);
		Programa.getPila().cierraBloque();
	}
	
	@Override
	public void bindM() {
		if(export) {
			Programa.getPila().insertaId(id, this);
		}
	}
	
	@Override
	public void type() {
		type.type();
		this.tipoAST = type.getT();
		if(!hasReturn && type.kind() != KindT.NONE) {
			Programa.addError("Funcion que no es NONE sin retorno en " + id);
		}
		for(Param p : params) {
			p.type();
		}
		for(I i : inst) {
			i.type();
		}
		
    }
	
	public ArrayList<Param> getParams() {
		return params;
	}
	
	public void setReturn() {
		this.hasReturn = true;
	}

	@Override
	public int setDelta(int delta) {
		int deltaLocal = 0;
		for(Param p : this.params) {
			deltaLocal = p.setDelta(deltaLocal);
		}
		for(I i : this.inst) {
			deltaLocal = i.setDelta(deltaLocal);
		}
		return delta;
	}
	
	public void generateCode() {
        Programa.getCode().println(" ;;generating code for " + this);
        Programa.getCode().println("(func $" + this.id);
        int size = maxMemory() + 4;
        if (this.tipoAST.kind() != KindT.NONE){
        	size += 4;
        	Programa.getCode().print(" (result i32)");
        }
        Programa.getCode().println("");
        
        Programa.preludioF(size);

        for (I i : inst){
        	i.generateCode(); 
        	if (i instanceof IReturn){ 
        		break;
        	}
        	Programa.getCode().println("");
        }
        if(!hasReturn) { 
        	Programa.finF();
        }
        Programa.getCode().println(")");
        Programa.getCode().println(" ;;end generating code for " + this);
    }

	private int maxMemory() {

		int c = 0;
		int max = 0;

		for(Param p : params) {
			c += p.getT().size();
		}
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
	
}
package ast.instructions;
import ast.types.Kind32;
import ast.types.KindT;
import ast.types.T;
import ast.Programa;
import ast.expressions.E;
import ast.expressions.consts.EArray;
import ast.expressions.consts.ERecord;

public class IDec extends I{
	
	private T type;
	private String id;
    private E exp = null;
	private int delta;
	private boolean global = false;

    public IDec(T type, String id, E exp){ 
    	this.type = type;
        this.id = id;
        this.exp = exp;
    }

    public IDec(T type, String id){ 
    	this.type = type;
        this.id = id;
    }

    @Override
    public String toString() {
        if (exp == null){
            return "DEC(" + type + "," + id + ")";
        }
        return "DEC(" + type + "," + id + "," + exp + ")";
    }
    
    @Override
    public void bind() {
    	type.bind();
    	Programa.getPila().insertaId(id, this);
    	if(exp != null) {
    		exp.bind();
    	}
    	
    }
   
    public void bindM() {
    	Programa.getPila().insertaId(id, this);
    }
    
    public void type() {
    	type.type();
    	if(exp != null) {
    		exp.type();
    		if(!type.getT().compatible(exp.getT())) { //cambiar por compatibles
    			Programa.addError("Tipos (" + type.getT() + "," + exp.getT() + ") no compatibles en declaración: " + this);
    		}
    	}
    	this.tipoAST = type.getT();
    }

	@Override
	public int setDelta(int delta) {
		this.delta = delta;
		return delta + this.tipoAST.size();
	}

	public int getMemory() {
		return this.tipoAST.size();
	}
	
	public void calculateAddress() {
		Programa.getCode().println(" i32.const " + delta);
		if(!global) {
			Programa.getCode().println(" local.get $localsStart"); 
			Programa.getCode().println(" i32.add");
		}
		
	}

	@Override
	public void generateCode() { //queda pendiente read y reales
		Programa.getCode().println(" ;;generating code for declaration " + this);
		if(exp != null) {
			if(this.tipoAST.kind() == KindT.ARRAY || this.tipoAST.kind() == KindT.RECORD) {
				this.calculateAddress();
				if(exp instanceof EArray || exp instanceof ERecord) {
					Programa.getCode().println(" call $dup_top");
            		exp.generateCode();
            	}
            	else {
            		for(int j = 0; j < exp.getT().size()/4; j++) {
            			Programa.getCode().println(" call $dup_top");
            			Programa.getCode().println(" i32.const " + 4*j);
            			Programa.getCode().println(" i32.add");
            			exp.generateCode();
            			Programa.getCode().println(" i32.const " + 4*j);
            			Programa.getCode().println(" i32.add");
            			if(exp.getT().getKind32().get(j) == Kind32.F32) {
	            			Programa.getCode().println(" f32.load");
	            			Programa.getCode().println(" f32.store");
            			}
            			else {
	            			Programa.getCode().println(" i32.load");
	            			Programa.getCode().println(" i32.store");
            			}
            		}
            	}
            	for(int j = 0; j < exp.getT().size()/4; j++) {
            		if(this.tipoAST.getKind32().get(j) == Kind32.F32 && exp.getT().getKind32().get(j) == Kind32.I32) {
            			Programa.getCode().println(" call $dup_top");
            			Programa.getCode().println(" i32.const " + 4*j);
            			Programa.getCode().println(" i32.add");
            			Programa.getCode().println(" call $dup_top");
            			Programa.getCode().println(" i32.load");
            			Programa.getCode().println(" f32.convert_i32_s");
            			Programa.getCode().println(" f32.store");
            		}
            	}
            	Programa.getCode().println(" drop");
			}
			else {
				this.calculateAddress();
				exp.generateCode();
				if(this.tipoAST.kind() == KindT.REAL) {
					if(exp.getT().kind() == KindT.INT) {
						Programa.getCode().println(" f32.convert_i32_s");
					}
					Programa.getCode().println(" f32.store");
				}
				else {
					Programa.getCode().println(" i32.store");
				}
			}
		}
		Programa.getCode().println(" ;;end generating code for declaration " + this);
		
	}

	public void setGlobal() {
		this.global  = true;
		
	}  
    
    
}
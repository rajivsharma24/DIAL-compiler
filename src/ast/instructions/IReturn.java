package ast.instructions;
import ast.expressions.E;
import ast.expressions.consts.EArray;
import ast.expressions.consts.ERecord;
import ast.Programa;
import ast.types.Kind32;
import ast.types.KindT;
import ast.declarations.DECFunc;

public class IReturn extends I{
	
	private E exp;

    public IReturn (E exp){
        this.exp = exp;
    }
    public IReturn (){
        this.exp = null;
    }
    
    public String toString(){
        if(exp == null){
            return "RETURN";
        }
        return "RETURN(" + exp + ")";
    }
    
    public void bind() {
    	if(exp != null) {
    		exp.bind();
    	}
    	DECFunc decf = Programa.getFuncActual();
    	if(decf == null) {
    		Programa.addError("Return fuera de función.");
    	}
    	decf.setReturn();
    	this.nodo = decf;
    	
    }
    
    public void type() {
    	if(exp != null) {
    		exp.type();
    		if(!nodo.getT().compatible(exp.getT())) {
    			Programa.addError("Return debe ser de mismo tipo que funcion " + this);
    		}
    	}
    	else {
    		if(nodo.getT().kind() != KindT.NONE) {
    			Programa.addError("Return vacio en función que no es NONE " + this.nodo);
    		}
    	}
    	this.tipoAST = nodo.getT();
    }
    
    @Override
	public void generateCode() {
        if(exp != null){
        	Programa.getCode().println(" global.get $SP");
            Programa.getCode().println(" i32.const 4");
            Programa.getCode().println(" i32.sub");
            
            if(exp.getT().kind() == KindT.ARRAY || exp.getT().kind() == KindT.RECORD) {
            	if(exp instanceof EArray || exp instanceof ERecord) { //guardamos temporal
            		Programa.getCode().println("call $dup_top");
            		Programa.getCode().println(" i32.const " + (exp.getT().size()));
                    Programa.getCode().println(" i32.sub");
            		exp.generateCode();
            		Programa.getCode().println("call $dup_top");
            		Programa.getCode().println(" i32.const " + (exp.getT().size()));
                    Programa.getCode().println(" i32.sub");
                    Programa.getCode().println(" i32.store");
                    Programa.getCode().println(" global.get $SP");
                    Programa.getCode().println(" i32.const 4");
                    Programa.getCode().println(" i32.sub");
                    Programa.getCode().println(" i32.const " + (exp.getT().size()));
                    Programa.getCode().println(" i32.sub");
            	}
            	else {
            		Programa.getCode().println(" call $dup_top");
            		exp.generateCode();
            		Programa.getCode().println(" i32.store");
            		exp.generateCode();
            		
            	}
            	
            	for(int i = 0; i < exp.getT().size()/4; i++) {
            		if(this.tipoAST.getKind32().get(i) == Kind32.F32 && this.exp.getT().getKind32().get(i) == Kind32.I32) {
            			Programa.getCode().println(" call $dup_top");
            			Programa.getCode().println(" i32.const " + 4*i);
            			Programa.getCode().println(" i32.add");
            			Programa.getCode().println(" call $dup_top");
            			Programa.getCode().println(" i32.load");
            			Programa.getCode().println(" f32.convert_i32_s");
            			Programa.getCode().println(" f32.store");
            		}
            	}
            	
            }
            else if(this.exp.getT().kind() == KindT.INT && this.tipoAST.kind() == KindT.REAL) {
            	exp.generateCode();
            	Programa.getCode().println(" f32.convert_i32_s");
            	Programa.getCode().println( "f32.store");
            }
            else if(this.exp.getT().kind() == KindT.REAL) {
            	exp.generateCode();
            	Programa.getCode().println( "f32.store");
            }
            else {
            	exp.generateCode();
            	Programa.getCode().println( "i32.store ;;hola");
            }
            
            Programa.getCode().println(" global.get $SP");
            Programa.getCode().println(" i32.const 4");
            Programa.getCode().println(" i32.sub");
        }
        Programa.finF();
        Programa.getCode().println(" return");
    }
	public int getMemory() { //En el caso en el que devolvemos una constante array o record, es como una declaracion
		if(exp instanceof EArray || exp instanceof ERecord) {
			return exp.getT().size();
		}
		return 0;
	}
	
}
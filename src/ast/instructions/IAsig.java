package ast.instructions;
import ast.expressions.E;
import ast.expressions.EBin;
import ast.expressions.ETer;
import ast.expressions.KindEBin;
import ast.expressions.consts.EArray;
import ast.expressions.consts.ERecord;
import ast.types.T;
import ast.expressions.desigs.D;
import ast.expressions.desigs.DCall;
import ast.Programa;
import ast.types.Kind32;
import ast.types.KindT;

public class IAsig extends I{

    private D desig;
    private E exp;
    private KindAsig kindAsig;
    
    public IAsig(D desig, E exp, KindAsig kindAsig){
        this.desig = desig;
        this.exp = exp;
        this.kindAsig = kindAsig;
    }

    @Override
    public String toString() {
        return kindAsig + "(" + desig + "," + exp + ")";
    }
    
    @Override
    public void bind() {
        desig.bind();
        exp.bind();
    }
    
    public void type() {
    	desig.type();
    	exp.type();
    	T td = desig.getT();
    	T te = exp.getT();
    	
    	/*
    	if(!td.equals(te)) {
    		if(td.kind() == KindT.REAL && te.kind() == KindT.INT) {
    			this.tipoAST = td;
    		}
    		else {
    			Programa.addError("Tipos no compatibles en asignacion: " + this);
    		}
    	}
    	else {
    		if(kindAsig != KindAsig.ASIG) {
    			if(td.kind() == KindT.REAL || td.kind() == KindT.INT) {
    				this.tipoAST = td;
    			}
    			else {
    				Programa.addError("Tipo no compatible con operacion aritmética en: " + this);
    			}
    		}
			else {
				this.tipoAST = td;
			}
    	}*/
    	if(desig instanceof DCall) { //revisar
    		Programa.addError("Designador no válido en asignación :" + this);
    	}
    	else if(!td.compatible(te)) {
    		System.out.println(td);
    		System.out.println(te);
    		Programa.addError("Tipos no compatibles en asignacion: " + this);
    	}
    	else {
    		if(kindAsig != KindAsig.ASIG) {
    			if(td.kind() == KindT.REAL || td.kind() == KindT.INT) {
    				this.tipoAST = td;
    				if(td.kind() == KindT.REAL && kindAsig == KindAsig.MOASIG) {
    					Programa.addError("Tipo no compatible con operacion aritmética en: " + this);
    				}
    			}
    			else{
    				Programa.addError("Tipo no compatible con operacion aritmética en: " + this);
    			}
    		}
    		else {
    			this.tipoAST = td;
    		}
    	}
    	
    }

	@Override
	public void generateCode() {
		switch(this.kindAsig) {
			case ASIG:
				if(desig.getT().kind() == KindT.ARRAY || desig.getT().kind() == KindT.RECORD) {
					desig.getDir();
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
	            		if(desig.getT().getKind32().get(j) == Kind32.F32 && exp.getT().getKind32().get(j) == Kind32.I32) {
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
					desig.getDir();
					exp.generateCode();
					if(desig.getT().kind() == KindT.REAL) {
						if(exp.getT().kind() == KindT.INT) {
							Programa.getCode().println(" f32.convert_i32_s");
						}
						Programa.getCode().println(" f32.store");
					}
					else {
						Programa.getCode().println(" i32.store");
					}
				}
				break;
			case DASIG:
				desig.getDir();
				desig.generateCode();
				exp.generateCode();
				if(desig.getT().kind() == KindT.REAL) {
					if(exp.getT().kind() == KindT.INT) {
						Programa.getCode().println(" f32.convert_i32_s");
					}
					Programa.getCode().println(" f32.div");
					Programa.getCode().println(" f32.store");
				}
				else {
					Programa.getCode().println(" i32.div_s");
					Programa.getCode().println(" i32.store");
				}
				break;
			case MASIG:
				desig.getDir();
				desig.generateCode();
				exp.generateCode();
				if(desig.getT().kind() == KindT.REAL) {
					if(exp.getT().kind() == KindT.INT) {
						Programa.getCode().println(" f32.convert_i32_s");
					}
					Programa.getCode().println(" f32.mul");
					Programa.getCode().println(" f32.store");
				}
				else {
					Programa.getCode().println(" i32.mul");
					Programa.getCode().println(" i32.store");
				}
				break;
			case MOASIG:
				desig.getDir();
				desig.generateCode();
				exp.generateCode();
				Programa.getCode().println(" i32.rem_s");
				Programa.getCode().println(" i32.store");
				break;
			case RASIG:
				desig.getDir();
				desig.generateCode();
				exp.generateCode();
				if(desig.getT().kind() == KindT.REAL) {
					if(exp.getT().kind() == KindT.INT) {
						Programa.getCode().println(" f32.convert_i32_s");
					}
					Programa.getCode().println(" f32.sub");
					Programa.getCode().println(" f32.store");
				}
				else {
					Programa.getCode().println(" i32.sub");
					Programa.getCode().println(" i32.store");
				}
				break;
			case SASIG:
				desig.getDir();
				desig.generateCode();
				exp.generateCode();
				if(desig.getT().kind() == KindT.REAL) {
					if(exp.getT().kind() == KindT.INT) {
						Programa.getCode().println(" f32.convert_i32_s");
					}
					Programa.getCode().println(" f32.add");
					Programa.getCode().println(" f32.store");
				}
				else {
					Programa.getCode().println(" i32.add");
					Programa.getCode().println(" i32.store");
				}
				break;
		
		}
		
	}

} 
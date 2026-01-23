package ast.expressions.desigs;
import java.util.ArrayList;
import ast.Programa;
import ast.expressions.E;
import ast.expressions.consts.EArray;
import ast.expressions.consts.ERecord;
import ast.types.Kind32;
import ast.types.KindT;
import ast.types.T;
import ast.declarations.DECFunc;
import ast.declarations.Param;
import ast.ASTNode;

public class DCall extends D {

    private String id;
    private ArrayList<E> params;
    
    public DCall(String id, ArrayList<E> params){
        this.id = id;
        this.params = params;
    }

    @Override
    public String toString(){
    	if (params.isEmpty()) return id + "()";
        String res = id + "(";
        for(int i = 0; i < params.size()-1; i++) {
        	res = res + params.get(i) + ",";
        }
        res = res + params.get(params.size()-1) + ")";
        return res;
    }
    
    @Override
    public void bind() {
    	ASTNode node = Programa.getPila().buscaId(id);
        if(node == null){
            Programa.addError("Funcion " + id + " no encontrada.");
        }
        else{
        	this.nodo = node;
            for(E e : params){
                e.bind();
            }
        }
    }
    
    public void type() {
        if (nodo instanceof DECFunc decf) {
            ArrayList<Param> paramsDec = decf.getParams();
            if (params.size() != paramsDec.size()) {
                Programa.addError("Se esperaban " + paramsDec.size() + " argumentos en " + this);
            } else {
                for (int i = 0; i < params.size(); i++) {
                    params.get(i).type();
                    if (!paramsDec.get(i).getT().compatible(params.get(i).getT())) {
                        Programa.addError("Tipos no encajan. Se esperaba " + 
                            params.get(i).getT() + " en lugar de " + paramsDec.get(i).getT() + " en posicion " + i + " de " + this);
                    }
                    if (paramsDec.get(i).isRef() && !(params.get(i) instanceof D)) {
                        Programa.addError("En posicion " + i + " debe ser un designador porque se pasa por referencia.");
                    }
                }
            }

            this.tipoAST = decf.getT();
        } else {
            Programa.addError("Identificador: " + id + " no es una funcion.");
           // this.tipoAST = new TBasico(KindT.NONE); // Asignas algo para que no explote luego
        }
    }

	@Override
	public void getDir() { //solo se llama si es array o record.
		generateCode();
	}

	@Override
	public void generateCode() {
		
		DECFunc decf = (DECFunc) this.nodo;
		ArrayList<Param> paramsDec = decf.getParams();
		
		for(int i = 0; i < this.params.size(); i++) {
			E exp = this.params.get(i);
			Param p = paramsDec.get(i);
			if(!p.isRef()) {
				Programa.getCode().println(" global.get $SP");
				Programa.getCode().println(" i32.const " + 4); 
				Programa.getCode().println(" i32.add");
				Programa.getCode().println(" i32.const " + p.getDelta());
				Programa.getCode().println(" i32.add");
				if(p.getT().kind() == KindT.ARRAY || p.getT().kind() == KindT.RECORD) {
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
	            		if(p.getT().getKind32().get(j) == Kind32.F32 && exp.getT().getKind32().get(j) == Kind32.I32) {
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
					exp.generateCode();
					if(p.getT().kind() == KindT.REAL) {
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
			else {
				Programa.getCode().println(" global.get $SP");
				Programa.getCode().println(" i32.const " + 4); 
				Programa.getCode().println(" i32.add");
				Programa.getCode().println(" i32.const " + p.getDelta());
				Programa.getCode().println(" i32.add");
				D d = (D) exp;
				d.getDir();
				Programa.getCode().println(" i32.store");
			}
		}
		Programa.getCode().println(" call $" + this.id);  
		if(this.tipoAST.kind() != KindT.NONE) {
			if(this.tipoAST.kind() == KindT.REAL) {
				Programa.getCode().println(" f32.load"); 
			}
			else {
				Programa.getCode().println(" i32.load"); 
			}
		}
	}
	

    /*
    public void type() {
    	
		if(!(nodo instanceof DECFunc decf)) {
			Programa.addError("Identificador: " + id + " no es una funcion.");
		}
		ArrayList<Param> paramsDec = decf.getParams();
		if(params.size() != paramsDec.size()) {
			Programa.addError("Se esperaban " + paramsDec.size() + " argumentos en " + this);
		}
		else {
			for(int i = 0; i < params.size(); i++) {
				params.get(i).type();
				if(!paramsDec.get(i).getT().compatible(params.get(i).getT())){
	                Programa.addError("Tipos no encajan. Se esperaba " + 
				              params.get(i).getT() + " en lugar de " + paramsDec.get(i).getT() + " en posicion " 
				              + i + " de " + this);
	            }
				if(paramsDec.get(i).isRef() && !(params.get(i) instanceof D)){
	                Programa.addError("En posicion " + i + " debe ser un designador porque se pasa por referencia.");
	            }
				//futuro: si añadimos constantes cuidado referencia
			}
		}
		
		this.tipoAST = decf.getT();
    }
    */
}
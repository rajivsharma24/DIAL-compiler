package ast.expressions.consts;
import java.util.ArrayList;
import ast.Programa;
import ast.expressions.E;
import ast.types.TArray;
import ast.types.Kind32;
import ast.types.KindT;
import ast.types.T;


public class EArray extends E {

	public ArrayList<E> exps;
	
	public EArray (ArrayList<E> exps) {
		this.exps = exps;
	}
  
	public String toString(){
        return exps.toString();
    }
	
	@Override
	public void bind() {
        for(E e : exps){
            e.bind();
        }
    }
	
	public void type() {
		if(exps.isEmpty()) {
			Programa.addError("Constante array vacia");
		}
		else {
			exps.get(0).type();
			T type = exps.get(0).getT();
			for(int i = 1; i < exps.size(); i++) {
				exps.get(i).type();
				if(exps.get(i).getT().compatible(type)) {
					type = exps.get(i).getT(); //mi nuevo "maximo"
				}
				else if(!type.compatible(exps.get(i).getT())) {
					Programa.addError("Posiciones con distinto tipo en: " + this);
				}
			}
			if(type.kind() == KindT.ARRAY) {
				this.tipoAST = ((TArray)type).daLaVuelta(new EInt(exps.size() + ""));
			}
			else {
				this.tipoAST = new TArray(type, new EInt(exps.size() + ""));
			}
			
		}
	}
	
	@Override
	public void generateCode() {
		TArray tar = (TArray) tipoAST;
		Programa.getCode().println(" ;;generating code for earray" + this);
		for(int i = 0; i < exps.size(); i++) {
			Programa.getCode().println(" call $dup_top");
			Programa.getCode().println(" i32.const " + i*exps.get(i).getT().size());
			Programa.getCode().println(" i32.add");
			if(exps.get(i).getT().kind() == KindT.ARRAY || exps.get(i).getT().kind() == KindT.RECORD) {
				if(exps.get(i) instanceof EArray || exps.get(i) instanceof ERecord) {
					exps.get(i).generateCode();
            	}
            	else {
            		for(int j = 0; j < exps.get(i).getT().size()/4; j++) {
            			Programa.getCode().println(" call $dup_top");
            			Programa.getCode().println(" i32.const " + j*4);
            			Programa.getCode().println(" i32.add");
            			exps.get(i).generateCode();
            			Programa.getCode().println(" i32.const " + j*4);
            			Programa.getCode().println(" i32.add");
            			if(exps.get(i).getT().getKind32().get(j) == Kind32.F32) {
            				Programa.getCode().println(" f32.load");
                			Programa.getCode().println(" f32.store");
            			}
            			else {
            				Programa.getCode().println(" i32.load");
                			Programa.getCode().println(" i32.store");
            			}
            		}
            		Programa.getCode().println(" drop");
            	}
				for(int j = 0; j < exps.get(i).getT().size()/4; j++) {
            		if(tar.getTArr().getKind32().get(i) == Kind32.F32 && exps.get(i).getT().getKind32().get(i) == Kind32.I32) {
            			Programa.getCode().println(" call $dup_top");
            			Programa.getCode().println(" i32.const " + 4*j);
            			Programa.getCode().println(" i32.add");
            			Programa.getCode().println(" call $dup_top");
            			Programa.getCode().println(" i32.load");
            			Programa.getCode().println(" f32.convert_i32_s");
            			Programa.getCode().println(" f32.store");
            		}
            	}
			}
			else {
				exps.get(i).generateCode();
				if(tar.getTArr().kind() == KindT.REAL) {
					if(exps.get(i).getT().kind() == KindT.INT){
						Programa.getCode().println(" f32.convert_i32_s");
					}
					Programa.getCode().println(" f32.store");
				}
				else {
					Programa.getCode().println(" i32.store");
				}
			}
			
		}
		Programa.getCode().println(" drop");
		Programa.getCode().println(" ;;end generating code for earray" + this);
	}
}
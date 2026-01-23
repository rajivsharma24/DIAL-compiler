package ast.expressions.consts;
import java.util.ArrayList;
import ast.types.TRecord;
import ast.Programa;
import ast.expressions.E;
import ast.types.Kind32;
import ast.types.KindT;
import ast.types.T;

public class ERecord extends E {

	public ArrayList<E> exps;
	
	public ERecord (ArrayList<E> exps) {
		this.exps = exps;
	}
  
	public String toString(){
        String res = "{";
        for(int i = 0; i < exps.size()-1; i++) {
        	res = res + exps.get(i) + ",";
        }
        res = res + exps.get(exps.size()-1) + "}";
        return res;
    }
	
	@Override
	public void bind() {
        for(E e : exps){
            e.bind();
        }
    }
	
	public void type() {
		ArrayList<T> tipos = new ArrayList<T>();
		for(int i = 0; i < exps.size(); i++) {
			exps.get(i).type();
			tipos.add(exps.get(i).getT());
		}
		this.tipoAST = new TRecord(tipos);
		
	}
	
	@Override
	public void generateCode() {
		TRecord tr = (TRecord) tipoAST;
		Programa.getCode().println(" ;;generating code for erecord" + this);
		for(int i = 0; i < exps.size(); i++) {
			Programa.getCode().println(" call $dup_top");
			Programa.getCode().println(" i32.const " + tr.getDeltaP(i));
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
            		if(tr.getTipos().get(i).getKind32().get(i) == Kind32.F32 && exps.get(i).getT().getKind32().get(i) == Kind32.I32) {
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
				if(tr.getTipos().get(i).kind() == KindT.REAL) {
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
		Programa.getCode().println(" ;;end generating code for erecord" + this);
	}
}
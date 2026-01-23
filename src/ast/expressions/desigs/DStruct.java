package ast.expressions.desigs;
import ast.Programa;
import ast.types.T;
import ast.types.TRecord;
import ast.types.Kind32;
import ast.types.KindT;
import ast.declarations.Param;
import java.util.ArrayList;

public class DStruct extends D {

    private D desig;
    private String id;

    public DStruct(D desig, String id){
        this.desig = desig;
        this.id = id;
    }

    @Override
    public String toString(){
        return desig  + "." + id;
    }
    
    @Override
    public void bind() {
    	desig.bind();
    	//this.nodo = desig.getN(); //se puede hacer con tipos
    }
    
    public void type() {
    	desig.type();
    	T t = desig.getT();
    	if(t.kind() != KindT.RECORD) {
    		Programa.addError("Designador: " + desig + " no es un registro.");
    	}
    	ArrayList<Param> campos = ((TRecord) t).getCampos();
    	int i = 0;
    	for(; i < campos.size(); i++) {
    		if(campos.get(i).getId().equals(id)) {
    			break;
    		}
    	}
    	if(i == campos.size()) {
    		Programa.addError("No existe campo " + id + " en registro " + desig);
    	}
    	this.tipoAST = campos.get(i).getT();

    }

	@Override
	public void getDir() {
		desig.getDir();
		TRecord tr = (TRecord) desig.getT();
		Programa.getCode().println(" i32.const " + tr.getDelta(id));  
		Programa.getCode().println(" i32.add");  
	}

	@Override
	public void generateCode() {
		if(this.tipoAST.kind() == KindT.ARRAY || this.tipoAST.kind() == KindT.RECORD) {
			this.getDir();
		}
		else {
			this.getDir();
			if(this.tipoAST.kind() == KindT.REAL) {
				Programa.getCode().println(" f32.load");
			}
			else {
				Programa.getCode().println(" i32.load");
			}
		}
	}
}
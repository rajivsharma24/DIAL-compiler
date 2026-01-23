package ast.expressions.desigs;
import ast.Programa;
import ast.types.Kind32;
import ast.types.KindT;
import ast.types.T;
import ast.types.TPointer;

public class DPointer extends D {

    private D desig;

    public DPointer(D desig){
        this.desig = desig;
    }

    @Override
    public String toString(){
        return "@" + desig;
    }
    
    @Override
    public void bind() {
    	desig.bind();
    }
    
    public void type() {
    	desig.type();
    	if(desig.getT().kind() != KindT.POINTER) {
    		Programa.addError("Designador: " + desig + " no es un puntero.");
    	}
    	this.tipoAST = ((TPointer) desig.getT()).getTPointer();
    	System.out.println(tipoAST);
    }

	@Override
	public void getDir() {
		desig.generateCode();
		Programa.getCode().println(";; aqqui");
	}

	@Override
	public void generateCode() { //revisar
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
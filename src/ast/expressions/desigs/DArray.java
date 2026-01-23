package ast.expressions.desigs;
import ast.expressions.E;
import ast.Programa;
import ast.types.Kind32;
import ast.types.KindT;
import ast.types.T;
import ast.types.TArray;

public class DArray extends D{
    private D desig;
    private E exp;

    public DArray(D desig, E exp){
        this.desig = desig;
        this.exp = exp;   
    }

    @Override
    public String toString(){
        return desig + "[" + exp + "]";
    }
    
    @Override
    public void bind() {
		desig.bind();
		exp.bind();
    }
    
    public void type() {
		desig.type();
		exp.type();
		//System.out.println(this + ",, " + desig.getT());
		if(desig.getT().kind() != KindT.ARRAY) {
			Programa.addError("Designador: " + desig + " no es un array.");
		}
		if(exp.getT().kind() != KindT.INT) { //de momento no miramos si es constante
			Programa.addError("Posicion en acceso: " + this + " no es un entero.");
		}
		//System.out.println((TArray) desig.getT());
		this.tipoAST = ((TArray) desig.getT()).getTArr();

    }

	@Override						
	public void generateCode() { //comprobar indices
		TArray tar = (TArray) desig.getT();
		if(tar.getTArr().kind() == KindT.ARRAY || tar.getTArr().kind() == KindT.RECORD) {
			this.getDir();
		}
		else {
			comprobarIndices();
			desig.getDir();
			exp.generateCode();
			Programa.getCode().println(" i32.const 4");
			Programa.getCode().println(" i32.mul");
			Programa.getCode().println(" i32.add");
			if(tar.getTArr().kind() == KindT.REAL) {
				Programa.getCode().println(" f32.load");
			}
			else {
				Programa.getCode().println(" i32.load");
				}
			}
		}

	@Override
	public void getDir() {
		comprobarIndices();
		desig.getDir();
		exp.generateCode();
		Programa.getCode().println(" i32.const " +  this.tipoAST.size());
		Programa.getCode().println(" i32.mul");
		Programa.getCode().println(" i32.add");
	}

	private void comprobarIndices() {
		exp.generateCode(); // >= 0
		Programa.getCode().println(" i32.const 0");
		Programa.getCode().println(" i32.lt_s");
		Programa.getCode().println(" if");
		Programa.getCode().println(" i32.const 2");
		Programa.getCode().println(" call $exception");
		Programa.getCode().println(" end"); 
		
		exp.generateCode(); // < size
		TArray tar = (TArray) desig.getT();
		Programa.getCode().println(" i32.const " + (tar.getN()-1));
		Programa.getCode().println(" i32.gt_s");
		Programa.getCode().println(" if");
		Programa.getCode().println(" i32.const 2");
		Programa.getCode().println(" call $exception");
		Programa.getCode().println(" end");
	}
}
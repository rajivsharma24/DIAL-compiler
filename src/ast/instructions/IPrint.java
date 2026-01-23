package ast.instructions;
import ast.Programa;
import ast.expressions.E;
import ast.types.KindT;

public class IPrint extends I{

    private E exp;

    public IPrint(E exp){
        this.exp = exp;
    }

    public String toString(){
        return "PRINT(" + exp + ")";
    }
    
    @Override
    public void bind() {
    	exp.bind();
    }
    
    @Override
    public void type() {
    	exp.type();
    	if(exp.getT().kind() == KindT.RECORD || exp.getT().kind() == KindT.ARRAY) {
    		Programa.addError("Print compatible solo con tipos basicos");
    	}
    }

	@Override
	public void generateCode() {
		exp.generateCode();
		if(exp.getT().kind() == KindT.INT || exp.getT().kind() == KindT.BOOL) {
			Programa.getCode().println("call $printi32");
		}
		else {
			Programa.getCode().println("call $printf32");
		}
		
	}
}
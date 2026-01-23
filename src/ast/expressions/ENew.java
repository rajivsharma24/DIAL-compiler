package ast.expressions;
import ast.Programa;
import ast.types.T;
import ast.types.TPointer;

public class ENew extends E{

	private T type;
	
    public ENew(T type){
    	this.type = type;
    }
    
    @Override
    public String toString(){
        return "new " + type; 
    }
    
    @Override
    public void bind() {
		type.bind();
    }
    
    public void type() {
    	type.type();
    	this.tipoAST = new TPointer(type.getT()); //con la idea de eliminar alias de tipo revisar
    }

	@Override
	public void generateCode() {
		Programa.getCode().println(" i32.const " + this.type.getT().size());
        Programa.getCode().println(" call $reserveHeap ");
        Programa.getCode().println(" global.get $NP");
	}
	
}
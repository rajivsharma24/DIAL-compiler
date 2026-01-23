package ast.instructions;
import ast.expressions.desigs.D;
import ast.Programa;
import ast.types.KindT;

public class IDel extends I{
	
	private D desig;

    public IDel (D desig){
        this.desig = desig;
    }
    
    public String toString(){
        return "DELETE(" + desig + ")";
    }
    
    @Override
    public void bind() {
        desig.bind();
    }
    
    public void type() {
    	desig.type();
    	if(desig.getT().kind() != KindT.POINTER) {
    		Programa.addError("Delete solo se puede hacer sobre punteros: "+ this);
    	}
    }

	@Override
	public void generateCode() {
		// TODO Auto-generated method stub
		//ni idea
	}
}
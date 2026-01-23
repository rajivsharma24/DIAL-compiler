package ast.instructions;
import ast.Programa;
import ast.expressions.desigs.DCall;
import ast.types.KindT;

public class ICall extends I{

    private DCall call;

    public ICall(DCall call){
        this.call = call;
    }

    public String toString(){
        return "ICall(" +  call + ")";
    }
    
    @Override
    public void bind() {
        call.bind();
    }
    
    public void type() {
    	call.type();
    }

	@Override
	public void generateCode() {
		call.generateCode();
		if(call.getT().kind() != KindT.NONE) {
			Programa.getCode().println(" drop");
		}
		
	}

}
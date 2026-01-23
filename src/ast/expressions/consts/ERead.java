package ast.expressions.consts;
import ast.types.TBasico;
import ast.Programa;
import ast.expressions.E;
import ast.types.KindT;
import ast.types.T;

public class ERead extends E{

	private boolean isf32;
	
    public ERead(boolean isf32){
    	this.isf32 = isf32;
    }


    public String toString(){
        return "read()"; 
    }
    
    public void type() {
    	this.tipoAST = new TBasico(isf32 ? KindT.REAL : KindT.INT);
    	System.out.println(this.tipoAST);
    }



	@Override
	public void generateCode() { //arreglar expresiones
		if(this.tipoAST.kind() == KindT.REAL) {
			Programa.getCode().println(" call $readf32");
		}
		else {
			Programa.getCode().println(" call $readi32");
		}
    }

}
package ast.expressions.consts;
import ast.Programa;
import ast.expressions.E;
import ast.types.KindT;
import ast.types.T;
import ast.types.TBasico;

public class EReal extends E {

	private String r;
	
	public EReal (String r) {
		this.r = r;
	}
  
	public String toString(){
		return "Real" + "(" + r + ")";  
	}
	
	public void type() {
		this.tipoAST = new TBasico(KindT.REAL);
	}
	
	@Override
	public void generateCode() {
		Programa.getCode().println(" f32.const " + r); //no se si va bien asi
	}

}
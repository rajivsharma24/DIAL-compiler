package ast.expressions.consts;
import ast.Programa;
import ast.expressions.E;
import ast.types.KindT;
import ast.types.T;
import ast.types.TBasico;

public class EInt extends E {

	private String n;
	
	public EInt (String n) {
		this.n = n;
	}
  
	public String toString(){
		return "Int" + "(" + n + ")";  
	}
	
	public void type() {
		this.tipoAST = new TBasico(KindT.INT);
  }
	
	public int getN() {
		return Integer.parseInt(n);
	}
	
	public void generateCode() {
		Programa.getCode().println(" i32.const " + n);
	}
	
}
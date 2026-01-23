package ast.expressions.consts;
import ast.Programa;
import ast.expressions.E;
import ast.types.KindT;
import ast.types.T;
import ast.types.TBasico;

public class ENull extends E {

  public ENull () {
  }

  public String toString(){
	  return "null";  
  }
  
  public void type() {
		this.tipoAST = new TBasico(KindT.NULL);
  }
  
  @Override
  public void generateCode() {
	  Programa.getCode().println(" i32.const 0"); //provisional preguntar valor de nulo
  }
  
}
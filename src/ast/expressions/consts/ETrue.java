package ast.expressions.consts;
import ast.Programa;
import ast.expressions.E;
import ast.types.KindT;
import ast.types.T;
import ast.types.TBasico;

public class ETrue extends E {

  public ETrue () {
  }

  public String toString(){
	  return "true";  
  }
  
  public void type() {
		this.tipoAST = new TBasico(KindT.BOOL);
  }
  
  @Override
  public void generateCode() {
	  Programa.getCode().println(" i32.const 1");
  }
  
}
package ast.expressions;
import ast.types.Kind32;
import ast.types.KindT;
import ast.types.T;
import ast.Programa;
import ast.expressions.consts.EArray;
import ast.expressions.consts.ERecord;

public class ETer extends E {
  private E opnd1;
  private E opnd2;
  private E opnd3;

  public ETer(E opnd1, E opnd2, E opnd3) {
    this.opnd1 = opnd1;
    this.opnd2 = opnd2;
    this.opnd3 = opnd3;
  }

  public String toString(){
	  return "CONDTER(" + opnd1 + "," + opnd2 + "," + opnd3 + ")";  
  }
  
  @Override
  public void bind() {
		opnd1.bind();
		opnd2.bind();
		opnd3.bind();
  }
  
  public void type() {
	  opnd1.type();
	  opnd2.type();
	  opnd3.type();

	  if(opnd1.getT().kind() != KindT.BOOL) {
		  Programa.addError("La primera expresion no es booleana en " + this);
	  }
	  if(opnd2.getT().kind() == KindT.ARRAY || opnd2.getT().kind() == KindT.RECORD) {
		  Programa.addError("No permitimos arrays ni records en expresion ternaria " + this);
	  }
	  if(opnd2.getT().compatible(opnd3.getT())) {
		  this.tipoAST = opnd2.getT();
	  }
	  else if(opnd3.getT().compatible(opnd2.getT())) {
		  this.tipoAST = opnd3.getT();
	  }
	  else {
		  Programa.addError("Tipos de operandos no coinciden en " + this);
	  }
	  /*if(!(opnd2.getT().equals(opnd3.getT()))) {
		  if((opnd2.getT().kind() == KindT.INT && opnd3.getT().kind() == KindT.REAL) ||
	              (opnd2.getT().kind() == KindT.REAL && opnd3.getT().kind() == KindT.INT)) {
			  this.tipoAST = (opnd2.getT().kind() == KindT.REAL) ? opnd2.getT() : opnd3.getT();
		  }
		  else {
			  Programa.addError("Tipos de operandos no coinciden en " + this);
		  }
	  }else {
		  this.tipoAST = opnd2.getT();
	  }
	*/	  
        
  }
	@Override
	public void generateCode() {
		this.opnd1.generateCode();
		Programa.getCode().println(" if (result" + ((this.tipoAST.kind() == KindT.REAL) ? " f32)" : " i32)"));
		if(this.opnd2.getT().kind() == KindT.INT && this.tipoAST.kind() == KindT.REAL) {
        	opnd2.generateCode();
        	Programa.getCode().println(" f32.convert_i32_s");
        }
        else {
        	opnd2.generateCode();
        }
		Programa.getCode().println(" else");
		if (this.opnd3.getT().kind() == KindT.INT && this.tipoAST.kind() == KindT.REAL) {
			opnd3.generateCode();
			Programa.getCode().println(" f32.convert_i32_s");
		} else {
			opnd3.generateCode();
		}
		Programa.getCode().println(" end");
		
	}
	
}
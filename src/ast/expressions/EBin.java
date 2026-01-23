package ast.expressions;
import ast.Programa;
import ast.types.KindT;
import ast.types.T;
import ast.types.KindT;
import ast.types.TBasico;

public class EBin extends E {
  private E opnd1;
  private E opnd2;
  private KindEBin kindEBin;

  public EBin(E opnd1, E opnd2,  KindEBin kindEBin) {
    this.opnd1 = opnd1;
    this.opnd2 = opnd2;
    this.kindEBin = kindEBin;
  }

  public String toString(){
	  return kindEBin + "(" + opnd1 + "," + opnd2 + ")";  
  }
  
  @Override
  public void bind() {
		opnd1.bind();
		opnd2.bind();
  }
  
  public void type() {
	    opnd1.type();
	    opnd2.type();

	    T t1 = opnd1.getT();
	    T t2 = opnd2.getT();

	    switch (kindEBin) {
	        case MAS, MENOS, POR, DIV -> {
	            if ((t1.kind() != KindT.INT && t1.kind() != KindT.REAL) ||
	                (t2.kind() != KindT.INT && t2.kind() != KindT.REAL)) {
	                Programa.addError("Operadores aritméticos solo permiten INT o REAL en " + this);
	            }

	            if (t1.kind() == KindT.REAL || t2.kind() == KindT.REAL) {
	                this.tipoAST = (t1.kind() == KindT.REAL) ? t1 : t2;
	            } else {
	                this.tipoAST = t1; // Ambos son INT
	            }
	        }

	        case MAYOR, MENOR, MAYORIG, MENORIG -> {
	            if ((t1.kind() != KindT.INT && t1.kind() != KindT.REAL) ||
	                (t2.kind() != KindT.INT && t2.kind() != KindT.REAL)) {
	                Programa.addError("Operadores relacionales solo permiten INT o REAL en " + this);
	            }
	            this.tipoAST = new TBasico(KindT.BOOL);
	        }

	        case IGUAL, DESIGUAL -> {
	        	if /*(!(
	                    (t1.kind() == t2.kind()) ||
	                    (t1.kind() == KindTBasico.INT && b2.kind() == KindTBasico.REAL) ||
	                    (b1.kind() == KindTBasico.REAL && b2.kind() == KindTBasico.INT)
	                ))*/(!(t1.compatible(t2) || t2.compatible(t1)))  {
	                Programa.addError("Comparación entre tipos no compatibles en " + this);
	            }
	            this.tipoAST = new TBasico(KindT.BOOL);
	        }

	        case AND, OR -> {
	            if (t1.kind() != KindT.BOOL || t2.kind() != KindT.BOOL) {
	                Programa.addError("Operadores lógicos solo permiten BOOL en " + this);
	            }
	            this.tipoAST = new TBasico(KindT.BOOL);
	        }
	        case MOD -> {
	        	if (t1.kind() != KindT.INT || t2.kind() != KindT.INT) {
	                Programa.addError("Operador módulo solo permite INT en " + this);
	            }
	        	this.tipoAST = new TBasico(KindT.INT);
	        }
	    }
	}

  @Override
  public void generateCode() { 
	  this.opnd1.generateCode();
	  this.opnd2.generateCode();
	  
	  switch(this.kindEBin) {
		  case AND:
			  Programa.getCode().println(" i32.and");
			  break;
		  case DESIGUAL:
			  Programa.getCode().println(" " + this.convierteOperandos() + "32.ne");
			  break;
		  case DIV:
			  if(this.convierteOperandos() == 'i') {
				  Programa.getCode().println(" i32.div_s");
			  }
			  else {
				  Programa.getCode().println(" f32.div");
			  }
			  break;
		  case IGUAL:
			  Programa.getCode().println(" " + this.convierteOperandos() + "32.eq");
			  break;
		  case MAS:
			  Programa.getCode().println(" " + this.convierteOperandos() + "32.add");
			  break;
		  case MAYOR:
			  if(this.convierteOperandos() == 'i') {
				  Programa.getCode().println(" i32.gt_s");
			  }
			  else {
				  Programa.getCode().println(" f32.gt");
			  }
			  break;
		  case MAYORIG:
			  if(this.convierteOperandos() == 'i') {
				  Programa.getCode().println(" i32.ge_s");
			  }
			  else {
				  Programa.getCode().println(" f32.ge");
			  }
			  break;
		  case MENOR:
			  if(this.convierteOperandos() == 'i') {
				  Programa.getCode().println(" i32.lt_s");
			  }
			  else {
				  Programa.getCode().println(" f32.lt");
			  }
			  break;
		  case MENORIG:
			  if(this.convierteOperandos() == 'i') {
				  Programa.getCode().println(" i32.le_s");
			  }
			  else {
				  Programa.getCode().println(" f32.le");
			  }
			  break;
		  case MENOS:
			  Programa.getCode().println(" " + this.convierteOperandos() + "32.sub");
			  break;
		  case MOD:
			  Programa.getCode().println(" i32.rem_s");
			  break;
		  case OR:
			  Programa.getCode().println(" i32.or");
			  break;
		  case POR:
			  Programa.getCode().println(" " + this.convierteOperandos() + "32.mul");
			  break;

	  }

  }
  
  private char convierteOperandos() {
	  if(this.opnd1.getT().kind() == KindT.REAL) {
		  if(this.opnd2.getT().kind() == KindT.INT) {
			  Programa.getCode().println(" f32.convert_i32_s");
		  }
		  return 'f';
	  }
	  else if(this.opnd2.getT().kind() == KindT.REAL) {
		  Programa.getCode().println(" call $swap_i32f32");
		  Programa.getCode().println(" f32.convert_i32_s");
		  Programa.getCode().println(" call $swap_f32");
		  return 'f';
	  }
	  else {
		  return 'i';
	  }
  }
 

}
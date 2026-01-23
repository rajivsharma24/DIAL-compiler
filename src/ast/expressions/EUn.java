package ast.expressions;
import ast.Programa;
import ast.types.KindT;
import ast.types.T;
import ast.types.KindT;

public class EUn extends E {
   private E opnd;
   private KindEUn kindEun;

   public EUn(E opnd,  KindEUn kindEun) {
	   this.opnd = opnd;
	   this.kindEun = kindEun;
   }
 
   public String toString(){
	   return kindEun + "(" + opnd + ")";
   }
   
   @Override
   public void bind() {
		opnd.bind();
   }
   
   public void type() {
	    opnd.type();

	    T t = opnd.getT();

	    switch (kindEun) {
	        case NOT -> {
	            if (t.kind() != KindT.BOOL) {
	                Programa.addError("El operador NOT requiere un BOOL en " + this);
	            }
	            this.tipoAST = t;
	        }
	        case MAS, MENOS -> {
	            if (t.kind() != KindT.INT && t.kind() != KindT.REAL) {
	                Programa.addError("El operador unario " + kindEun + " requiere INT o REAL en " + this);
	            }
	            this.tipoAST = t;
	        }
	    }
	}

   @Override
   public void generateCode() {
	   
	   switch(this.kindEun) {
		   case MAS: 
			   this.opnd.generateCode();
			   break;
		   case MENOS:
			   char c = this.tipoAST.kind() == KindT.REAL ? 'f' : 'i';
			   Programa.getCode().println(" " + c + "32.const 0");
			   this.opnd.generateCode();
			   Programa.getCode().println(" " + c + "32.sub");
			   break;
		   case NOT:
			   this.opnd.generateCode();
			   Programa.getCode().println(" i32.eqz");
			   break;	   
	   }

   }
  
}
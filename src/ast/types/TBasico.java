package ast.types;

import java.util.ArrayList;

public class TBasico extends T {
	
   private KindT kind;
   
   public TBasico(KindT kind) {
     this.kind = kind;
   }
   
   public KindT kind() {return this.kind;}
   public String toString() {return kind.toString();}
   
   public void type() {
	   this.tipoAST = this;
   }
   
   public boolean compatible(T t) {

	   return this.kind() == t.kind() || this.kind() == KindT.REAL && t.kind() == KindT.INT;

   }
   
   public int size() { 
	   if(kind == KindT.NONE) {
		   return 0;
	   }
	   return 4;
   }

	@Override
	protected void setKind32() {
		this.kind32 = new ArrayList<>();
		if(this.kind == KindT.REAL) {
			kind32.add(Kind32.F32);
		}
		else if(this.kind != KindT.NONE) {
			kind32.add(Kind32.I32);
		}
		
	}
}

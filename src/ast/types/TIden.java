package ast.types;
import ast.Programa;
import ast.declarations.DECAlias;
import ast.declarations.DECEnum;
import ast.declarations.DECReg;
import ast.ASTNode;

public class TIden extends T{
	
	private String iden;
	
	public TIden(String iden) {
		this.iden = iden;
	}
	
	public KindT kind() {
		return KindT.IDEN;
	}
	   
	public String toString() {return iden;}
	
	public void bind(){
		ASTNode node = Programa.getPila().buscaId(iden);
		if(node == null){
			Programa.addError("Tipo: " + iden + " not found");
		}
		this.nodo = node;
	}
	
	public void type() {
		if(nodo instanceof DECAlias || nodo instanceof DECEnum || nodo instanceof DECReg) {
			this.tipoAST = nodo.getT(); //deca.type() ya lo habran hecho
		}
		else {
			Programa.addError("El identificador '" + iden + "' no es un tipo válido.");
		}
    }

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected void setKind32() {
		// TODO Auto-generated method stub
		
	}
	
	//se supone que no voy a usar compatible con esto porque manejo tipos reducidos
}
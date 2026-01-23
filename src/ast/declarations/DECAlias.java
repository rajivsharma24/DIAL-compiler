package ast.declarations;
import ast.types.T;
import ast.PilaTablaSimbolos;
import ast.Programa;

public class DECAlias extends DEC {
	
	private String id;
	private T type;
	
	public DECAlias (String id, T type){
		this.id = id;
		this.type = type;
	}
	
	@Override
    public String toString() {
		if(export) {
			return "EXPORT_DECAlias(" + id + "," + type + ")";
		}
    	return "DECAlias(" + id + "," + type + ")";
    }
	
	@Override
	public void bind() {
		Programa.getPila().insertaId(id, this);
		type.bind();
	}
	
	@Override
	public void type() {
		type.type();
		this.tipoAST = type.getT();
    }

	@Override
	public void bindM() {
		if(export) {
			Programa.getPila().insertaId(id, this);
		}
	}

}
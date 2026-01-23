package ast.declarations;
import java.util.ArrayList;
import ast.types.TEnum;
import ast.Programa;


public class DECEnum extends DEC {
	
	private String id;
	private ArrayList<String> valores;
	
	public DECEnum (String id, ArrayList<String> valores){
		this.id = id;
		this.valores = valores;
	}
	
	@Override
    public String toString() {
		if(export) {
			return "EXPORT_DECEnum(" + id + "," + valores + ")";
		}
    	return "DECEnum(" + id + "," + valores + ")";
    }
	
	public void bind() {
		Programa.getPila().insertaId(id,this);
		//Programa.getPila().abreBloque();
		for(String s : valores) {
			Programa.getPila().insertaId(s, this);
		}
		//Programa.getPila().cierraBloque();
		//no se si llevarlos en global o no.
	}
	
	@Override
	public void bindM() {
		if(export) {
			Programa.getPila().insertaId(id, this);
			for(String s : valores) {
				Programa.getPila().insertaId(s, this);
			}
		}
	}
	
	public void type() {
		this.tipoAST = new TEnum(this);
	}
	
	public boolean checkId(String s) {
		return id.equals(s);
	}
	
	public String getId() {
		return id;
	}

	public int getPos(String id) {
		for(int i = 0; i < valores.size(); i++) {
			if(id.equals(valores.get(i))) {
				return i;
			}
		}
		return -1;
	}

}
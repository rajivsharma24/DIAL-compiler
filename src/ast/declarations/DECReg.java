package ast.declarations;
import java.util.ArrayList;
import ast.Programa;
import ast.types.TRecord;

public class DECReg extends DEC {
	
	private String id;
	private ArrayList<Param> params;
	
	public DECReg (String id, ArrayList<Param> params){
		this.id = id;
		this.params = params;
	}
	
	@Override
    public String toString() {
		if(export) {
			return "EXPORT_DECReg(" + id + "," + params + ")";
		}
    	return "DECReg(" + id + "," + params + ")";
    }

	@Override
	public void bind() {
		Programa.getPila().abreBloque();
		for(Param p : params) {
			p.bind();
		}
		Programa.getPila().cierraBloque();
		Programa.getPila().insertaId(id, this);
	}
	
	@Override
	public void bindM() {
		if(export) {
			Programa.getPila().insertaId(id, this);
		}
	}
	
	public void type() {
		for(Param p : params) {
			p.type();
		}
		this.tipoAST = new TRecord(this); //muy provisional
    }
	
	public ArrayList<Param> getCampos() {
		return params;
	}
	
	public String getId() {
		return id;
	}

	@Override
	public int setDelta(int delta) {
		int deltaLocal = 0;
		for(Param p : params){
			deltaLocal = p.setDelta(deltaLocal);
		}
		return delta;
	}

	public int getDelta(String id) {
		for(Param p : params) {
			if(p.getId().equals(id)) {
				return p.getDelta();
			}
		}
		return -1;
	}
}
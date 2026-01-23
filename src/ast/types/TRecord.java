package ast.types;
import java.util.ArrayList;
import ast.declarations.DECReg;
import ast.declarations.Param;

public class TRecord extends T { //pensar en añadir declaracion explicita de TRecord en lenguaje
	
	private String id = null;
	private ArrayList<T> params = null;
	private DECReg decr = null;
	private ArrayList<Integer> deltas = null;
	
	public TRecord (ArrayList<T> params){
		this.params = params;
		//this.tipoAST = this;
	}
	
	public TRecord (DECReg decr){
		this.decr = decr;
		this.id = decr.getId();
		ArrayList<Param> ps = decr.getCampos();
		params = new ArrayList<T>();
		for(Param p : ps) {
			params.add(p.getT());
		}
		//this.tipoAST = this;
	}
	
	public String toString() {
		return ("Record:"+id);
	}
	
	public ArrayList<Param> getCampos() {
		if(decr != null) {
			return decr.getCampos();
		}
		return null;
	}
	
	public ArrayList<T> getTipos() {
		return params;
	}
	
	public KindT kind() {
		return KindT.RECORD;
	}
	
	public String getId() {
		return id;
	}
	
	public boolean compatible(T t) { //de momento por nombre, aunque se puede hacer muy facil por estructura
		if(t.kind() != KindT.RECORD) {
			return false;
		}
		else {
			TRecord t2 = (TRecord)t;
			if(t2.getId() == null) {
				ArrayList<T> tipos = t2.getTipos();
				if(params.size() != tipos.size()) {
					return false;
				}
				for(int i = 0; i < params.size(); i++) {
					if(!params.get(i).compatible(tipos.get(i))) {
						return false;
					}
				}
				return true;
			}
			else {
				return this.id.equals(t2.getId());
			}
		}
	}
	
	public int size() {
		int total = 0;
		for(T t : params) {
			total += t.size();
		}
		return total;
	}

	public int getDelta(String id) {
		return this.decr.getDelta(id);
	}

	@Override
	protected void setKind32() {
		this.kind32 = new ArrayList<>();
		for(T t : params) {
			kind32.addAll(t.getKind32());
		}
	}

	public int getDeltaP(int i) {
		if(decr != null) {
			return decr.getCampos().get(i).getDelta();
		}
		setDeltas();
		return this.deltas.get(i);
		
	}

	private void setDeltas() {
		if(deltas == null) {
			deltas = new ArrayList<Integer>();
			for(int i = 0; i < this.getTipos().size(); i++) {
				if(i == 0) {
					deltas.add(0);
				}
				else {
					deltas.add(deltas.get(i-1) + this.getTipos().get(i-1).size());
				}
			}
		}
		
	}
}
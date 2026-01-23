package ast.types;
import java.util.ArrayList;
import ast.declarations.DECEnum;

public class TEnum extends T { //no muy seguro
	
	private DECEnum dece =  null;
	
	public TEnum (DECEnum dece){
		this.dece = dece;
		//this.tipoAST = this;
	}
	
	public String toString() {return "Enum:" + dece.getId();}
	
	public KindT kind() {
		return KindT.ENUM;
	}
	
	public DECEnum getDec() {
		return dece;
	}
	
	public boolean compatible(T t) {
		if(t.kind() !=  KindT.ENUM) {
			return false;
		}
		else {
			TEnum t2 = (TEnum)t;
			return dece.equals(t2.getDec());
		}
	}
	
	public int size() {
		return 4;
	}

	@Override
	protected void setKind32() {
		this.kind32 = new ArrayList<>();
		kind32.add(Kind32.I32);
	}
	
	
}
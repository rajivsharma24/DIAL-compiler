package ast.declarations;
import ast.instructions.IDec;

public class DECVar extends DEC {
	
	private IDec dec;
	
	public DECVar (IDec dec){
		this.dec = dec;
	}
	
	@Override
    public String toString() {
		if(export) {
			return "EXPORT_" + dec.toString();
		}
    	return dec.toString();
    }
	
	@Override
	public void bind() {
		dec.bind();
		dec.setGlobal();
	}
	
	@Override
	public void bindM() {
		if(export) {
			dec.bindM();
		}
	}
	
	public void type() {
		dec.type();
		//this.tipoAST = dec.getT();
    }

	@Override
	public int setDelta(int delta) {
		return dec.setDelta(delta);
	}

	public int getMemory() {
		return dec.getMemory();
	}

	@Override
	public void generateCode() {
		dec.generateCode();
		
	}
	

}
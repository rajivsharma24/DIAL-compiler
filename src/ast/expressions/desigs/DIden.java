package ast.expressions.desigs;
import ast.Programa;
import java.util.ArrayList;
import ast.declarations.DECEnum;
import ast.instructions.IDec;
import ast.types.Kind32;
import ast.types.KindT;
import ast.types.T;
import ast.ASTNode;
import ast.declarations.Param;

public class DIden extends D {
	
	private String id;

    public DIden(String id){
        this.id = id;
    }

    @Override
    public String toString(){
        return id;
    }
    
    @Override
    public void bind() {
    	ASTNode node = Programa.getPila().buscaId(id);
        if(node == null){
            Programa.addError("Id: " + id + " not found.");
        }
        this.nodo = node;
    }
    
    public void type() {
    	
		if(!(nodo instanceof IDec) && !(nodo instanceof DECEnum) && !(nodo instanceof Param)) {
			Programa.addError("Identificador: " + id + " no es un designador valido.");	
		}
		else if((nodo instanceof DECEnum dece) && dece.checkId(id)) { //no es un valor del enum
			Programa.addError("Identificador: " + id + " no es un designador valido.");	
		}
		this.tipoAST = nodo.getT();

    }

	@Override
	public void getDir() {
		if(nodo instanceof IDec idec) {
			idec.calculateAddress();
		}
		else if(nodo instanceof Param param) {
			param.calculateAddress();
		}
	}

	@Override
	public void generateCode() {
		if(nodo instanceof IDec || nodo instanceof Param) {
			if(this.tipoAST.kind() == KindT.ARRAY || this.tipoAST.kind() == KindT.RECORD) {
				this.getDir();
			}
			else {
				this.getDir();
				if(this.tipoAST.kind() == KindT.REAL) {
					Programa.getCode().println(" f32.load");
				}
				else {
					Programa.getCode().println(" i32.load");
				}
			}
		}
		else if(nodo instanceof DECEnum dece) {
			Programa.getCode().println(" i32.const " + dece.getPos(id));
		}
		
	}

	
}
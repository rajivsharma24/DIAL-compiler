package ast.declarations;
import java.util.ArrayList;
import ast.Programa;
import ast.instructions.I;
import ast.types.TBasico;
import ast.types.KindT;

public class DECMain extends DECFunc {
	
	public DECMain (ArrayList<I> inst){
		super(new TBasico(KindT.INT), "main", new ArrayList<Param>(), inst);
	}
	
	@Override
    public String toString() {
    	return "DECMain(" + inst + ")";
    }
	
	@Override
	public void bind() {
		Programa.setFuncActual(this);
		Programa.getPila().abreBloque();
		Programa.setFuncActual(this);
		for(I i : inst) {
			i.bind();
		}
		Programa.setFuncActual(null);
		Programa.getPila().cierraBloque();
	}
	
	public void type() {
		this.tipoAST = type;
		if(!hasReturn) {
			Programa.addError("Funcion MAIN sin retorno.");
		}
		for(I i : inst) {
			i.type();
		}
    }

}
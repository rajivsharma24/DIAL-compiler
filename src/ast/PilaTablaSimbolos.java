package ast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PilaTablaSimbolos {
	
	private List<HashMap<String,ASTNode>> pila; 
	
	public PilaTablaSimbolos() {
		pila = new ArrayList<HashMap<String,ASTNode>>();
	}
	
	public void abreBloque() {
		pila.add(new HashMap<>());
	}
	
	public void cierraBloque() {
		if (!pila.isEmpty()){ 
            pila.remove(pila.size()-1); 
        }
	}
	
	public void insertaId(String id, ASTNode nodo) {
		if (!pila.isEmpty()){ 
			HashMap<String, ASTNode> marcoActual = pila.get(pila.size()-1);
			if(marcoActual.containsKey(id)) {
				Programa.addError("Error:" + id + " already in use.");
			}else {
				marcoActual.put(id,nodo);
			}
			
        }
	}
	
	public ASTNode buscaId(String id) {
		for(int i = pila.size()-1; i>=0; i--) {
			if(pila.get(i).containsKey(id)) {
				return pila.get(i).get(id);
			}
		}
		return null;
	}
}

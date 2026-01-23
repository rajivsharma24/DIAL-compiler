package ast.instructions;
import ast.Programa;
import java.util.ArrayList;
import ast.expressions.E;
import ast.types.KindT;

record Tupla<T, U>(T first, U second) {}

public class Ramas {
	private ArrayList<Tupla<E, ArrayList<I>>> elifs = null;
	private ArrayList<I> _else = null;
	
	public Ramas(ArrayList<I> _else) {
		this._else = _else;
	}
	
	public Ramas() {
		
	}
	
	public void addElif(E exp, ArrayList<I> inst) {
		if(elifs == null) {
			elifs = new ArrayList<Tupla<E, ArrayList<I>>>();
		}
		elifs.add(new Tupla<E, ArrayList<I>>(exp,inst));
	}
	
	public void addElse(ArrayList<I> inst) {
		this._else = inst;
	}
	
	@Override
	public String toString() {
		String res = "";
		if(elifs != null) {
			for(Tupla<E,ArrayList<I>> t : elifs) {
				res = res + "ELIF(" + t.first() + "," + t.second() + ")";
			}
		}
		if(_else != null) {
			res = res + "ELSE(" + _else + ")";
		}
		return res;
	}
	
	public void bind() {
		if(elifs != null) {
			for(Tupla<E,ArrayList<I>> t : elifs) {
				t.first().bind();
				Programa.getPila().abreBloque();
				for(I i : t.second()) {
					i.bind();
				}
				Programa.getPila().cierraBloque();
			}
		}
		if(_else != null) {
			Programa.getPila().abreBloque();
			for(I i : _else) {
				i.bind();
			}
			Programa.getPila().cierraBloque();
		}
	}
	
	public void type() {
		if(elifs != null) {
			for(Tupla<E,ArrayList<I>> t : elifs) {
				t.first().type();
				if(t.first().getT().kind() != KindT.BOOL){ //pensar en añadir kind a todos los T
		            Programa.addError("Condicion en elif no booleana: " + this);
		        }
				for(I i : t.second()) {
					i.type();
				}
			}
		}
		if(_else != null) {
			for(I i : _else) {
				i.type();
			}
		}
	}

	public void setDelta(int delta) {
		
		if(elifs != null) {
			for(Tupla<E,ArrayList<I>> t : elifs) {
				int deltaLocalElif = delta;
				for(I i : t.second()) {
					deltaLocalElif = i.setDelta(deltaLocalElif);
				}
			}
		}
		if(_else != null) {
			int deltaLocal = delta;
			for(I i : _else) {
				deltaLocal = i.setDelta(deltaLocal);
			}
		}
		
	}
	
	public int getMemory(int max) {
		if(elifs != null) {
			for(Tupla<E,ArrayList<I>> t : elifs) {
				int c = 0;
				for(I i : t.second()){
					if(i instanceof IDec idec) {
						c += idec.getMemory();
					}
					else if(i instanceof IReturn ir) {
						c += ir.getMemory();
					}
					else if(i instanceof IBloque ib){
						int bs = ib.getMemory();
						if(c + bs > max){
							max = c + bs;
						}
					}
					
				}
				if(c > max) {
					max = c;
				}
				
			}
		}
		if(_else != null) {
			int c = 0;
			for(I i : _else){
				if(i instanceof IDec idec) {
					c += idec.getMemory();
				}
				else if(i instanceof IReturn ir) {
					c += ir.getMemory();
				}
				else if(i instanceof IBloque ib){
					int bs = ib.getMemory();
					if(c + bs > max){
						max = c + bs;
					}
				}
				
			}
			if(c > max) {
				max = c;
			}
		}
		return max;
	}
	
	public void generateCode() { //revisar
	    if (elifs != null && !elifs.isEmpty()) {
	        
	        Programa.getCode().println(" else");

	        for (int i = 0; i < elifs.size(); i++) {
	            Tupla<E, ArrayList<I>> t = elifs.get(i);

	           
	            t.first().generateCode();
	            Programa.getCode().println(" if");

	            
	            for (I instr : t.second()) {
	                instr.generateCode();
	            }

	            
	            if (i < elifs.size() - 1 || _else != null) {
	                Programa.getCode().println(" else");
	            } 
	        }

	        
	        if (_else != null) {
	            for (I i : _else) {
	                i.generateCode();
	            }
	        }

	        
	        for (int i = 0; i < elifs.size(); i++) {
	            Programa.getCode().println(" end");
	        }

	    } else if (_else != null) {
	        Programa.getCode().println(" else");
	        for (I i : _else) {
	            i.generateCode();
	        }
	    }
	}


}

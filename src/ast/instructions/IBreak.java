package ast.instructions;

import ast.Programa;

public class IBreak extends I{

    public IBreak (){
    }
    
    public String toString(){
        return "BREAK";
    }

	@Override
	public void generateCode() { //de momento solo para bucles
		Programa.getCode().println(" br 1");
	}
}
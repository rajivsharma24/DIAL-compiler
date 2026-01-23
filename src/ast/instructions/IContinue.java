package ast.instructions;

import ast.Programa;

public class IContinue extends I{

    public IContinue (){
    }
    
    public String toString(){
        return "CONTINUE";
    }
    
    @Override
	public void generateCode() {
		Programa.getCode().println(" br 0");
	}
}
package asint;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import alex.AnalizadorLexico;
import ast.Programa;

public class Main {
	public static void main(String[] args) throws Exception {
	     Reader input = new InputStreamReader(new FileInputStream(args[0]));
		 AnalizadorLexico alex = new AnalizadorLexico(input);
		 AnalizadorSintactico asint = new AnalizadorSintactico(alex);
		 //System.out.println(asint.parse().value);
		 try {
			 Programa p = (Programa) asint.parse().value;
			 if(!asint.hayErrores()) {
				 System.out.println("*********************TREE AST*********************");
				 System.out.println(p);
				 System.out.println("*********************BINDING********************");
				 p.setImports(args[0]);
				 p.bind(); // vinculacion
				 if(Programa.getErrores().isEmpty()) {
					 System.out.println("Correct binding!");
					 System.out.println("*********************TYPING********************");
					 p.type();
					 System.out.println("Correct typing!");

					 System.out.println("*********************DELTAS********************");
					 p.setDelta(0); 
					 System.out.println("Correct deltas!");

					 System.out.println("*********************GENERATING CODE********************");
					 p.generateCode();
				 }
				 else {
					 System.out.println("Errores en binding!");
					 for(String s : Programa.getErrores()) {
						 System.out.println(s);
					 }
				 }

			 }
		 }
		 catch(Exception e) {
			 System.out.println("Error while parsing..." + e.getMessage());
			 e.printStackTrace(System.out);
		 }
		 
		 

		 
		 
	}
}

package ast;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import ast.declarations.DEC;
import ast.declarations.DECFunc;
import ast.declarations.DECImport;
import ast.declarations.DECVar;

public class Programa extends ASTNode{ //comprobar no haya exports
	
	private ArrayList<DEC> decs;
	private static PilaTablaSimbolos pila;
	private static DECFunc fActual = null;
	private static ArrayList<String> errores;
	private static boolean enTipado = false; //muy muy provisional
	private static PrintWriter codigo;
	private String nombreCodigo = "codigo.wat";

    public Programa(ArrayList<DEC> decs) {
        this.decs = decs;
        pila = new PilaTablaSimbolos();
        errores = new ArrayList<String>();
    }

    @Override
    public NodeKind nodeKind() {
        return NodeKind.PROGRAM;
    }

    @Override
    public String toString() {
        return "PROGRAM(" + decs + ")";
    }
    
    public static PilaTablaSimbolos getPila() {
    	return pila;
    }
    
    public static void setFuncActual(DECFunc decf) {
    	Programa.fActual = decf;
    }
    
    public static DECFunc getFuncActual() {
    	return fActual;
    }
    
    public void bind() {
    	pila.abreBloque();
    	for(DEC d : decs) {
    		d.bind();
    	}
    	pila.cierraBloque();
    }
    
    public void type() {
    	Programa.enTipado = true;
    	for(DEC d : decs) {
    		d.type();
    	}
    	Programa.enTipado = false;
    }
    
    public static void addError(String e) {
    	errores.add(e);
    	if(enTipado) { //a mejorar
    		System.out.println("Error en Tipado");
    		System.out.println(e);
    		System.exit(0);
    	}
    }
    
    public static ArrayList<String> getErrores(){
    	return errores;
    }

	@Override
	public int setDelta(int delta) {
		int deltaLocal = delta;
		for(DEC dec : this.decs) {
			deltaLocal = dec.setDelta(deltaLocal);
		}
		return delta;
	}
	
	private int getMemory() {
		int m = 0;
		for(DEC dec : decs) {
			m += dec.getMemory();
		}
		return m;
	}
	
	private void generaCodigoGlobal() {
		for(DEC dec : decs) {
			if(dec instanceof DECVar) {
				dec.generateCode();
			}
			else if(dec instanceof DECImport di) {
				di.generaCodigoGlobal();
			}
		}
	}

	public static PrintWriter getCode() {
			return codigo;
	}

	@Override
	public void generateCode() {
		try {
			codigo = new PrintWriter(new FileWriter("code/" + nombreCodigo)); 
	        FileReader prelude = new FileReader("code/preludio.wat");
	        prelude.transferTo(codigo);
	        prelude.close();
	        
	        codigo.println("(func $preludio ");
	        int size = getMemory(); 
	        preludioF(size + 4);
	        generaCodigoGlobal();
	        codigo.println(" call $main");
	        codigo.println(" drop");
	        finF();
	        codigo.println(")");
	        
	        
	        generaCodigoResto();
	        
	        FileReader epilogo = new FileReader("code/epilogo.wat");
	        epilogo.transferTo(codigo);
	        epilogo.close();

            codigo.close();
	        
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void generaCodigoResto() {
		for(DEC dec : decs) {
			if(!(dec instanceof DECVar)) {
				dec.generateCode();
			}
		}
	}

	public static void preludioF(int size) {
        codigo.println(" (local $temp i32)");
        codigo.println(" (local $localsStart i32)");
        codigo.println(" i32.const " + size);
        codigo.println(" call $reserveStack");
        codigo.println(" local.set $temp");
        codigo.println(" global.get $MP");
        codigo.println(" local.get $temp");
        codigo.println(" i32.store");
        codigo.println(" global.get $MP");
        codigo.println(" i32.const 4");
        codigo.println(" i32.add");
        codigo.println(" local.set $localsStart");
    }
	
	public static void finF() {
		codigo.println(" call $freeStack");
	}
	
	public void setImports(String args) {
		File archivo = new File(args);
        File carpeta = archivo.getParentFile(); // Esto obtiene el directorio padre
		for(DEC d : decs) {
			if(d instanceof DECImport di) {
				di.setImport(carpeta);
				di.bindInterno();
			}
		}
	}
}

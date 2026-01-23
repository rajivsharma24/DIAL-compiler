package ast.declarations;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import alex.AnalizadorLexico;
import asint.AnalizadorSintactico;
import ast.Modulo;
import ast.Programa;

public class DECImport extends DEC {
	
	private String id;
	private Modulo m;
	
	public DECImport (String id){
		this.id = id;
	}
	
	@Override
    public String toString() {
		return "IMPORT(" + id + ")";
    }
	
	public void setImport(File carpeta) {
		Reader input;
		String ruta = carpeta == null ? "" : (carpeta.getAbsolutePath() + "/");
		try {
			input = new InputStreamReader(new FileInputStream(ruta + id + ".txt"));
			AnalizadorLexico alex = new AnalizadorLexico(input);
			AnalizadorSintactico asint = new AnalizadorSintactico(alex);
			if(asint.parse().value instanceof Modulo m) {
				this.m = m;
			}
		} catch (Exception e) {
			e.printStackTrace();
			Programa.addError("No se ha podido cargar el modulo : " + id);
		}
		
	}
	
	@Override
	public void bind() {
		m.bind();
	}

	public void bindInterno() {
		m.bindInterno();
	}

	@Override
	public void bindM() {
		
	}
	
	@Override
	public void type() {
		m.type();
	}
	
	@Override
	public int setDelta(int delta) {
		return m.setDelta(delta);
	}
	
	@Override
	public int getMemory() {
		return m.getMemory();
	}

	public void generaCodigoGlobal() {
		m.generaCodigoGlobal();
	}
	
	@Override
	public void generateCode() {
		m.generateCode();
	}

}
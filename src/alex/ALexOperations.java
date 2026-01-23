package alex;

import asint.ClaseLexica;

public class ALexOperations {
  private AnalizadorLexico alex;
  
  public ALexOperations(AnalizadorLexico alex) {
    this.alex = alex;
  }
  
  public UnidadLexica unidadFunc() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.FUNC); }
  public UnidadLexica unidadNone() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.NONE); }
  public UnidadLexica unidadReturn() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.RETURN); }
  public UnidadLexica unidadNew() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.NEW); }
  public UnidadLexica unidadDel() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.DEL); }
  public UnidadLexica unidadNull() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.NULL); }
  public UnidadLexica unidadRecord() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.RECORD); }
  public UnidadLexica unidadModule() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.MODULE); }
  public UnidadLexica unidadExport() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.EXPORT); }
  public UnidadLexica unidadImport() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.IMPORT); }
  public UnidadLexica unidadIf() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.IF); }
  public UnidadLexica unidadElif() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.ELIF); }
  public UnidadLexica unidadElse() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.ELSE); }
  public UnidadLexica unidadWhile() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.WHILE); }
  public UnidadLexica unidadFor() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.FOR); }
  public UnidadLexica unidadRepeat() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.REPEAT); }
  public UnidadLexica unidadInt() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.INT); }
  public UnidadLexica unidadReal() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.REAL); }
  public UnidadLexica unidadEnum() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.ENUM); }
  public UnidadLexica unidadBool() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.BOOL); }
  public UnidadLexica unidadTrue() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.TRUE); }
  public UnidadLexica unidadFalse() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.FALSE); }
  public UnidadLexica unidadUsing() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.USING); }
  public UnidadLexica unidadBreak() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.BREAK); }
  public UnidadLexica unidadContinue() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.CONTINUE); }
  public UnidadLexica unidadCase() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.CASE); }
  public UnidadLexica unidadWhen() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.WHEN); }
  public UnidadLexica unidadDefault() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.DEFAULT); }
  public UnidadLexica unidadPrint() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.PRINT); }
  public UnidadLexica unidadReadi() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.READI); }
  public UnidadLexica unidadReadf() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.READF); }
  public UnidadLexica unidadMain() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.MAIN); }
  public UnidadLexica unidadArray() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.ARRAY); }
  
  public UnidadLexica unidadId() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.IDEN, alex.lexema()); }
  public UnidadLexica unidadNumInt() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.NUMENT, alex.lexema()); }
  public UnidadLexica unidadNumReal() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.NUMREAL, alex.lexema()); }
 
  public UnidadLexica unidadSuma() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.MAS); }
  public UnidadLexica unidadResta() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.MENOS); }
  public UnidadLexica unidadMul() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.POR); }
  public UnidadLexica unidadDiv() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.DIV); }
  public UnidadLexica unidadMod() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.MOD); }
  
  public UnidadLexica unidadNot() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.NOT); }
  public UnidadLexica unidadAnd() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.AND); }
  public UnidadLexica unidadOr() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.OR); }
  
  public UnidadLexica unidadIgual() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.IGUAL); }
  public UnidadLexica unidadDesigual() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.DESIGUAL); }
  public UnidadLexica unidadMayor() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.MAYOR); }
  public UnidadLexica unidadMenor() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.MENOR); }
  public UnidadLexica unidadMayorIg() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.MAYORIG); }
  public UnidadLexica unidadMenorIg() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.MENORIG); }
  
  public UnidadLexica unidadPuntero() { return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.ACCESO); }
  public UnidadLexica unidadRef() { return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.REF); }
  //public UnidadLexica unidadFlecha() { return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.FLECHA); }
  public UnidadLexica unidadPunto() { return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.PUNTO); }
  public UnidadLexica unidadDosPuntos() { return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.DOSPUNTOS); }
  public UnidadLexica unidadInterrog() { return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.INTERROG); }
  
  public UnidadLexica unidadAsig() { return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.ASIG); }
  public UnidadLexica unidadSumaAsig() { return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.SASIG); }
  public UnidadLexica unidadRestaAsig() { return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.RASIG); }
  public UnidadLexica unidadMulAsig() { return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.MASIG); }
  public UnidadLexica unidadDivAsig() { return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.DASIG); }
  public UnidadLexica unidadModAsig() { return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.MOASIG); }

	  
  
  public UnidadLexica unidadPAp() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.PAP); }
  public UnidadLexica unidadPCierre() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.PCIERRE); }
  public UnidadLexica unidadCAp() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.CAP); }
  public UnidadLexica unidadCCierre() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.CCIERRE); }
  public UnidadLexica unidadLAp() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.LAP); }
  public UnidadLexica unidadLCierre() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.LCIERRE); }
  public UnidadLexica unidadPuntoComa() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.PCOMA); }
  public UnidadLexica unidadComa() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.COMA); }
  
  public UnidadLexica unidadEof() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.EOF); }
}
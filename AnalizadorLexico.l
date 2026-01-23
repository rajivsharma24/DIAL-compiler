package alex;

import errors.GestionErrores;

%%
%cup
%line
%column
%class AnalizadorLexico
%unicode
%public

%{
  private ALexOperations ops;
  private GestionErrores errores;
  public String lexema() {return yytext();}
  public int fila() {return yyline+1;}
  public int columna() {return yycolumn+1;}
  public void fijaGestionErrores(GestionErrores errores) {
   this.errores = errores;
  }
%}

%eofval{
  return ops.unidadEof();
%eofval}

%init{
  ops = new ALexOperations(this);
%init}


letra  = ([A-Z]|[a-z])
digitoPositivo = [1-9]
digito = ({digitoPositivo}|0)
parteEntera = {digitoPositivo}{digito}*
parteDecimal = {digito}*
numeroEntero = ({parteEntera}|0)
numeroReal = {numeroEntero}\.{parteDecimal}
separador = [ \t\r\b\n]

comentario = \#[^\n]* 
comillas = \"\"\"
comentarios = {comillas} ~{comillas} 

operadorSuma = \+
operadorResta = \-
operadorMul = \*
operadorDiv = \/
operadorMod = \%

operadorNot = \!
operadorAnd = \&\&
operadorOr = \|\|

operadorIgual = \=\=
operadorDesigual = \!\=
operadorMayor = \>
operadorMenor = \<
operadorMayorIg = {operadorMayor}{operadorAsig}
operadorMenorIg = {operadorMenor}{operadorAsig}

operadorPuntero = \@
operadorRef = \&
//operadorFlecha = \->
operadorPunto = \.
operadorDosPuntos = \:
operadorInterrog = \?

operadorAsig = \=
operadorSumaAsig = {operadorSuma}{operadorAsig}
operadorRestaAsig = {operadorResta}{operadorAsig}
operadorMulAsig = {operadorMul}{operadorAsig}
operadorDivAsig = {operadorDiv}{operadorAsig}
operadorModAsig = {operadorMod}{operadorAsig}

parentesisApertura = \(
parentesisCierre = \)
corcheteApertura = \[
corcheteCierre = \]
llaveApertura = \{
llaveCierre = \}
puntoComa = \;
coma = \,

func = func
none = none
return = return
new = new
del = del
null = null
record = record
module = module
export = export
import = import
if = if
elif = elif
else = else
while = while
for = for
repeat = repeat
int = int
real = real
enum = enum
bool = bool
true = true
false = false
using = using
break = break
continue = continue
case = case
when = when
default = default
print = print
readi = readi
readf = readf
array = array
main = main

identificador = ({letra}|_)({letra}|{digito}|_)*

%%
{separador}               {}
{comentario}              {}
{comentarios}             {}

{func}                    {return ops.unidadFunc();}
{none}                    {return ops.unidadNone();}
{return}                  {return ops.unidadReturn();}
{new}                     {return ops.unidadNew();}
{del}                     {return ops.unidadDel();}
{null}                    {return ops.unidadNull();}
{record}                  {return ops.unidadRecord();}
{module}                  {return ops.unidadModule();}
{export}                  {return ops.unidadExport();}
{import}                  {return ops.unidadImport();}
{if}                      {return ops.unidadIf();}
{elif}                    {return ops.unidadElif();}
{else}                    {return ops.unidadElse();}
{while}                   {return ops.unidadWhile();}
{for}                     {return ops.unidadFor();}
{repeat}                  {return ops.unidadRepeat();}
{int}                     {return ops.unidadInt();}
{real}                    {return ops.unidadReal();}
{enum}                    {return ops.unidadEnum();}
{bool}                    {return ops.unidadBool();}
{true}                    {return ops.unidadTrue();}
{false}                   {return ops.unidadFalse();}
{using}                   {return ops.unidadUsing();}
{break}                   {return ops.unidadBreak();}
{continue}                {return ops.unidadContinue();}
{case}                    {return ops.unidadCase();}
{when}                    {return ops.unidadWhen();}
{default}                 {return ops.unidadDefault();}
{print}                   {return ops.unidadPrint();}
{readi}                    {return ops.unidadReadi();}
{readf}                    {return ops.unidadReadf();}
{array}					  {return ops.unidadArray();}
{main}					  {return ops.unidadMain();}

{identificador}           {return ops.unidadId();}
{numeroEntero}            {return ops.unidadNumInt();}
{numeroReal}              {return ops.unidadNumReal();}

{operadorSuma}            {return ops.unidadSuma();}
{operadorResta}           {return ops.unidadResta();}
{operadorMul}             {return ops.unidadMul();}
{operadorDiv}             {return ops.unidadDiv();}
{operadorMod}             {return ops.unidadMod();}

{operadorNot}             {return ops.unidadNot();}
{operadorAnd}             {return ops.unidadAnd();}
{operadorOr}              {return ops.unidadOr();}

{operadorIgual}           {return ops.unidadIgual();}
{operadorDesigual}        {return ops.unidadDesigual();}
{operadorMayor}           {return ops.unidadMayor();}
{operadorMenor}           {return ops.unidadMenor();}
{operadorMayorIg}         {return ops.unidadMayorIg();}
{operadorMenorIg}         {return ops.unidadMenorIg();}

{operadorPuntero}         {return ops.unidadPuntero();}
{operadorRef}             {return ops.unidadRef();}
//{operadorFlecha}          {return ops.unidadFlecha();}
{operadorPunto}           {return ops.unidadPunto();}
{operadorDosPuntos}       {return ops.unidadDosPuntos();}
{operadorInterrog}        {return ops.unidadInterrog();}

{operadorAsig}            {return ops.unidadAsig();}
{operadorSumaAsig}        {return ops.unidadSumaAsig();}
{operadorRestaAsig}       {return ops.unidadRestaAsig();}
{operadorMulAsig}         {return ops.unidadMulAsig();}
{operadorDivAsig}         {return ops.unidadDivAsig();}
{operadorModAsig}         {return ops.unidadModAsig();}

{parentesisApertura}      {return ops.unidadPAp();}
{parentesisCierre}        {return ops.unidadPCierre();} 
{corcheteApertura}        {return ops.unidadCAp();}
{corcheteCierre}          {return ops.unidadCCierre();}
{llaveApertura}           {return ops.unidadLAp();}
{llaveCierre}             {return ops.unidadLCierre();}
{puntoComa}               {return ops.unidadPuntoComa();}
{coma}                    {return ops.unidadComa();}
[^]                       {errores.errorLexico(fila(),columna(),lexema());}  

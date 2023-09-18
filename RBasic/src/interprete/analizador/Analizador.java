package interprete.analizador;

import diccionario.TPReservada;
import interprete.Interprete;
import interprete.Parrafo;
import interprete.ejecutor.BEjecutor;
import interprete.ejecutor.bloque.BDe;
import interprete.ejecutor.bloque.BMientras;
import interprete.ejecutor.bloque.BSi;
import interprete.ejecutor.bloque.Bloque;
import interprete.ejecutor.proceso.PAccion;
import interprete.ejecutor.proceso.PImprimir;
import interprete.ejecutor.proceso.PAsignacion;
import interprete.ejecutor.proceso.PAsignacionFuncion;
import interprete.ejecutor.proceso.PEsperar;
import interprete.ejecutor.proceso.PLimpiar;
import interprete.ejecutor.proceso.PRecibe;
import interprete.ejecutor.proceso.PRetorna;
import interprete.ejecutor.proceso.Proceso;
import interprete.ejecutor.subproceso.SAccion;
import interprete.ejecutor.subproceso.SFuncion;
import interprete.ejecutor.subproceso.SubSegmento;
import interprete.ejecutor.variables.VBooleano;
import interprete.ejecutor.variables.VCaracter;
import interprete.ejecutor.variables.VDecimal;
import interprete.ejecutor.variables.VEntero;
import interprete.ejecutor.variables.VTexto;
import interprete.ejecutor.variables.Variable;
import java.util.ArrayList;
import utileria.Utileria;
import java.util.List;
import java.util.regex.Pattern;

public class Analizador {
    private final Bloque segmento;
    private final Interprete interprete;
    public Analizador (Interprete interprete, Bloque segmento){
        this.interprete = interprete;
        this.segmento=segmento;
    }
    private String getTermino(Bloque bloque){
        String cadena = "";
        if(bloque instanceof BSi)cadena = TPReservada.TSI.getRegex();
        if(bloque instanceof BDe)cadena = TPReservada.TDE.getRegex();
        if(bloque instanceof BMientras)cadena = TPReservada.TMIENTRAS.getRegex();
        return "^([\\s]*".concat(cadena).concat("[\\s]*)$");
    } 
    private boolean isFormatoBloque(String valor, Bloque bloque){
        if(bloque instanceof BSi)return isSi(valor);
        if(bloque instanceof BDe)return isDe(valor);
        if(bloque instanceof BMientras)return isMientras(valor);
        return false;
    }
    private TPReservada getTBloque(Bloque bloque){
        return bloque instanceof SAccion?TPReservada.TACCION:TPReservada.TOPERACION;
    }
    private int getMetodo(List<Parrafo> parrafos, int index, SubSegmento bloque){
        int i = 0;
        List<Parrafo> interno = new ArrayList<>();
        for (i = index + 1; i < parrafos.size(); i++) {
            if(parrafos.get(i).getValor().matches("^([\\s]*".concat(getTBloque(bloque).getRegex()).concat("[\\s]*)$"))){
                parrafos.get(i).setCorrecto();
                segmento.setMetodo(bloque);
                new Analizador(interprete, bloque).procesar(interno);
                break;
            }
            interno.add(parrafos.get(i));
        }
        return i;
    }
    private int getParrafos(List<Parrafo> parrafos, int index, Bloque bloque){
        int i = 0, apertura = 0;
        List<Parrafo> interno = new ArrayList<>();
        for (i = index + 1; i < parrafos.size(); i++) {
            if(isFormatoBloque(parrafos.get(i).getValor(), bloque))apertura++;
            if(parrafos.get(i).getValor().matches(getTermino(bloque))){
                if(apertura==0){
                    parrafos.get(index).setCorrecto();
                    parrafos.get(i).setCorrecto();
                    segmento.setAcciones(bloque);
                    new Analizador(interprete, bloque).procesar(interno);
                    break;
                }else apertura--;
            }
            interno.add(parrafos.get(i));
        }
        return i;
    }
    public boolean procesar(List<Parrafo> parrafos){
        for (int i = 0; i < parrafos.size(); i++) {
            Parrafo parrafo = parrafos.get(i);
            TSentencia tipo = analisisGeneral(parrafo.getValor());
            String aux = "";
            switch(tipo){
                case DECLARACION:
                    setVariable(parrafo);
                    break;
                case IMPRIMIR:
                    setImpresion(parrafo);
                    break;
                case SI:
                    aux = parrafo.getValor();
                    aux = aux.replaceFirst("^([\\s]*(".concat(TPReservada.SI.getRegex()).concat(")[\\s]+)"), "");
                    aux = aux.replaceAll("([\\s]+".concat(TPReservada.ENTONCES.getRegex()).concat("[\\s]*)$"), "");
                    if(isFormatoBooleano(segmento.cambiarVariables(aux))){
                        BSi si = new BSi();
                        si.setCondicion(aux);
                        si.setVariables(segmento.getVariables());
                        i = getParrafos(parrafos, i, si);
                    }else parrafo.setMensajeError("Argumentos incorrectos en la linea ".concat(String.valueOf(parrafo.getRenglon())));
                    break;
                case SINO:
                    aux = parrafo.getValor();
                    aux = aux.replaceFirst("^([\\s]*(".concat(TPReservada.SINO.getRegex()).concat(")[\\s]+)"), "");
                    aux = aux.replaceAll("([\\s]+".concat(TPReservada.ENTONCES.getRegex()).concat("[\\s]*)$"), "");
                    if(isFormatoBooleano(segmento.cambiarVariables(aux))){
                        BSi casteo = (BSi)segmento;
                        casteo.setCondicion(aux);
                        parrafo.setCorrecto();
                    }else parrafo.setMensajeError("Argumentos incorrectos en la linea ".concat(String.valueOf(parrafo.getRenglon())));
                    break;
                case NO:
                    BSi casteo = (BSi)segmento;
                    casteo.setCondicion(TPReservada.VERDADERO.toString());
                    casteo.setNo();
                    parrafo.setCorrecto();
                    break;
                case DE:
                    aux = parrafo.getValor();
                    aux = aux.replaceFirst("^([\\s]+)", "");
                    aux = aux.replaceAll("([\\s]+)$", "");
                    aux = aux.replaceAll("[\\s]+", " ");
                    aux = aux.replaceFirst("^([\\s]*".concat(TPReservada.DE.getRegex()).concat("[\\s]+)"), "");
                    aux = aux.replaceFirst("([\\s]+".concat(TPReservada.ENTONCES.getRegex()).concat("[\\s]*)$"), "");
                    aux = aux.replaceAll("([\\s]+".concat(TPReservada.HASTA.getRegex()).concat("[\\s]+)"), "\\$");
                    aux = aux.replaceAll("([\\s]+".concat(TPReservada.PASO.getRegex()).concat("[\\s]+)"), "\\$");
                    String[] tokens  = aux.split("\\$");
                    String[] subTokens = tokens[0].split("[\\s]+[=][\\s]+");
                    if(isFormatoNumerico(segmento.cambiarVariables(subTokens[1])) && !segmento.isNombreAsignado(subTokens[0]) && !Utileria.isPalabraReservada(subTokens[0])){
                        VEntero variable = new VEntero(subTokens[0]);
                        BDe bDe = new BDe(variable,subTokens[1]);
                        bDe.setVariables(segmento.getVariables());
                        if(isFormatoNumerico(bDe.cambiarVariables(tokens[1]))&&isFormatoNumerico(bDe.cambiarVariables(tokens[2]))){
                            bDe.setCondicion(tokens[1]);
                            bDe.setPaso(tokens[2]);
                            i = getParrafos(parrafos, i, bDe);
                        }else parrafo.setMensajeError("Argumentos en ".concat(TPReservada.HASTA.toString()).concat(" o/y ").concat(TPReservada.PASO.toString()).concat(" erroneos en la linea ").concat(String.valueOf(parrafo.getRenglon())));
                    }else parrafo.setMensajeError("Argumentos en la variable ".concat(subTokens[0]).concat(" erroneos en la linea ").concat(String.valueOf(parrafo.getRenglon())));
                    break;
                case MIENTRAS:
                    aux = parrafo.getValor();
                    aux = aux.replaceFirst("^([\\s]*".concat(TPReservada.MIENTRAS.getRegex()).concat("[\\s]+)"), "");
                    aux = aux.replaceAll("([\\s]+".concat(TPReservada.ENTONCES.getRegex()).concat("[\\s]*)$"), "");
                    if(isFormatoBooleano(segmento.cambiarVariables(aux))){
                        BMientras bMientras = new BMientras(aux);
                        bMientras.setVariables(segmento.getVariables());
                        i = getParrafos(parrafos, i, bMientras);
                    }else parrafo.setMensajeError("Condicion no valida en el ciclo de la linea ".concat(String.valueOf(parrafo.getRenglon())));
                    break;
                case ACCION:
                    aux = parrafo.getValor();
                    String identificador = aux.replaceAll("([\\s]+[\\(].*)$", "");
                    identificador = identificador.replaceFirst("^([\\s]*".concat(TPReservada.ACCION.getRegex()).concat("[\\s]+)"), "");
                    aux = aux.replaceFirst("^([\\s]*".concat(TPReservada.ACCION.getRegex()).concat(".*[\\(])"), "");
                    aux = aux.replaceAll("([\\s]*[\\)][\\s]*".concat(TPReservada.ENTONCES.getRegex()).concat("[\\s]*)$"), "");
                    if(!aux.isEmpty()){
                        SAccion accion = new SAccion(identificador, getVariables(aux));
                        parrafo.setCorrecto();
                        i = getMetodo(parrafos, i, accion);
                    }else parrafo.setMensajeError("Tiene que tener al menos un argumento en el metodo en la linea ".concat(String.valueOf(parrafo.getRenglon())));
                    break;
                case PACCION:
                    segmento.setAcciones(new PAccion(parrafo.getValor(), segmento, (SAccion)segmento.getMetodo(parrafo.getValor())));
                    parrafo.setCorrecto();
                    break;
                case FUNCION:
                    aux = parrafo.getValor();
                    String id = aux.replaceFirst("^([\\s]*".concat(TPReservada.OPERACION.getRegex()).concat("[\\s]+)"), "");
                    id = id.replaceAll("[\\(].*", "");
                    String tipoFuncion = aux.replaceAll("^(.*".concat(TPReservada.ES.getRegex()).concat("[\\s]+)"), "");
                    tipoFuncion = tipoFuncion.replaceFirst("([\\s]+)$", "");
                    aux = aux.replaceFirst("^(.*[\\(][\\s]*)", "");
                    aux = aux.replaceFirst("([\\s]*[\\)].*)$", "");
                    if(!aux.isEmpty()){
                        SFuncion funcion = new SFuncion(id, getVariables(aux), getTipo(tipoFuncion, "interna"));
                        parrafo.setCorrecto();
                        i = getMetodo(parrafos, i, funcion);
                    }else parrafo.setMensajeError("Tiene que tener al menos un argumento en la funcion en la linea ".concat(String.valueOf(parrafo.getRenglon())));
                    break;
                case RETORNA:
                    aux = parrafo.getValor().replaceFirst("[\\s]*".concat(TPReservada.RETORNA.getRegex()).concat("[\\s]+"), "");
                    segmento.setAcciones(new PRetorna(aux, (SFuncion)segmento, segmento));
                case LIMPIAR:
                case ESPERAR:
                case VACIA:
                case PFUNCION:
                case ASIGNACION:
                    parrafo.setCorrecto();
                    break;
                default:
                    parrafo.setMensajeError("Sintaxis incorrecta en la linea ".concat(String.valueOf(parrafo.getRenglon())));
            }
        }
        return parrafos.stream().noneMatch((parrafo) -> (parrafo.isError()));
    }
    private List<Variable> getVariables(String cadena){
        List<Variable> variables=new ArrayList<>();
        String[] tokens = cadena.split("[,]");
        for (int i = 0; i < tokens.length; i++) {
            String cProcesada = tokens[i];
            cProcesada = cProcesada.replaceFirst("^([\\s]+)", "");
            cProcesada = cProcesada.replaceAll("([\\s]+)$", "");
            String[] subTokents = cProcesada.split("[\\s]+");
            variables.add(getTipo(subTokents[2], subTokents[0]));
        }
        return variables;
    }
    private boolean isEsperar(String cadena){
        String aux = segmento.cambiarVariables(cadena);
        if(!aux.matches("^([\\s]*".concat(TPReservada.ESPERAR.getRegex()).concat("[\\s]+[\\d]+[\\s]*)$")))return false;
        cadena = cadena.replaceAll(TPReservada.ESPERAR.getRegex(), "");
        segmento.setAcciones(new PEsperar(cadena, segmento));
        return true;
    }
    private boolean isLimpiar(String cadena){
        if(!cadena.matches("^([\\s]*".concat(TPReservada.LIMPIAR.getRegex()).concat("[\\s]*)$")))return false;
        segmento.setAcciones(new PLimpiar(interprete));
        return true;
    }
    private void setImpresion(Parrafo parrafo){
        String cadena = parrafo.getValor();
        cadena = cadena.replaceAll("^([\\s]*".concat(TPReservada.IMPRIME.getRegex()).concat("[\\s]+)"), "");
        String aux = cadena;
        cadena = segmento.cambiarVariables(cadena);
        cadena = cadena.replaceAll("([\\s]+)$", "");
        if(isFormatoTexto(cadena)){
            Proceso proceso = new PImprimir(aux, segmento, interprete);
            segmento.setAcciones(proceso);
            parrafo.setCorrecto();
        }else{
            parrafo.setMensajeError("Parametros incorrectos en la impresion en el renglon ".concat(String.valueOf(parrafo.getRenglon())));
        }
    }
    private boolean isFormatoTexto(String cadena){
        return cadena.matches("^([\\s]*([\\d]+([.][\\d]+)?|['].[']|[\"].*[\"]|(".concat(TPReservada.VERDADERO.getRegex()).concat("|").concat(TPReservada.FALSO.getRegex()).concat("))([ ][+][ ]([\\d]+([.][\\d]+)?|['].[']|[\"].*[\"]|(").concat(TPReservada.VERDADERO.getRegex()).concat("|").concat(TPReservada.FALSO.getRegex()).concat(")))*[\\s]*)$"));
    }
    private boolean isFormatoCaracter(String cadena){
        return cadena.matches("^([\\s]*['].['][\\s]*)$");
    }
    private boolean isFormatoNumerico(String cadena){
        return cadena.matches("^([\\s]*([\\(][\\s]+)?([\\d]+([.][\\d]+)?)([\\s]+([+]|[-]|[*]|[/])[\\s]+([\\(][\\s]+)?([\\d]+([.][\\d]+)?)([\\s]+[\\)])*)*([\\s]+[\\)])*[\\s]*)$");
    }
    private boolean isFormatoBooleano(String cadena){
        return cadena.matches("^([\\s]*((".concat(TPReservada.VERDADERO.getRegex()).concat("|").concat(TPReservada.FALSO.getRegex()).concat(")|(([\"].*[\"]|['].[']|[\\d]+([.][\\d]+)*|(").concat(TPReservada.VERDADERO.getRegex()).concat("|").concat(TPReservada.FALSO.getRegex()).concat("))[\\s]+(").concat(TPReservada.EQUIVALE.getRegex()).concat("|").concat(TPReservada.DIFERENTE.getRegex()).concat(")[\\s]+([\"].*[\"]|['].[']|[\\d]+([.][\\d]+)*|(").concat(TPReservada.VERDADERO.getRegex()).concat("|").concat(TPReservada.FALSO.getRegex()).concat("))|(([\\d]+([.][\\d]+)*)[\\s]+(").concat(TPReservada.MENOROIGUALQUE.getRegex()).concat("|").concat(TPReservada.MENORQUE.getRegex()).concat("|").concat(TPReservada.MAYOROIGUALQUE.getRegex()).concat("|").concat(TPReservada.MAYORQUE.getRegex()).concat(")[\\s]+([\\d]+([.][\\d]+)*))))([\\s]+(").concat(TPReservada.Y.getRegex()).concat("|").concat(TPReservada.O.getRegex()).concat(")[\\s]+((").concat(TPReservada.VERDADERO.getRegex()).concat("|").concat(TPReservada.FALSO.getRegex()).concat(")|(([\"].*[\"]|['].[']|[\\d]+([.][\\d]+)|(").concat(TPReservada.VERDADERO.getRegex()).concat("|").concat(TPReservada.FALSO.getRegex()).concat("))[\\s]+(").concat(TPReservada.EQUIVALE.getRegex()).concat("|").concat(TPReservada.DIFERENTE.getRegex()).concat(")[\\s]+([\"].*[\"]|['].[']|[\\d]+([.][\\d]+)*|(").concat(TPReservada.VERDADERO.getRegex()).concat("|").concat(TPReservada.FALSO.getRegex()).concat("))|(([\\d]+([.][\\d]+)*)[\\s]+(").concat(TPReservada.MENOROIGUALQUE.getRegex()).concat("|").concat(TPReservada.MENORQUE.getRegex()).concat("|").concat(TPReservada.MAYOROIGUALQUE.getRegex()).concat("|").concat(TPReservada.MAYORQUE.getRegex()).concat(")[\\s]+([\\d]+([.][\\d]+)*)))))*[\\s]*)$"));
    }
    private boolean isImprimir(String cadena){
        return cadena.matches("^([\\s]+".concat(TPReservada.IMPRIME.getRegex()).concat("[\\s]+).+"));
    }
    private boolean isSi(String cadena){
        return cadena.matches("^([\\s]*".concat(TPReservada.SI.getRegex()).concat("[\\s]+.*[\\s]+").concat(TPReservada.ENTONCES.getRegex()).concat("[\\s]*)$"));
    }    
    private boolean isSiNo(String cadena){
        return cadena.matches("^([\\s]*".concat(TPReservada.SINO.getRegex()).concat("[\\s]+.*[\\s]+").concat(TPReservada.ENTONCES.getRegex()).concat("[\\s]*)$"));
    }
    private boolean isNo(String cadena){
        return cadena.matches("^([\\s]*".concat(TPReservada.NO.getRegex()).concat("[\\s]*)$"));
    }
    private boolean isDe(String cadena){
        return cadena.matches("^([\\s]*".concat(TPReservada.DE.getRegex()).concat("[\\s]+([a-zA-Z][\\w]*)[\\s]+[=]").concat("[\\s]+.*[\\s]+").concat(TPReservada.HASTA.getRegex()).concat("[\\s]+.*[\\s]+").concat(TPReservada.PASO.getRegex()).concat("[\\s]+.*[\\s]+").concat(TPReservada.ENTONCES.getRegex()).concat("[\\s]*)$"));
    }
    private boolean isMientras(String cadena){
        return cadena.matches("^([\\s]*".concat(TPReservada.MIENTRAS.getRegex()).concat("[\\s]+.*[\\s]+").concat(TPReservada.ENTONCES.getRegex()).concat("[\\s]*)$"));
    }
    private boolean isAccion(String cadena){
        return cadena.matches("^([\\s]*".concat(TPReservada.ACCION.getRegex()).concat("[\\s]+[\\w]+[\\s]*[\\(][\\s]*([\\w]+[\\s]+").concat(TPReservada.ES.getRegex()).concat("[\\s]+(").concat(TPReservada.TEXTO.getRegex()).concat("|").concat(TPReservada.CARACTER.getRegex()).concat("|").concat(TPReservada.ENTERO.getRegex()).concat("|").concat(TPReservada.DECIMAL.getRegex()).concat("|").concat(TPReservada.BOOLEANO.getRegex()).concat(")([\\s]*[,][\\s]*[\\w]+[\\s]+").concat(TPReservada.ES.getRegex()).concat("[\\s]+(").concat(TPReservada.TEXTO.getRegex()).concat("|").concat(TPReservada.CARACTER.getRegex()).concat("|").concat(TPReservada.ENTERO.getRegex()).concat("|").concat(TPReservada.DECIMAL.getRegex()).concat("|").concat(TPReservada.BOOLEANO.getRegex()).concat("))*)?[\\s]*[\\)][\\s]*").concat(TPReservada.ENTONCES.getRegex()).concat("[\\s]*)$"));
    }
    private boolean isFuncion(String cadena){
        return cadena.matches("^([\\s]*".concat(TPReservada.OPERACION.getRegex()).concat("[\\s]+[\\w]+[\\s]*[\\(][\\s]*([\\w]+[\\s]+").concat(TPReservada.ES.getRegex()).concat("[\\s]+(").concat(TPReservada.TEXTO.getRegex()).concat("|").concat(TPReservada.CARACTER.getRegex()).concat("|").concat(TPReservada.ENTERO.getRegex()).concat("|").concat(TPReservada.DECIMAL.getRegex()).concat("|").concat(TPReservada.BOOLEANO.getRegex()).concat(")([\\s]*[,][\\s]*[\\w]+[\\s]+").concat(TPReservada.ES.getRegex()).concat("[\\s]+(").concat(TPReservada.TEXTO.getRegex()).concat("|").concat(TPReservada.CARACTER.getRegex()).concat("|").concat(TPReservada.ENTERO.getRegex()).concat("|").concat(TPReservada.DECIMAL.getRegex()).concat("|").concat(TPReservada.BOOLEANO.getRegex()).concat("))*)?[\\s]*[\\)][\\s]*").concat(TPReservada.ES.getRegex()).concat("[\\s]+").concat("(").concat(TPReservada.TEXTO.getRegex()).concat("|").concat(TPReservada.CARACTER.getRegex()).concat("|").concat(TPReservada.ENTERO.getRegex()).concat("|").concat(TPReservada.DECIMAL.getRegex()).concat("|").concat(TPReservada.BOOLEANO.getRegex()).concat(")[\\s]*)$"));
    }
    private boolean isRetorna(String cadena){
        return cadena.matches("[\\s]*".concat(TPReservada.RETORNA.getRegex()).concat("[\\s]+.*"))?isBienAsignado(((SFuncion)segmento).getFuncion(), cadena.replaceFirst("[\\s]*".concat(TPReservada.RETORNA.getRegex()).concat("[\\s]+"), "")):false;
    }
    private boolean isFuncionAsignada(String cadena){
        cadena = cadena.replaceAll("[\\s]+", "");
        String[] tokens = cadena.split("[=]");
        if(tokens.length!=2)return false;
        if(segmento.isMetodo(tokens[1])){
            if(isBienAsignado(segmento.getVariable(tokens[0]), ((SFuncion)segmento.getMetodo(tokens[1])).getFuncion().getValor())){
                String argumentos = tokens[1].replaceFirst(".*[\\(]", "");
                argumentos = argumentos.replaceFirst("[\\)].*", "");
                segmento.setAcciones(new PAsignacionFuncion(argumentos, segmento.getVariable(tokens[0]), (SFuncion)segmento.getMetodo(tokens[1]), segmento));
                return true;
            }
        }
        return false;
    }
    private TSentencia analisisGeneral(String cadena){
        cadena=" ".concat(cadena);
        if("".equals(cadena)||isVacia(cadena))return TSentencia.VACIA;
        if(isVariable(cadena))return TSentencia.DECLARACION;       
        if(isAsignacion(cadena))return TSentencia.ASIGNACION;
        if(isImprimir(cadena))return TSentencia.IMPRIMIR;
        if(isLimpiar(cadena))return TSentencia.LIMPIAR;
        if(isEsperar(cadena))return TSentencia.ESPERAR;
        if(isSi(cadena))return TSentencia.SI;
        if(isDe(cadena))return TSentencia.DE;
        if(isMientras(cadena))return TSentencia.MIENTRAS;
        if(segmento instanceof BEjecutor && isAccion(cadena))return TSentencia.ACCION;
        if(segmento instanceof BEjecutor && isFuncion(cadena))return TSentencia.FUNCION;
        if(segmento instanceof BSi && isSiNo(cadena) && !((BSi)segmento).getNo())return TSentencia.SINO;
        if(segmento instanceof BSi && isNo(cadena) && !((BSi)segmento).getNo())return TSentencia.NO;
        if(segmento.isMetodo(cadena))return TSentencia.PACCION;
        if(isFuncionAsignada(cadena)) return TSentencia.PFUNCION;
        if(segmento instanceof SFuncion && isRetorna(cadena)) return TSentencia.RETORNA;
        return TSentencia.ERROR;
    }
    private boolean isRecibe(String cadena){
        return cadena.matches("^([\\s]*".concat(TPReservada.RECIBE.getRegex()).concat("[\\s]+[\"].+[\"][\\s]*)$"));
    }
    private boolean isAsignacion(String cadena){
        String[] tokens = cadena.split("[=]");
        if(tokens.length!=2)return false;
        tokens[0]=tokens[0].replaceAll("[\\s]+","");
        Variable variable = segmento.getVariable(tokens[0]);
        if(isRecibe(tokens[1])){
            tokens[1]=tokens[1].replaceAll("^([\\s]*".concat(TPReservada.RECIBE.getRegex()).concat("[\\s]+[\"])"), "");
            tokens[1]=tokens[1].replaceAll("([\"].*)$", "");
            Proceso proceso = new PRecibe(tokens[1], variable, interprete);
            segmento.setAcciones(proceso);
            return true;
        }
        if(isBienAsignado(variable, tokens[1])){
            Proceso proceso = new PAsignacion(tokens[1], variable, segmento);
            segmento.setAcciones(proceso);
            return true;
        }else return false;
    }
    private boolean isParentecisCorrectos(String cadena){
        int a = 0, b = 0;
        for (int i = 0; i < cadena.length(); i++) {
            if(cadena.charAt(i)=='(')a++;
            if(cadena.charAt(i)==')')b++;
        }
        return a == b;
    }
    private boolean isBienAsignado(Variable variable, String asignacion){
        asignacion = segmento.cambiarVariables(asignacion);
        if(variable instanceof VTexto)return isFormatoTexto(asignacion);
        if(variable instanceof VCaracter)return isFormatoCaracter(asignacion);
        if(variable instanceof VBooleano)return isFormatoBooleano(asignacion);
        if(variable instanceof VDecimal || variable instanceof VEntero)return isFormatoNumerico(asignacion) && isParentecisCorrectos(asignacion);
        return false;
    }
    private boolean isVariable(String cadena){
        return cadena.matches("^([\\s]*[".concat(TPReservada.R.getRegex()).concat("])[\\s]+[a-zA-Z][\\w]*[\\s]+[E][S][\\s]+((").concat(TPReservada.TEXTO.getRegex()).concat("|").concat(TPReservada.CARACTER.getRegex()).concat("|").concat(TPReservada.ENTERO.getRegex()).concat("|").concat(TPReservada.DECIMAL.getRegex()).concat("|").concat(TPReservada.BOOLEANO.getRegex()).concat(")[\\s]*)$"));
    }
    private boolean isVacia(String cadena){
        return Pattern.compile("^([\\s]+)$").matcher(cadena).matches();
    }
    private void setVariable(Parrafo parrafo){
        String cadena = parrafo.getValor();
        cadena = cadena.replaceAll("[\\s]+".concat(TPReservada.R.getRegex()), TPReservada.R.toString());
        String[] tokens = cadena.split("[\\s]+");
        String identificador = tokens[1];
        if(Utileria.isPalabraReservada(identificador)){
            parrafo.setMensajeError("Esta intentando ingresar como nombre una palabra reservada en la linea ".concat(String.valueOf(parrafo.getRenglon())));
            return;
        }
        if(segmento.isNombreAsignado(identificador)){
            parrafo.setMensajeError("Variable duplicada en la linea ".concat(String.valueOf(parrafo.getRenglon())));
            return;            
        }
        segmento.setVariable(getTipo(tokens[3], identificador));
        parrafo.setCorrecto();
    }
    private Variable getTipo(String tipo, String identificador){
        if(tipo.equals(TPReservada.TEXTO.toString()))return new VTexto(identificador);
        if(tipo.equals(TPReservada.CARACTER.toString()))return new VCaracter(identificador);
        if(tipo.equals(TPReservada.ENTERO.toString()))return new VEntero(identificador);
        if(tipo.equals(TPReservada.DECIMAL.toString()))return new VDecimal(identificador);
        return new VBooleano(identificador);
    }
    public BEjecutor getEjecutor(){
        return (BEjecutor) segmento;
    }
}
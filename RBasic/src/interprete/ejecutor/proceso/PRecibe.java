package interprete.ejecutor.proceso;

import diccionario.TPReservada;
import interprete.Interprete;
import interprete.ejecutor.variables.*;

public class PRecibe extends Proceso{
    private final Interprete interprete;
    private final Variable variable;
    public PRecibe(String sentencia, Variable variable, Interprete interprete) {
        super(sentencia, null);
        this.interprete=interprete;
        this.variable=variable;
        variable.asignar();
    }
    @Override
    public void procesar() { 
        interprete.showPeticion(sentencia);
        interprete.setEspera();
        String cadena = "s"; 
        do {
            System.out.println("WAIT");
            cadena=interprete.getEntrada();
        } while (cadena.equals(""));
        interprete.setEspera();
        if(isSintaxis(variable, cadena)){
            interprete.showMensaje(cadena);
            variable.setValor(cadena);
        }else throw new IllegalArgumentException("Ingreso un valor no valido para la variable");
    }
    private boolean isSintaxis(Variable variable, String cadena){
        if(variable instanceof VTexto)return true;
        if(variable instanceof VCaracter)return cadena.length()==1;
        if(variable instanceof VDecimal || variable instanceof VEntero)return cadena.matches("^([-]?[\\d]+([.][\\d]+)*)$");
        if(variable instanceof VBooleano)return cadena.matches("^(".concat(TPReservada.VERDADERO.getRegex()).concat("|").concat(TPReservada.FALSO.getRegex()).concat(")$"));
        return false;
    }
}
package interprete.ejecutor.bloque;

import interprete.ejecutor.Accion;
import java.util.ArrayList;
import java.util.List;
import utileria.calculadoraBooleana.Calculadora;

public class BSi extends Bloque{
    
    private List<List<Accion>> grupoAcciones = new ArrayList<>();
    private List<String> condiciones = new ArrayList<>();
    private boolean no = false;
    @Override
    public void procesar() {
        acciones = new ArrayList<>();
        for (int i = 0; i < condiciones.size(); i++) {
            if(Calculadora.resultado(cambiarVariables(condiciones.get(i)))){
                acciones = grupoAcciones.get(i);
                break;
            }
        }
        acciones.stream().forEach((accione) -> {accione.procesar();});        
    }
    public boolean getNo(){
        return no;
    }
    public void setNo(){
        no=true;
    }
    public void setCondicion(String condicion){
        condiciones.add(condicion);
        grupoAcciones.add(new ArrayList<>());
        acciones = grupoAcciones.get(grupoAcciones.size()-1);
    }
}
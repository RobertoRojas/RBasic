package interprete.ejecutor.proceso;

import interprete.ejecutor.bloque.Bloque;

public class PEsperar extends Proceso{
    public PEsperar(String sentencia, Bloque ejecutor) {
        super(sentencia, ejecutor);
    }
    @Override
    public void procesar() {
        String aux = super.ejecutor.cambiarVariables(super.sentencia);
        aux = aux.replaceAll("[\\s]+", "");
        int espera = Integer.parseInt(aux) * 1000;
        try {
            Thread.sleep(espera);
        } catch (InterruptedException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }
}
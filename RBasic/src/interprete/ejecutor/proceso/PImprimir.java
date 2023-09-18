package interprete.ejecutor.proceso;

import interprete.Interprete;
import interprete.ejecutor.bloque.Bloque;
import utileria.Utileria;

public class PImprimir extends Proceso{
    private final Interprete interprete;
    public PImprimir(String sentencia, Bloque ejecutor, Interprete interprete) {
        super(sentencia, ejecutor);
        this.interprete=interprete;
    }
    @Override
    public void procesar() {
        String aux = "";
        String[] tokens = super.sentencia.split("[+]");
        for (String token : tokens) {
            String interno;
            if (!Utileria.isComitas(token)) {
                interno = token.replaceAll("[\\s]+", "");
                interno = super.ejecutor.cambiarVariables(interno);
            } else interno = token; 
            interno = interno.replaceFirst("^([\\s]*[\"])", "");
            aux = aux.concat(interno.replaceAll("([\"][\\s]*)$", ""));
        }
        aux = Utileria.formatoImpresion(aux);
        interprete.showMensaje(aux);
    }
}
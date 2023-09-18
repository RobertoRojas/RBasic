package interprete;

import interfaz.Interfaz;
import interprete.analizador.Analizador;
import interprete.ejecutor.BEjecutor;
import java.util.ArrayList;
import java.util.List;

public class Interprete extends Thread{
    private final List<Parrafo> parrafos = new ArrayList<>();
    private final Interfaz padre;
    private final Analizador analizador = new Analizador(this, new BEjecutor());
    private boolean espera = false;
    public Interprete(Interfaz padre){
        this.padre = padre;
    }
    public void setEspera(){
        padre.limpiarEntrada();
        espera=!espera;
    }
    public String getEntrada(){
        return padre.getEntrara();
    }
    public boolean isEspera(){
        return espera;
    }
    @Override
    public void run(){
        proceso();
    }
    public void setText(String texto){
        parrafos.clear();
        String[]tokens=texto.split("\n");
        for (int i = 0; i < tokens.length; i++)parrafos.add(new Parrafo(tokens[i], i+1));
    }
    private void proceso(){
        boolean termino = true;
        try{
            if(analizador.procesar(parrafos)){
                ejecutar();
                termino=false;
            } else generarMensajesError();
        }catch(Exception e){
            showError(e.getMessage()!=null?e.getMessage():"Error de sintaxis probablemente...");
        }
        padre.detener(termino);
    }
    private void ejecutar(){
        analizador.getEjecutor().procesar();
    } 
    private void generarMensajesError(){
        parrafos.stream().filter((parrafo) -> (parrafo.isError())).forEach((parrafo) -> showError(parrafo.getError()));
    }
    public void clearConsola(){
        padre.clearConsola();
    }
    public void showMensaje(String mensaje){
        padre.setMensaje("<div>".concat(mensaje).concat("</div>"));
    }
    private void showError(String mensaje){
        padre.setMensaje("<div class='error'>".concat(mensaje).concat("</div>"));    
    }
    public void showPeticion(String mensaje){
        padre.setMensaje("<div class='peticion'>".concat(mensaje).concat("</div>"));
    }
}
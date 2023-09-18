package interprete;

public class Parrafo {
    private final String valor;
    private final int renglon;
    private boolean error = true;
    private String mensajeError = "";
    public Parrafo(String valor, int renglon){
        valor=valor.replaceFirst("[$].*", "");
        this.valor=valor;
        this.renglon=renglon;
    }
    public void setMensajeError(String mensajeError){
        this.mensajeError=mensajeError;
    }
    public String getError(){
        return mensajeError.isEmpty()?"Error del compilador en la linea ".concat(String.valueOf(renglon)).concat("|").concat(valor).concat("|"):mensajeError;
    }
    public String getValor(){
        return valor;
    }
    public int getRenglon(){
        return renglon;
    }
    public void setCorrecto(){
        error=false;
    }
    public boolean isError(){
        return error;
    }
}
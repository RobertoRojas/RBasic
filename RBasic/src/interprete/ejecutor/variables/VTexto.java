package interprete.ejecutor.variables;

public class VTexto implements Variable{
    private final String nombre;    
    private boolean asignado=false;
    private String valor="";
    public VTexto(String nombre) {
        this.nombre=nombre;
    }
    @Override
    public void setValor(String valor){
        this.valor=valor;
        asignar();
    }
    @Override
    public String getValor(){
        return "\"".concat(valor).concat("\"");
    }
    @Override
    public String getNombre() {
        return nombre;
    }
    @Override
    public void asignar() {
        asignado=true;
    }
    @Override
    public boolean isAsignado() {
        return asignado;
    }
}
package interprete.ejecutor.variables;

public class VCaracter implements Variable{
    private final String nombre;
    private boolean asignado=false;
    private char valor=' ';
    public VCaracter(String nombre) {
        this.nombre=nombre;
    }
    @Override
    public void setValor(String valor){
        valor=valor.replaceAll("^([\\s]*['])", "");
        valor=valor.replaceAll("(['][\\s]*)$", "");
        this.valor=valor.charAt(0);
        asignar();
    }
    @Override
    public String getValor(){
        return "'".concat(String.valueOf(valor)).concat("'");
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
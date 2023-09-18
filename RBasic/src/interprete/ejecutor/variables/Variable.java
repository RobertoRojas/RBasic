package interprete.ejecutor.variables;

public interface Variable {
    public String getNombre();
    public void asignar();
    public boolean isAsignado();
    public void setValor(String valor);
    public String getValor();
}
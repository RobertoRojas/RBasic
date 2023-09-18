package interfaz.acercade.animacion;

import javax.swing.JEditorPane;
import javax.swing.text.html.HTMLEditorKit;

public class Renglon extends Thread{
    private int y, tamanio;
    private boolean activo = true;
    private final JEditorPane editor;
    private final String texto;
    private int r = 217, g = 188, b = 26;
    public Renglon(String texto, int y, int tamanio){
        this.y=y;this.tamanio=tamanio;
        this.texto=texto;
        editor = new JEditorPane();
        HTMLEditorKit kit = new HTMLEditorKit();
        editor.setEditorKit(kit);
        editor.setOpaque(false);editor.setEditable(false);
        setTexto();
        editor.setVisible(false);
    }
    public JEditorPane getRenglon(){
        return editor;
    }
    @Override
    public void run(){
        animacion();
    }
    public void setTexto(){
        editor.setText("<div style=\"font-size:".concat(String.valueOf(tamanio)).concat("px;color:rgb(").concat(String.valueOf(r)).concat(",").concat(String.valueOf(g)).concat(",").concat(String.valueOf(b)).concat(");\">").concat(texto).concat("<div>"));
        editor.setBounds(50, y, 700, tamanio + 20);
    }
    private int diferencial(int x, double porcentaje){
        return (int)Math.max(0, x - Math.ceil(x * porcentaje));
    }
    private void animacion(){
        editor.setVisible(true);
        while(activo){
            setTexto();
            try {
                Thread.sleep(150);
            } catch (InterruptedException ex) {
                break;
            }
            if(y<90){
                r = diferencial(r, 0.03);
                g = diferencial(g, 0.03);
                b = diferencial(b, 0.03);
            }
            y -= 1;
        }
    }
    public int getY(){
        return y;
    }
    protected int getTamanio(){
        return tamanio;
    }
    public void detener(){
        activo = false;
    }
}
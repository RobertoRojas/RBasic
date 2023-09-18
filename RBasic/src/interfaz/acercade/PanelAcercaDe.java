package interfaz.acercade;

import interfaz.Interfaz;
import interfaz.acercade.animacion.Animacion;
import interfaz.acercade.animacion.Renglon;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

public class PanelAcercaDe extends JPanel{
    private final Interfaz interfaz;
    private JEditorPane editor;
    private Animacion animacion = new Animacion(this);
    public PanelAcercaDe(Interfaz interfaz){
        this.interfaz=interfaz;
        setLayout(null);
        setPreferredSize(new Dimension(700, 500));
        setBackground(Color.BLACK);
        iniciarComponentes();
    }
    public void iniciarAnimacion(){
        animacion.start();
    }
    public void terminarAnimacion(){
        animacion.detenerRenglones();
        if(animacion.isAlive())animacion.stop();
    }
    private void iniciarComponentes(){
        editor = new JEditorPane();
        HTMLEditorKit kit = new HTMLEditorKit();
        editor.setEditorKit(kit);
        editor.setOpaque(false);
        StyleSheet styleSheet = kit.getStyleSheet();
        styleSheet.addRule("body{font-size:23px;font-family: \"STARWARS\";background:rgba(0,0,0,0.5);color:rgb(33,181,184);text-align: center;}");
        editor.setDocument(kit.createDefaultDocument());
        editor.setEditable(false);
        editor.setText("<div>Hace mucho tiempo, en una UVM<div>"
                     + "<div>muy, muy lejana&nbsp;&nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</div>");
        editor.setBounds(0, 190, 700, 100);
        add(editor);
    }
    
    public void addRenglon(Renglon renglon){
        add(renglon.getRenglon());
    }
    public void esconderInicio(){
        editor.setVisible(false);
    }
    @Override
    protected void paintComponent(Graphics g) {
        //Cambiar a ruta de interfaz en algun momento
        g.drawImage(new ImageIcon(interfaz.getRuta().concat("/img/fondo.jpg")).getImage(), 0, 0,700, 500, this);
    }
}
package interfaz.subventanas;

import interfaz.Interfaz;
import java.awt.Dimension;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

public class Base{
    private final Object[] tags;
    private final String titulo;
    private final String contenido;
    private final Interfaz interfaz;
    public Base(Object[] tags,String contenido,String titulo,Interfaz interfaz){
        this.tags=tags;
        this.contenido=contenido;
        this.interfaz=interfaz;
        this.titulo=titulo;
        iniciarComponentes();
    }    
    private void iniciarComponentes(){
        editor = new JEditorPane();
        editor.setContentType("text/html");
        HTMLEditorKit kit = new HTMLEditorKit();
        editor.setEditorKit(kit);
        StyleSheet styleSheet = kit.getStyleSheet();
        styleSheet.addRule("body{font-family: \"Estrangelo Edessa\", Sans-serif;font-size: 16px;width: 463px;}");
        styleSheet.addRule(".titulo{font-size: 27px;font-weight: bold;}");
        styleSheet.addRule(".subTitulo{font-size: 18px;font-style: italic;}");
        styleSheet.addRule(".seudoCodigo{color:rgb(19,148,139);margin: 0 auto;}");
        styleSheet.addRule(".booleano{color: rgb(237,32,32);}");
        styleSheet.addRule(".palabra{color:blue;font-weight: bold;}");
        styleSheet.addRule(".funcion{color:rgb(39,168,207);}");
        styleSheet.addRule(".dato{color:green;font-weight: bold;}");
        styleSheet.addRule(".cadena{color:orange;}");
        styleSheet.addRule(".numero{color:rgb(163,50,191);}");
        styleSheet.addRule(".codigo1{background: rgb(237,235,166);padding-left: 10px;}");
        styleSheet.addRule(".codigo2{background: rgb(237,236,211);padding-left: 10px;}");
        styleSheet.addRule(".caja{width: 360px;font-family: \"Calibri\", Sans-serif;font-size: 14px;border-style:solid;border-width: 1px;margin-left:60px;}");
        styleSheet.addRule(".comentario{color:rgb(101,101,101);}");
        editor.setDocument(kit.createDefaultDocument());
        editor.setEditable(false);
        editor.setText(contenido);
        jEditor = new JScrollPane(editor);
        jEditor.setPreferredSize(new Dimension(640,600));
        jEditor.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }
    public final String getTitulo(){
        return titulo;
    }
    public final Object[] getTags(){
        return tags;
    }
    public final void showMensaje(){
        JOptionPane.showOptionDialog(interfaz, new Object[]{jEditor}, titulo, JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[]{}, null);
    }
    private JEditorPane editor;
    private JScrollPane jEditor;
}
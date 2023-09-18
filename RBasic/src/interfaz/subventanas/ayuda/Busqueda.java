package interfaz.subventanas.ayuda;

import interfaz.subventanas.Base;
import interfaz.Interfaz;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import utileria.Utileria;

public class Busqueda {
    private Interfaz interfaz;
    private List<Base> lista = new ArrayList<>();
    public Busqueda(String busqueda, Interfaz interfaz){
        this.interfaz=interfaz;
        if(!busqueda.isEmpty()){
            List<Base> interna = interfaz.getAyudas();
            interna.stream().filter((ayuda) -> (isMiembro(busqueda, ayuda.getTags()))).forEach((ayuda) -> {
                lista.add(ayuda);
            });
        }else lista = interfaz.getAyudas();
    }
    private boolean isMiembro(String busqueda, Object[] tag){
        for (Object tag1 : tag)if (Utileria.isPalabraSimilar(busqueda, tag1.toString()))return true;
        return false;
    }
    public void showMensaje(){
        if(lista.size()==1){
            lista.get(0).showMensaje();
            return;
        }
        if(lista.isEmpty()){
            JOptionPane.showMessageDialog(interfaz,"No fue posible encontrar ayuda,pero puede buscar manualmente si lo desea.","¡No se pudo encontrar ayuda!",JOptionPane.WARNING_MESSAGE);
            lista = interfaz.getAyudas();
        }
        JList jLista = new JList();
        jLista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        DefaultListModel modelo = new DefaultListModel();
        lista.stream().forEach((lista1) -> {
            modelo.addElement(lista1.getTitulo());
        });
        jLista.setModel(modelo);
        jLista.setOpaque(true);
        jLista.setBackground(Color.GRAY);
        jLista.setCellRenderer(new CeldaRender());
        JScrollPane sJLista = new JScrollPane(jLista);
        sJLista.setPreferredSize(new Dimension(380,282));
        jLista.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt){
                if(evt.getClickCount()==2){
                    abrirDialogo(jLista.locationToIndex(evt.getPoint()));
                }
            }
        });
        JOptionPane.showOptionDialog(interfaz, new Object[]{sJLista}, "Ayuda", JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE,null, new Object[]{}, null);
    }
    private void abrirDialogo(int index){
        lista.get(index).showMensaje();
    }
    private class CeldaRender implements ListCellRenderer{
        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = new JLabel(value.toString());
            Color letra;
            Color fondo;
            Color borde;
            label.setOpaque(true);
            label.setFont(new Font("Estrangelo Edessa", Font.BOLD, 20));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setPreferredSize(new Dimension(350, 40));
            if(isSelected){
                letra = new Color(48, 48, 48);
                fondo = new Color(227, 233, 250);
                borde = new Color(50, 50, 50);
            }else{
                letra = new Color(89, 89, 89);
                fondo = new Color(255, 255, 255);
                borde = new Color(168, 168, 168);
            }
            label.setForeground(letra);
            label.setBackground(fondo);
            label.setBorder(BorderFactory.createLineBorder(borde));
            return label;
        }        
    }
}
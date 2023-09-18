package utileria;

import interfaz.Interfaz;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class CargarArchivo {
    private String texto="",ruta="",nombre="Nombre no encontrado";
    public boolean getArchivo(Interfaz padre){
        JFileChooser open = new JFileChooser();
        open.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Codigo RBASIC", "rbasic");
        open.addChoosableFileFilter(filter);
        if(open.showOpenDialog(padre)!=JFileChooser.APPROVE_OPTION)return false;
        ruta = open.getSelectedFile().getAbsolutePath();
        return getArchivo(padre, open.getSelectedFile());
    }
    public boolean getArchivo(Interfaz padre, File file){
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String aux;
            while((aux=br.readLine())!=null)texto = texto.concat(aux).concat("\n");
            br.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(padre, "Error: ".concat(ex.getMessage()),"¡Problemas al abrir archivo!",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        nombre = file.getName().replaceFirst("[.][r][b][a][s][i][c]", "");
        return true;
    }
    public String getNombre(){
        return nombre;
    }
    public String getTexto(){
        return texto;
    }
    @Override
    public String toString(){
        return ruta;
    }
}
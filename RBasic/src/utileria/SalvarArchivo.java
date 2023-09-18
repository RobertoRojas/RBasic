package utileria;

import interfaz.Interfaz;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class SalvarArchivo {
    private File archivo;
    private boolean rutaEspecificada = true;
    private final Interfaz padre;
    private String nombre = "Nombre no encontrado";
    public SalvarArchivo(String ruta, Interfaz padre){
        this.padre = padre;
        if(ruta.isEmpty()){
            accederArchivo();
        }else archivo = new File(ruta);
    }
    private void accederArchivo(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar codigo fuente");
        if(fileChooser.showSaveDialog(padre)==JFileChooser.APPROVE_OPTION){
            String ruta = fileChooser.getSelectedFile().toString().replaceAll("[.].*", "");
            archivo = new File(ruta.concat(".rbasic"));
        }else{
            rutaEspecificada=false;
            archivo = new File("");
        }
    }
    public boolean salvar(String texto) throws IOException{
        if(!rutaEspecificada)return false;
        if(archivo.exists()){
            if(JOptionPane.showConfirmDialog(padre, "El archivo que selecciono ya existe ¿Desea sobreescribirlo?","¿Desea sobreescribir archivo?",JOptionPane.YES_NO_OPTION)!=JOptionPane.YES_OPTION){
                accederArchivo();
                return salvar(texto);
            }
        }
        BufferedWriter bw = new BufferedWriter(new FileWriter(archivo));
        bw.write(texto);      
        bw.close();
        nombre = archivo.getName().replaceFirst("[.][r][b][a][s][i][c]", "");
        return true;
    }
    public String getNombre(){
        return nombre;
    }
    @Override
    public String toString(){
        return archivo.toString();
    }
}
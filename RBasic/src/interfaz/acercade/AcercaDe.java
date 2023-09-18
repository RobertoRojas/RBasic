package interfaz.acercade;

import interfaz.Interfaz;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;

public class AcercaDe {
    private final Interfaz interfaz;
    private final PanelAcercaDe panel;
    private Clip sonido;
    public AcercaDe(Interfaz interfaz){
        this.interfaz=interfaz;
        panel = new PanelAcercaDe(interfaz);
    }
    public void showMensaje(){
        try {
            panel.iniciarAnimacion();
            sonido = AudioSystem.getClip();
            sonido.open(AudioSystem.getAudioInputStream(new File(interfaz.getRuta().concat("/snd/fondo.wav"))));
            sonido.start();
            JOptionPane.showOptionDialog(interfaz, new Object[]{panel}, "Acerca de", JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[]{}, null);
            sonido.stop();
            panel.terminarAnimacion();
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
            System.out.println("changos: " + ex.getMessage());
            Logger.getLogger(AcercaDe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
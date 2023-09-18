package interfaz.acercade.animacion;

import interfaz.acercade.PanelAcercaDe;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Animacion extends Thread{
    private final PanelAcercaDe panel;
    private final List<Renglon> renglones = new ArrayList<>();
    private Robot robot;
    public Animacion(PanelAcercaDe panel){
        this.panel = panel;
        try {
            robot = new Robot();
        } catch (AWTException ex) {
            System.out.println("animacion robot loco: ".concat(ex.getMessage()));
        }
    }
    @Override
    public void run(){
        iniciarRenglones();
        proceso();
    }
    private void proceso(){
        robot.delay(8000);
        panel.esconderInicio();
        for (int i = 0; i < renglones.size(); i++) {
            renglones.get(i).start();
        }
        while(renglones.get(renglones.size()-1).getY()>300);
        robot.keyPress(KeyEvent.VK_ESCAPE);
        robot.keyRelease(KeyEvent.VK_ESCAPE);
        //panel.addRenglon(new Renglon("RBASIC", 40, 12));
    }
    public void detenerRenglones(){
        renglones.stream().forEach((renglone) -> {
            renglone.detener();
        });
    }
    private void iniciarRenglones(){
        iniciarRenglon(new Renglon("RBASIC", 100, 23));
        iniciarRenglon(new Renglon("Cuando se creía que la clase de ", 140, 17));
        iniciarRenglon(new Renglon("Jacinto viviría por siempre, el ", 160, 17));
        iniciarRenglon(new Renglon("imperio de Zavala junto con el ", 180, 17));
        iniciarRenglon(new Renglon("malvado Juan Manuel atacaron", 200, 17));
        iniciarRenglon(new Renglon("la clase perfecta de no ir a clase.", 220, 17));
        
        iniciarRenglon(new Renglon("Pero gracias a la alianza de los", 250, 17));
        iniciarRenglon(new Renglon("sistemos el estatus QUO volvió", 270, 17));
        iniciarRenglon(new Renglon("a la normalidad no haciendo las", 290, 17));
        iniciarRenglon(new Renglon("practicas que fueron dejando.", 310, 17));
        
        iniciarRenglon(new Renglon("Gracias al Luisillo por los ", 340, 17));
        iniciarRenglon(new Renglon("cigarros utilizados para la ", 360, 17));
        iniciarRenglon(new Renglon("realización de este compilador", 380, 17));
        iniciarRenglon(new Renglon("a Sergio por valer verga siempre", 400, 17));
        iniciarRenglon(new Renglon("gabo por pinche tramposo el el ", 420, 17));
        iniciarRenglon(new Renglon("guitar flash a Pepa por valer", 440, 17));
        iniciarRenglon(new Renglon("peor verga que checo, al", 460, 17));
        iniciarRenglon(new Renglon("George por prestar su casa", 480, 17));
        iniciarRenglon(new Renglon("para asi poder romperle el", 500, 17));
        iniciarRenglon(new Renglon("baño, al ram por vivir la ", 520, 17));
        iniciarRenglon(new Renglon("vida loca de los pugs, el", 540, 17));
        iniciarRenglon(new Renglon("Uriel por hacer cosas raras", 560, 17));
        iniciarRenglon(new Renglon("en la biblioteca, al matus", 580, 17));
        iniciarRenglon(new Renglon("por no apoyar en nada creo", 600, 17));
        iniciarRenglon(new Renglon("y al Luis B reponte de tu ", 620, 17));
        iniciarRenglon(new Renglon("perdida de pareja.", 640, 17));
        
        iniciarRenglon(new Renglon("Por ultimo un gran ", 670, 17));
        iniciarRenglon(new Renglon("agradecimiento a ", 690, 17));
        iniciarRenglon(new Renglon("alguien que nos ayudo", 710, 17));
        iniciarRenglon(new Renglon("mucho para poder", 730, 17));
        iniciarRenglon(new Renglon("realizar este compilador", 750, 17));
        iniciarRenglon(new Renglon("a mi madre por parir ", 770, 17));
        iniciarRenglon(new Renglon("a un hijo tan riata como", 790, 17));
        iniciarRenglon(new Renglon("yo, y por ultimo gracias", 810, 17));
        iniciarRenglon(new Renglon("a mi por ser tan vergas.", 830, 17));
    }
    private void iniciarRenglon(Renglon renglon){
        renglones.add(renglon);
        panel.add(renglon.getRenglon());
    }
}











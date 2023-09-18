package interfaz;

import interfaz.subventanas.ayuda.Declaracion;
import interprete.Interprete;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu.Separator;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import org.fife.ui.rsyntaxtextarea.AbstractTokenMakerFactory;
import org.fife.ui.rsyntaxtextarea.CodeTemplateManager;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.Token;
import org.fife.ui.rsyntaxtextarea.TokenMakerFactory;
import org.fife.ui.rsyntaxtextarea.templates.CodeTemplate;
import org.fife.ui.rsyntaxtextarea.templates.StaticCodeTemplate;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.jdesktop.swingx.prompt.PromptSupport;
import com.mxrck.autocompleter.TextAutoCompleter;
import interfaz.acercade.AcercaDe;
import interfaz.subventanas.Base;
import interfaz.subventanas.ayuda.*;
import interfaz.subventanas.teminosycondiciones.TerminosYCondiciones;
import java.awt.AWTException;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.util.ArrayList;
import java.util.List;  
import javax.imageio.ImageIO;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import utileria.CargarArchivo;
import utileria.SalvarArchivo;

public class Interfaz extends JFrame{
    private final List<Base>ayudas = new ArrayList<>();    
    private final String ruta = new File("").getAbsolutePath();
    private boolean ejecutando = false;
    private Interprete interprete=null;
    private String mensajes="";
    private long ejecucion=0;
    private String entrada="";
    private String rutaArchivo="";
    private String textoGuardado="";
    private final String titulo = "RBasic";
    private final TerminosYCondiciones terminos = new TerminosYCondiciones(this);
    public String getEntrara(){
        return entrada;
    }
    public void limpiarEntrada(){
        entrada="";
    }
    public void detener(boolean interrupcion){
        mDebug_ejecutar.setText("Ejecutar");
        mEjecutar.setIcon(new ImageIcon(ruta.concat("/img/play.png"))); 
        String tipo = interrupcion?"interrupcion":"flujo";
        setMensaje("<div class='".concat(tipo).concat("'> Terminado: ").concat(String.valueOf((System.currentTimeMillis()-ejecucion)/1000)).concat(" segundos de ejecucion </div>"));
        ejecucion=0;
        mensajes="";
        tComando.setText("");
        tCodigo.requestFocus();
        setEditable(false);
        ejecutando=false;
    }
    public void clearConsola(){
        mensajes="";
        setMensaje("<div class='flujo'>Limpieza</div>");
    }
    public void setMensaje(String mensaje){
        mensajes=mensajes.concat(mensaje);
        tConsola.setText(mensajes);
        tConsola.setCaretPosition(tConsola.getDocument().getLength());
    }
    private void mEjecutar_click(){
        ejecutando=!ejecutando;
        if(ejecutando){
            interprete = new Interprete(this);
            mDebug_ejecutar.setText("Detener");
            mEjecutar.setIcon(new ImageIcon(ruta.concat("/img/stop.png"))); 
            setMensaje("<div class='flujo'>Iniciando</div>");
            setEditable(true);
            tComando.requestFocus();
            interprete.setText(tCodigo.getText());
            ejecucion=System.currentTimeMillis();
            interprete.start();
        }else{
            interprete.stop();
            detener(true);
        }
    }
    private void busqueda_keyTyped_enter(String busqueda){
        Busqueda iBusqueda = new Busqueda(busqueda,this);
        iBusqueda.showMensaje();
    }
    private void boton_click(){
        if(!bComando.isEnabled())return;
        if(interprete.isEspera()){
            entrada=tComando.getText();
        }
        tComando.setText("");
    }
    private void mArchivo_salir_click(){
        if(!isGuardado())deseaGuardarCambios();
        if (JOptionPane.showConfirmDialog(this, "¿Desea realmente salir del sistema?", "Salir del sistema", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)System.exit(0);
    }    
    private void mArchivo_nuevo_click(){
        if(!isGuardado())deseaGuardarCambios();
        if(interprete!=null && interprete.isAlive()){
            interprete.stop();
            detener(true);
        }
        setTitle(titulo);
        tConsola.setText("");
        textoGuardado="";
        tCodigo.setText("");
        rutaArchivo="";
    }
    private void mAyuda_ahorcado(){
        cargarEjemplo(new File(ruta.concat("/ejemp/AHORCADO.rbasic")));
    }
    private void mAyuda_animacion(){
        cargarEjemplo(new File(ruta.concat("/ejemp/ANIMACION.rbasic")));
    }   
    private void mAyuda_asignacion(){
        cargarEjemplo(new File(ruta.concat("/ejemp/ASIGNACION.rbasic")));
    }
    private void mAyuda_cicloDe(){
        cargarEjemplo(new File(ruta.concat("/ejemp/CICLODE.rbasic")));
    }
    private void mAyuda_condicionales(){
        cargarEjemplo(new File(ruta.concat("/ejemp/CONDICIONALES.rbasic")));
    }
    private void mAyuda_imprimir(){
        cargarEjemplo(new File(ruta.concat("/ejemp/IMPRIMIR.rbasic")));
    }
    private void mAyuda_recibe(){
        cargarEjemplo(new File(ruta.concat("/ejemp/RECIBE.rbasic")));
    }
    private void mAyuda_cicloMientras(){
        cargarEjemplo(new File(ruta.concat("/ejemp/MIENTRAS.rbasic")));
    }
    private void mAyuda_accion(){
        cargarEjemplo(new File(ruta.concat("/ejemp/ACCION.rbasic")));
    }
    private void mAyuda_funcion(){
        cargarEjemplo(new File(ruta.concat("/ejemp/FUNCION.rbasic")));
    }
    private void cargarEjemplo(File archivo){
        if(!isGuardado())deseaGuardarCambios();
        CargarArchivo cargar = new CargarArchivo();
        if(cargar.getArchivo(this, archivo)){
            setTitle(titulo.concat(" - ".concat(cargar.getNombre())));
            textoGuardado=cargar.getTexto();
            tCodigo.setText(textoGuardado);
            rutaArchivo="";
        }
    }
    private void mArchivo_abrir_click(){
        if(!isGuardado())deseaGuardarCambios();
        CargarArchivo cargar = new CargarArchivo();
        if(cargar.getArchivo(this)){
            setTitle(titulo.concat(" - ").concat(cargar.getNombre()));
            textoGuardado=cargar.getTexto();
            tCodigo.setText(textoGuardado);
            rutaArchivo=cargar.toString();
        }
    }
    private void mArchivo_guardar_click(){
        SalvarArchivo salvar = new SalvarArchivo(rutaArchivo, this);
        try {
            if(!salvar.salvar(tCodigo.getText())){
                JOptionPane.showMessageDialog(this,"No se guardo el archivo","¡No guardado!",JOptionPane.WARNING_MESSAGE);
            }else{
                setTitle(titulo.concat(" - ").concat(salvar.getNombre()));
                textoGuardado=tCodigo.getText();
                rutaArchivo=salvar.toString();
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,"Error: ".concat(ex.getMessage()),"¡Error al guardar!",JOptionPane.ERROR_MESSAGE);
        }
    }
    private void mAyuda_acercaDe_click(){
        new AcercaDe(this).showMensaje();
    }
    private void mAyuda_terminosYCondiciones_click(){
        terminos.showMensaje();
    }
    //Fin de eventos
    //Configuracion del Frame
    public Interfaz(){
        //1
        ayudas.add(new Declaracion(this));
        ayudas.add(new Asignacion(this));
        ayudas.add(new Funciones(this));
        ayudas.add(new Condicional(this));
        ayudas.add(new Ciclos(this));
        ayudas.add(new Accion(this));
        ayudas.add(new Funcion(this));
        iniciarComponentes();
    }
    private void iniciarComponentes(){
        //Creamos el panel y lo configuramos
        JPanel panel = new JPanel();
        this.getContentPane().add(panel);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        //iniciamos los componenetes principales de la aplicacion
        //y los ingresamos al panel
        JPanel interno = new JPanel();
        interno.setLayout(new BoxLayout(interno, BoxLayout.X_AXIS));
        bComando = new JButton("Ingresar");
        bComando.addActionListener((java.awt.event.ActionEvent evt) -> {
            boton_click();
        });
        tComando = new JTextField();
        tComando.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_ENTER)boton_click();
            }
        });
        interno.add(tComando);
        interno.add(bComando);
        interno.setMaximumSize(new Dimension(2000, 30));
        interno.setMinimumSize(new Dimension(0, 30));
        tCodigo=new RSyntaxTextArea(10,100);
        RTextScrollPane sCodigo = new RTextScrollPane(tCodigo);
        sCodigo.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        tConsola = new JEditorPane();
        tConsola.setContentType("text/html");
        HTMLEditorKit kit = new HTMLEditorKit();
        tConsola.setEditorKit(kit);
        StyleSheet styleSheet = kit.getStyleSheet();
        styleSheet.addRule("body {font-family: \"Estrangelo Edessa\", Sans-serif}");
        styleSheet.addRule(".peticion{color:rgb(39,168,207);}");
        styleSheet.addRule(".flujo{font-family: \"Aharoni\";color:rgb(136,184,97);}");
        styleSheet.addRule(".error{font-family: \"Aharoni\";color:rgb(186,79,43);}");
        styleSheet.addRule(".interrupcion{font-family: \"Aharoni\";color:rgb(222,153,58);}");
        tConsola.setDocument(kit.createDefaultDocument());
        JScrollPane sConsola = new JScrollPane(tConsola);
        sConsola.setMaximumSize(new Dimension(2000, 200));
        sConsola.setMinimumSize(new Dimension(0, 200));
        sConsola.setPreferredSize(new Dimension(0, 200));
        panel.add(sCodigo);
        panel.add(sConsola);
        panel.add(interno);
        //Generacion de los menus
        generarMenus();
        //Configuracion del tCodigo
        configurarRS();
        //Configuraciones del Frame
        this.setTitle(titulo);
        this.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {
                mArchivo_salir_click();
            }
        });
        this.setIconImage(new ImageIcon(ruta.concat("/img/Logo.png")).getImage());
        tConsola.setEditable(false);
        setEditable(false);
        this.setSize(new Dimension(800, 600));
        this.setMinimumSize(new Dimension(800, 600));
        this.setLocationRelativeTo(null);
        setDefaultLookAndFeelDecorated(true);
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            System.out.println(ex.getMessage());
        }
        tCodigo.requestFocus();
        pack();
    }
    private ImageIcon getIconoMenu(String relativa){
        try {
            return new ImageIcon(ImageIO.read(new File(ruta.concat(relativa))).getScaledInstance(19, 19, Image.SCALE_DEFAULT));
        } catch (IOException ex) {
            return new ImageIcon();
        }
    }
    private void generarMenus(){
        JMenuBar menu = new JMenuBar();
        JMenu mArchivo = new JMenu("Archivo");
        JMenuItem mArchivo_salir = new JMenuItem("Salir", getIconoMenu("/img/salir.png"));
        mArchivo_salir.addActionListener((ActionEvent evt) -> {
            mArchivo_salir_click();
        });
        mArchivo_salir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_MASK));
        JMenuItem mArchivo_abrir = new JMenuItem("Abrir", getIconoMenu("/img/abrir.png"));
        mArchivo_abrir.addActionListener((ActionEvent evt) -> {
            mArchivo_abrir_click();
        });
        mArchivo_abrir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.ALT_MASK));
        JMenuItem mArchivo_nuevo = new JMenuItem("Nuevo", getIconoMenu("/img/nuevo.png"));
        mArchivo_nuevo.addActionListener((ActionEvent evt) -> {
            mArchivo_nuevo_click();
        });
        mArchivo_nuevo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
        JMenuItem mArchivo_guardar = new JMenuItem("Guardar", getIconoMenu("/img/guardar.png"));
        mArchivo_guardar.addActionListener((ActionEvent evt) -> {
            mArchivo_guardar_click();
        });
        mArchivo_guardar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_MASK));
        mArchivo.add(mArchivo_nuevo);
        mArchivo.add(mArchivo_abrir);
        mArchivo.add(mArchivo_guardar);
        mArchivo.add(new Separator());
        mArchivo.add(mArchivo_salir);
        //Compilacion
        JMenu mDebug = new JMenu("Debug");
        mDebug_ejecutar = new JMenuItem("Ejecutar");
        mDebug_ejecutar.addActionListener((ActionEvent evt)->{
            mEjecutar_click();
        });
        mDebug_ejecutar.setAccelerator(KeyStroke.getKeyStroke("F5"));
        mDebug.add(mDebug_ejecutar);
        //Ayuda
        JMenu mAyuda = new JMenu("Ayuda");
        JMenuItem mAyuda_sintaxis = new JMenuItem("Sintaxis", getIconoMenu("/img/documentacion.png"));
        mAyuda_sintaxis.addActionListener((ActionEvent evt)->{
            busqueda_keyTyped_enter("");
        });
        mAyuda_sintaxis.setAccelerator(KeyStroke.getKeyStroke("F1"));
        JMenu mAyuda_Ejemplos = new JMenu("Ejemplos");
        JMenuItem mAyuda_Ejemplos_Animacion = new JMenuItem("Animacion");
        mAyuda_Ejemplos_Animacion.addActionListener((ActionEvent evt)->{
            mAyuda_animacion();
        });
        JMenuItem mAyuda_Ejemplos_Asignacion = new JMenuItem("Asignacion");
        mAyuda_Ejemplos_Asignacion.addActionListener((ActionEvent evt)->{
            mAyuda_asignacion();
        });
        JMenuItem mAyuda_Ejemplos_CicloDe = new JMenuItem("Ciclo DE");
        mAyuda_Ejemplos_CicloDe.addActionListener((ActionEvent evt)->{
            mAyuda_cicloDe();
        });
        JMenuItem mAyuda_Ejemplos_CicloMientras = new JMenuItem("Ciclo MIENTRAS");
        mAyuda_Ejemplos_CicloMientras.addActionListener((ActionEvent evt)->{
            mAyuda_cicloMientras();
        });
        JMenuItem mAyuda_Ejemplos_Condicionales = new JMenuItem("Condicionales");
        mAyuda_Ejemplos_Condicionales.addActionListener((ActionEvent evt)->{
            mAyuda_condicionales();
        });
        JMenuItem mAyuda_Ejemplos_Imprimir = new JMenuItem("Imprimir");
        mAyuda_Ejemplos_Imprimir.addActionListener((ActionEvent evt)->{
            mAyuda_imprimir();
        });
        JMenuItem mAyuda_Ejemplos_Ahorcado = new JMenuItem("Ahorcado");
        mAyuda_Ejemplos_Ahorcado.addActionListener((ActionEvent evt)->{
            mAyuda_ahorcado();
        });
        JMenuItem mAyuda_Ejemplos_Recibe = new JMenuItem("Recibe");
        mAyuda_Ejemplos_Recibe.addActionListener((ActionEvent evt)->{
            mAyuda_recibe();
        });
        JMenuItem mAyuda_Ejemplos_Accion = new JMenuItem("Accion");
        mAyuda_Ejemplos_Accion.addActionListener((ActionEvent evt)->{
            mAyuda_accion();
        });
        JMenuItem mAyuda_Ejemplos_Funcion = new JMenuItem("Funcion");
        mAyuda_Ejemplos_Funcion.addActionListener((ActionEvent evt)->{
            mAyuda_funcion();
        });
        mAyuda_Ejemplos.add(mAyuda_Ejemplos_Imprimir);
        mAyuda_Ejemplos.add(mAyuda_Ejemplos_Asignacion);
        mAyuda_Ejemplos.add(mAyuda_Ejemplos_Recibe);
        mAyuda_Ejemplos.add(mAyuda_Ejemplos_Condicionales);
        mAyuda_Ejemplos.add(mAyuda_Ejemplos_CicloDe);
        mAyuda_Ejemplos.add(mAyuda_Ejemplos_CicloMientras);
        mAyuda_Ejemplos.add(mAyuda_Ejemplos_Accion);
        mAyuda_Ejemplos.add(mAyuda_Ejemplos_Funcion);
        mAyuda_Ejemplos.add(mAyuda_Ejemplos_Animacion);
        mAyuda_Ejemplos.add(mAyuda_Ejemplos_Ahorcado);
        JMenuItem mAyuda_acercaDe = new JMenuItem("Acerca de");
        mAyuda_acercaDe.addActionListener((ActionEvent evt)->{
            mAyuda_acercaDe_click();
        });
        JMenuItem mAyuda_terminosYCondiciones = new JMenuItem("Terminos y condiciones");
        mAyuda_terminosYCondiciones.addActionListener((ActionEvent evt)->{
            mAyuda_terminosYCondiciones_click();
        });
        mAyuda.add(mAyuda_sintaxis);
        mAyuda.add(mAyuda_Ejemplos);
        mAyuda.add(new Separator());
        mAyuda.add(mAyuda_acercaDe);
        mAyuda.add(mAyuda_terminosYCondiciones);
        //Ejecutar
        mEjecutar = new JMenuItem("F5");
        mEjecutar.setIcon(new ImageIcon(ruta.concat("/img/play.png")));
        mEjecutar.addActionListener((ActionEvent evt) -> {
            mEjecutar_click();
        });
        mEjecutar.setMaximumSize(new Dimension(95, 30));
        mEjecutar.setMinimumSize(new Dimension(95, 30));
        //Busqueda
        tBusqueda = new JTextField();
        PromptSupport.setPrompt("Buscar ayuda...    ",tBusqueda);
        PromptSupport.setForeground(Color.gray, tBusqueda);
        TextAutoCompleter textAutoComplete = new TextAutoCompleter(tBusqueda);
        textAutoComplete.addItems(getTags());
        tBusqueda.setMaximumSize(new Dimension(400, 30));
        tBusqueda.setMinimumSize(new Dimension(400, 30));
        tBusqueda.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_ENTER){
                    busqueda_keyTyped_enter(tBusqueda.getText().toLowerCase());
                    tBusqueda.setText("");
                }               
            }
        });
        //Fin de creacion de elementos
        menu.add(mArchivo);
        menu.add(mDebug);
        menu.add(mAyuda);
        menu.add(mEjecutar);
        menu.add(Box.createHorizontalGlue());
        menu.add(tBusqueda);
        setJMenuBar(menu);
        //Empezar creacion de tray
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }
        PopupMenu popup = new PopupMenu();
        TrayIcon trayIcon = new TrayIcon(new ImageIcon(ruta.concat("/img/Logo.png")).getImage());
        trayIcon.setImageAutoSize(true);
        SystemTray tray = SystemTray.getSystemTray();
        Menu traySubMenu = new Menu("Archivo");
        MenuItem traySubMenu_nuevo = new MenuItem("Nuevo");
        traySubMenu_nuevo.addActionListener((ActionEvent e) -> {
            mArchivo_nuevo_click();
        });
        MenuItem traySubMenu_abrir = new MenuItem("Abrir");
        traySubMenu_abrir.addActionListener((ActionEvent e) -> {
            mArchivo_abrir_click();
        });
        MenuItem traySubMenu_guardar = new MenuItem("Guardar");
        traySubMenu_guardar.addActionListener((ActionEvent e) -> {
            mArchivo_guardar_click();
        });
        MenuItem trayDocumentacion = new MenuItem("Documentacion");
        trayDocumentacion.addActionListener((ActionEvent e) -> {
            busqueda_keyTyped_enter("");
        });
        MenuItem traySalir = new MenuItem("Salir");
        traySalir.addActionListener((ActionEvent e) -> {
            mArchivo_salir_click();
        });
        traySubMenu.add(traySubMenu_nuevo);
        traySubMenu.add(traySubMenu_abrir);
        traySubMenu.add(traySubMenu_guardar);
        popup.add(traySubMenu);
        popup.addSeparator();
        popup.add(trayDocumentacion);
        popup.addSeparator();
        popup.add(traySalir);
        trayIcon.setPopupMenu(popup);
        trayIcon.setToolTip("RBasic");
        try {
            tray.add(trayIcon);
            trayIcon.displayMessage("Se inicio el interprete", "RBasic", TrayIcon.MessageType.INFO);
        } catch (AWTException e) {
            System.out.println("Problemas al iniciar el tray");
        }
    }
    private void configurarRS(){
        //Ingresamos el marcado para el lenguaje de RBasic
        AbstractTokenMakerFactory atmf = (AbstractTokenMakerFactory)TokenMakerFactory.getDefaultInstance();
        atmf.putMapping("text/rbasic", "utileria.RBasic");
        tCodigo.setSyntaxEditingStyle("text/rbasic");
        tCodigo.setCodeFoldingEnabled(true);
        //Cambiamos el estilo de algunos elementos
        tCodigo.getSyntaxScheme().getStyle(Token.LITERAL_STRING_DOUBLE_QUOTE).foreground = new Color(199, 132, 8);
        tCodigo.getSyntaxScheme().getStyle(Token.RESERVED_WORD_2).foreground = new Color(179, 47, 27);
        tCodigo.getSyntaxScheme().getStyle(Token.FUNCTION).foreground = new Color(39,168,207);
        tCodigo.getSyntaxScheme().getStyle(Token.COMMENT_EOL).foreground = Color.GRAY;     
        //Ingresamos los eventos de autocompletar
        RSyntaxTextArea.setTemplatesEnabled(true);
        CodeTemplateManager completador = RSyntaxTextArea.getCodeTemplateManager();
        CodeTemplate ct = new StaticCodeTemplate("IMP", "IMPRIME \"", "\"");
        completador.addTemplate(ct);
        ct = new StaticCodeTemplate("REC", "RECIBE \"", "\"");
        completador.addTemplate(ct);
        ct = new StaticCodeTemplate("DE", "DE I = ", " HASTA 10 PASO I + 1 ENTONCES\n\nTDE");
        completador.addTemplate(ct);
        ct = new StaticCodeTemplate("MIE", "MIENTRAS ", " ENTONCES \n\nTMIENTRAS");
        completador.addTemplate(ct);
        ct = new StaticCodeTemplate("LIM", "LIMPIAR\n", "");
        completador.addTemplate(ct);
        ct = new StaticCodeTemplate("ESP", "ESPERAR ", "");
        completador.addTemplate(ct);
        ct = new StaticCodeTemplate("SI", "SI ", " ENTONCES\n\n\n\nTSI");
        completador.addTemplate(ct);
        ct = new StaticCodeTemplate("SIN", "SINO ", " ENTONCES \n");
        completador.addTemplate(ct);
        ct = new StaticCodeTemplate("ENT", "ENTONCES\n", "");
        completador.addTemplate(ct);
        ct = new StaticCodeTemplate("ACC", "ACCION ", " () ENTONCES\n\nTACCION");
        completador.addTemplate(ct);
        ct = new StaticCodeTemplate("T", "TEXTO", "");
        completador.addTemplate(ct);
        ct = new StaticCodeTemplate("C", "CARACTER", "");
        completador.addTemplate(ct);
        ct = new StaticCodeTemplate("E", "ENTERO", "");
        completador.addTemplate(ct);
        ct = new StaticCodeTemplate("D", "DECIMAL", "");
        completador.addTemplate(ct);
        ct = new StaticCodeTemplate("B", "BOOLEANO", "");
        completador.addTemplate(ct);
        ct = new StaticCodeTemplate("ET", "ES TEXTO", "");
        completador.addTemplate(ct);
        ct = new StaticCodeTemplate("EC", "ES CARACTER", "");
        completador.addTemplate(ct);
        ct = new StaticCodeTemplate("EE", "ES ENTERO", "");
        completador.addTemplate(ct);
        ct = new StaticCodeTemplate("ED", "ES DECIMAL", "");
        completador.addTemplate(ct);
        ct = new StaticCodeTemplate("EB", "ES BOOLEANO", "");
        completador.addTemplate(ct);
        ct = new StaticCodeTemplate("RT", "R ", " ES TEXTO");
        completador.addTemplate(ct);
        ct = new StaticCodeTemplate("RC", "R ", " ES CARACTER");
        completador.addTemplate(ct);
        ct = new StaticCodeTemplate("RE", "R ", " ES ENTERO");
        completador.addTemplate(ct);
        ct = new StaticCodeTemplate("RD", "R ", " ES DECIMAL");
        completador.addTemplate(ct);
        ct = new StaticCodeTemplate("RB", "R ", " ES BOOLEANO");
        completador.addTemplate(ct);
        ct = new StaticCodeTemplate("DIF", "DIFERENTE ", "");
        completador.addTemplate(ct);
        ct = new StaticCodeTemplate("EQU", "EQUIVALE ", "");
        completador.addTemplate(ct);
        ct = new StaticCodeTemplate("ME", "MENORQUE ", "");
        completador.addTemplate(ct);
        ct = new StaticCodeTemplate("MEI", "MENOROIGUALQUE ", "");
        completador.addTemplate(ct);
        ct = new StaticCodeTemplate("MA", "MAYORQUE ", "");
        completador.addTemplate(ct);
        ct = new StaticCodeTemplate("MAI", "MAYOROIGUALQUE ", "");
        completador.addTemplate(ct);
        ct = new StaticCodeTemplate("VER", "VERDADERO", "");
        completador.addTemplate(ct);
        ct = new StaticCodeTemplate("FAL", "FALSO", "");
        completador.addTemplate(ct);
        ct = new StaticCodeTemplate("OPT", "OPERACION ", " () ES TEXTO\n\n\tRETORNA \"\"\nTOPERACION");
        completador.addTemplate(ct);
        ct = new StaticCodeTemplate("OPC", "OPERACION ", " () ES CARACTER\n\n\tRETORNA ' '\nTOPERACION");
        completador.addTemplate(ct);
        ct = new StaticCodeTemplate("OPE", "OPERACION ", " () ES ENTERO\n\n\tRETORNA 0\nTOPERACION");
        completador.addTemplate(ct);
        ct = new StaticCodeTemplate("OPD", "OPERACION ", " () ES DECIMAL\n\n\tRETORNA 0.0\nTOPERACION");
        completador.addTemplate(ct);
        ct = new StaticCodeTemplate("OPB", "OPERACION ", " () ES BOOLEANO\n\n\tRETORNA VERDADERO\nTOPERACION");
        completador.addTemplate(ct);
    }
    private void setEditable(boolean editable){
        tComando.setEditable(editable);
        bComando.setEnabled(editable);
    }
    private boolean isGuardado(){
        return tCodigo.getText().equals(textoGuardado);
    }
    private void deseaGuardarCambios(){
        if(JOptionPane.showConfirmDialog(this, "¿Desea guardar cambios antes de continuar?","¡Archivo no guardado!",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)mArchivo_guardar_click();
    }
    public String getRuta(){
        return ruta;
    }
    public List<Base> getAyudas(){
        return ayudas;
    }
    private ArrayList getTags(){
        ArrayList lista = new ArrayList();
        ayudas.stream().forEach((ayuda) -> {
            for (Object tag : ayuda.getTags()) {
                if(!lista.stream().anyMatch((lista1) -> (lista1.equals(tag))))lista.add(tag);
            }
        });
        return lista;
    }
    @SuppressWarnings("new-object-ignored")
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Interfaz().setVisible(true);
        });
    }
    private JButton bComando;
    private JEditorPane tConsola;
    private JTextField tComando;
    private JTextField tBusqueda;
    private RSyntaxTextArea tCodigo;
    private JMenuItem mDebug_ejecutar;
    private JMenuItem mEjecutar;
}
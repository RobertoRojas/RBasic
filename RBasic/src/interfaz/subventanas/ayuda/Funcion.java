package interfaz.subventanas.ayuda;

import interfaz.subventanas.Base;
import diccionario.TPReservada;
import interfaz.Interfaz;

public class Funcion extends Base{
    public Funcion(Interfaz interfaz) {
        super(new Object[]{"funcion","return","retorna","operacion","variable","asignacion"}, 
                "<div class=\"titulo\"style=\"padding-left:180px;\">Función</div><br/><div style=\"padding-left:15px;\">&#09;En RBasic se pueden hacer funciones que retornen un valor en particular, esto sirve entre otras cosas para hacer operaciones aritméticas o de cualquier otro índole, haciendo que el código sea más eficiente, la sintaxis es la siguiente</div><br/><div class=\"seudoCodigo\" style=\"padding-left:15px;font-size:10px;\"><div>["
                .concat(TPReservada.OPERACION.toString())
                .concat("] {<span style=\"font-style:italic;\">nombre funcion</span>}[(]{<span style=\"font-style:italic;\">parametros</span>}[)] [")
                .concat(TPReservada.ES.toString())
                .concat("] [")
                .concat(TPReservada.TEXTO.toString())
                .concat("|")
                .concat(TPReservada.CARACTER.toString())
                .concat("|")
                .concat(TPReservada.ENTERO.toString())
                .concat("|")
                .concat(TPReservada.DECIMAL.toString())
                .concat("|")
                .concat(TPReservada.BOOLEANO.toString())
                .concat("]</div><br/><div>[")
                .concat(TPReservada.TOPERACION.toString())
                .concat("]</div></div><br/><div style=\"padding-left:15px;\">&#09;Funciona en general igual que el método, pero este tiene que ser asignado a una variable necesariamente. Otra cosa a considerar es que el método cuando se le asigna a una variable tiene que ser la única operación existente en la igualdad. Ejemplo:</div><br/><div class=\"caja\"><div class=\"codigo1\"><span class=\"palabra\">")
                .concat(TPReservada.OPERACION.toString())
                .concat("</span> CUAD(A <span class=\"palabra\">")
                .concat(TPReservada.ES.toString())
                .concat("</span> <span class=\"dato\">")
                .concat(TPReservada.ENTERO.toString())
                .concat("</span>) <span class=\"palabra\">")
                .concat(TPReservada.ES.toString())
                .concat("</span> <span class=\"dato\">")
                .concat(TPReservada.ENTERO.toString())
                .concat("</span></div><div class=\"codigo2\">&#09;A = A * A</div><div class=\"codigo1\">&#09;<span class=\"palabra\">")
                .concat(TPReservada.RETORNA.toString())
                .concat("</span> A</div><div class=\"codigo2\"><span class=\"palabra\">")
                .concat(TPReservada.TOPERACION.toString())
                .concat("</span></div><div class=\"codigo1\"><span class=\"palabra\">")
                .concat(TPReservada.R.toString())
                .concat("</span> a <span class=\"palabra\">")
                .concat(TPReservada.ES.toString())
                .concat("</span> <span class=\"dato\">")
                .concat(TPReservada.ENTERO.toString())
                .concat("</span></div><div class=\"codigo2\">a = CUAD(2)</div><div class=\"codigo1\"><span class=\"funcion\">")
                .concat(TPReservada.IMPRIME.toString())
                .concat("</span> <span class=\"cadena\">\"El resultado es \"</span> + a</div></div><br/>"), "Funcion", interfaz);
    }
}
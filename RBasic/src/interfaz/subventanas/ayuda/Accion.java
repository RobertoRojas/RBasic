package interfaz.subventanas.ayuda;

import interfaz.subventanas.Base;
import diccionario.TPReservada;
import interfaz.Interfaz;

public class Accion extends Base{
    public Accion(Interfaz interfaz) {
        super(new Object[]{"metodo","void","accion","proceso","imprime","print","variable"}, 
                "<div class=\"titulo\"style=\"padding-left:188px;\">Accion</div><br/><div style=\"padding-left:15px;\">&#09;Puedes utilizar método para hacer un código en particular se repita n cantidad de veces. Como mínimo tiene que tener un argumento.</div><br/><div class=\"seudoCodigo\" style=\"padding-left:15px;\"><div>["
                .concat(TPReservada.ACCION.toString())
                .concat("]  {<span style=\"font-style:italic;\">nombre método</span>}[(]{<span style=\"font-style:italic;\">parametros</span>}[)][")
                .concat(TPReservada.ENTONCES.toString())
                .concat("]</div><br/><div>[")
                .concat(TPReservada.TACCION.toString())
                .concat("]</div></div><br/><div style=\"padding-left:15px;\">&#09;Debes ingresar un nombre al método cuidando que no se repita con otro o una variable, debido a que puede causar problemas. Además los parámetros deben estar el nombre de la variable para su uso interno y la nomenclatura de declaración sin la [R], por ejemplo:</div><br/><div class=\"caja\"><div class=\"codigo1\"><span class=\"palabra\">")
                .concat(TPReservada.ACCION.toString())
                .concat("</span> SHOWMENSAJE (M <span class=\"palabra\">")
                .concat(TPReservada.ES.toString())
                .concat("</span> <span class=\"dato\">")
                .concat(TPReservada.TEXTO.toString())
                .concat("</span>)<span class=\"palabra\">")
                .concat(TPReservada.ENTONCES.toString())
                .concat("</span></div><div class=\"codigo2\">&#09;<span class=\"funcion\">")
                .concat(TPReservada.IMPRIME.toString())
                .concat("</span> <span class=\"cadena\">\"METODO \"</span> + M</div><div class=\"codigo1\"><span class=\"palabra\">")
                .concat(TPReservada.TACCION.toString())
                .concat("</span></div><div class=\"codigo2\">SHOWMENSAJE(<span class=\"cadena\">\"MENSAJE 1\"</span>)</div><div class=\"codigo1\">SHOWMENSAJE(<span class=\"cadena\">\"MENSAJE 2\"</span>)</div><div class=\"codigo2\">SHOWMENSAJE(<span class=\"cadena\">\"MENSAJE 3\"</span>)</div></div><br/>"), 
                "Metodo", interfaz);
    }
}

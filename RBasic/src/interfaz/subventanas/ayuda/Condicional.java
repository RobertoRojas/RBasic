package interfaz.subventanas.ayuda;

import interfaz.subventanas.Base;
import diccionario.TPReservada;
import interfaz.Interfaz;

public class Condicional extends Base{

    public Condicional(Interfaz interfaz) {
        super(new Object[]{"condicionales","condicion","if","si","else","no","tsi","verdadero","true","falso","false","booleano","boolean"}, 
                "<div class=\"titulo\"style=\"padding-left:150px;\">Condicionales</div><br/><div style=\"padding-left:10px;\">&#09;Hay momentos en los que es necesario saber si una o varias variables cumplen con cierta condición, para ello nos sirven los saltos condicionales, en ellos podemos hacer distintas funciones dependiendo de si se cumple la condición, su estructura es la siguiente:</div><br/><div class=\"seudoCodigo\" style=\"padding-left:85px;\"><div>["
                .concat(TPReservada.SI.toString())
                .concat("] {<span style=\"font-style:italic;\">condicion</span>} [")
                .concat(TPReservada.ENTONCES.toString())
                .concat("] <span class=\"comentario\">$campo obligatorio</span></div><div>[")
                .concat(TPReservada.SINO.toString())
                .concat("] {<span style=\"font-style:italic;\">condicion</span>} [")
                .concat(TPReservada.ENTONCES.toString())
                .concat("] <span class=\"comentario\">$opcional, puede ingresarse varias veces</span></div><div>[")
                .concat(TPReservada.NO.toString())
                .concat("] <span class=\"comentario\">$Opcional, solo se puede ingresar una vez</span></div><div>[")
                .concat(TPReservada.TSI.toString())
                .concat( "] <span class=\"comentario\">$campo obligatorio</span></div></div><br/><div style=\"padding-left:10px;\">&#09;Si ingresas dos o más condiciones repetidas, se va a ejecutar la primera que encuentra, tiene un [")
                .concat(TPReservada.NO.toString())
                .concat("], entonces en caso de que ninguna condición se cumpla se va a ejecutar el código del bloque [")
                .concat(TPReservada.NO.toString())
                .concat("]. Las condiciones se rigen con las reglas de asignación de una variable BOOLEANO, por ejemplo:</div><br/><div class=\"caja\"><div class=\"codigo1\"><span class=\"palabra\">")
                .concat(TPReservada.R.toString())
                .concat("</span> CONA <span class=\"palabra\">")
                .concat(TPReservada.ES.toString())
                .concat("</span> <span class=\"dato\">")
                .concat(TPReservada.BOOLEANO.toString())
                .concat("</span></div><div class=\"codigo2\">CONA = <span class=\"booleano\">")
                .concat(TPReservada.FALSO.toString())
                .concat("</span></div><div class=\"codigo1\"><span class=\"palabra\">")
                .concat(TPReservada.R.toString())
                .concat("</span> CONB <span class=\"palabra\">")
                .concat(TPReservada.ES.toString())
                .concat("</span> <span class=\"dato\">")
                .concat(TPReservada.BOOLEANO.toString())
                .concat("</span></div><div class=\"codigo2\">CONB = <span class=\"booleano\">")
                .concat(TPReservada.VERDADERO.toString())
                .concat("</span></div><div class=\"codigo1\"><span class=\"palabra\">")
                .concat(TPReservada.SI.toString())
                .concat("</span> CONA <span class=\"palabra\">")
                .concat(TPReservada.Y.toString())
                .concat("</span> <span class=\"booleano\">")
                .concat(TPReservada.VERDADERO.toString())
                .concat("</span> <span class=\"palabra\">")
                .concat(TPReservada.ENTONCES.toString())
                .concat("</span></div><div class=\"codigo2\"><span class=\"funcion\">&#09;")
                .concat(TPReservada.IMPRIME.toString())
                .concat("</span> <span class=\"cadena\">\"A\"</span></div><div class=\"codigo1\"><span class=\"palabra\">")
                .concat(TPReservada.SINO.toString())
                .concat("</span> CONB<span class=\"palabra\"> ")
                .concat(TPReservada.DIFERENTE.toString())
                .concat("</span> CONA <span class=\"palabra\">")
                .concat(TPReservada.O.toString())
                .concat("</span> <span class=\"booleano\">")
                .concat(TPReservada.FALSO.toString())
                .concat("</span> <span class=\"palabra\">")
                .concat(TPReservada.ENTONCES.toString())
                .concat("</span></div><div class=\"codigo2\"><span class=\"funcion\">&#09;")
                .concat(TPReservada.IMPRIME.toString())
                .concat("</span> <span class=\"cadena\">\"B\"</span></div><div class=\"codigo1\"><span class=\"palabra\">")
                .concat(TPReservada.NO.toString())
                .concat("</span></div><div class=\"codigo2\"><span class=\"funcion\">&#09;")
                .concat(TPReservada.IMPRIME.toString())
                .concat("</span> <span class=\"cadena\">\"NINGUNO\"</span></div><div class=\"codigo1\"><span class=\"palabra\">")
                .concat(TPReservada.TSI.toString())
                .concat("</span></div></div><br/>"), "Condicionales", interfaz);
    }
}

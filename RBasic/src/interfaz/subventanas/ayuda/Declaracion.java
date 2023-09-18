package interfaz.subventanas.ayuda;

import interfaz.subventanas.Base;
import diccionario.TPReservada;
import interfaz.Interfaz;

public class Declaracion extends Base{

    public Declaracion(Interfaz interfaz) {
        super(new Object[]{"declaracion","variable","variables","string","texto","booleano","boolean","double","double","char","caracter","entero","int","datos"}, 
                "<div class=\"titulo\" style=\"padding-left:70px;\">Declaracion de variables</div><br/><div class=\"seudoCodigo\" style=\"font-size: 11px;padding-left:39px;\">["
                .concat(TPReservada.R.toString())
                .concat("] { <span style=\"font-style:italic;\">nombre de la variable</span> } [")
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
                .concat("]</div><br/><div style=\"padding-left:16px;\">&#09;El nombre de la variable tiene que empezar con una letra y lo siguiente puede ser letra o número. Ejemplos:</div><br/><div class=\"caja\"><div class=\"codigo1\"><span class=\"palabra\">")
                .concat(TPReservada.R.toString())
                .concat("</span> texto <span class=\"palabra\">")
                .concat(TPReservada.ES.toString())
                .concat("</span> <span class=\"dato\">")
                .concat(TPReservada.TEXTO.toString())
                .concat("</span></div><div class=\"codigo2\"><span class=\"palabra\">")
                .concat(TPReservada.R.toString())
                .concat("</span> caracter1 <span class=\"palabra\">")
                .concat(TPReservada.ES.toString())
                .concat("</span> <span class=\"dato\">")
                .concat(TPReservada.CARACTER.toString())
                .concat("</span></div><div class=\"codigo1\"><span class=\"palabra\">")
                .concat(TPReservada.R.toString())
                .concat("</span> Numero <span class=\"palabra\">")
                .concat(TPReservada.ES.toString())
                .concat("</span> <span class=\"dato\">")
                .concat(TPReservada.DECIMAL.toString())
                .concat("</span>\n")
                .concat("</div>\n")
                .concat("<div class=\"codigo2\">\n")
                .concat("<span class=\"palabra\">")
                .concat(TPReservada.R.toString())
                .concat("</span> Variable2C <span class=\"palabra\">")
                .concat(TPReservada.ES.toString())
                .concat("</span> <span class=\"dato\">")
                .concat(TPReservada.ENTERO.toString())
                .concat("</span></div><div class=\"codigo1\"><span class=\"palabra\">")
                .concat(TPReservada.R.toString())
                .concat("</span> NOMBRE <span class=\"palabra\">")
                .concat(TPReservada.ES.toString())
                .concat("</span> <span class=\"dato\">")
                .concat(TPReservada.BOOLEANO.toString())
                .concat("</span></div></div>"), "Declaracion de variables",interfaz);
    }
}
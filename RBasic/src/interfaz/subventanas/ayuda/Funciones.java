package interfaz.subventanas.ayuda;

import interfaz.subventanas.Base;
import diccionario.TPReservada;
import interfaz.Interfaz;

public class Funciones extends Base{
    public Funciones(Interfaz interfaz) {//).concat(
        super(new Object[]{"funciones","sistenas","estandar","recibe","in","out","salida","sout","print","get","clear","cls","limpiar","esperar","wait","comentarios","coment","comentario","variable","valor","ingresar"}, 
            "<div class=\"titulo\"style=\"padding-left:88px;\">Funciones del sistema</div><br/><div style=\"padding-left:16px;\">&#09;Existen varias funciones ya creadas en el sistema para su uso en los programas las cuales son las siguientes:</div><br/><div class=\"subTitulo\" style=\"padding-left:16px;\">COMENTARIOS</div><br/><div class=\"seudoCodigo\" style=\"padding-left:133px;\">[$] {<span style=\"font-style:italic;\">El texto del comentario</span>}</div><br/><div style=\"padding-left:16px;\">&#09;En RBasic puedes documentar tu código utilizando el símbolo de $, con el cual puedes agregar notas a tu código, y el compilador ignora dichas líneas de código, por ejemplo:</div><br/><div class=\"caja\"><div class=\"codigo1\"><span class=\"palabra\">"
            .concat(TPReservada.R.toString())
            .concat("</span> S <span class=\"palabra\">")
            .concat(TPReservada.ES.toString())
            .concat("</span> <span class=\"dato\">")
            .concat(TPReservada.TEXTO.toString())
            .concat("</span><span class=\"comentario\">$Declaramos la variable que vamos a utilizar</span></div><div class=\"codigo2\"><span class=\"comentario\">$Y le asignamos un valor</span></div><div class=\"codigo1\">S = <span class=\"cadena\">\"TEXTO\"</span></div></div><br/><div class=\"subTitulo\" style=\"padding-left:16px;\">IMPRIME</div><br/><div class=\"seudoCodigo\" style=\"padding-left:134px;\">[")
            .concat(TPReservada.IMPRIME.toString())
            .concat("] {<span style=\"font-style:italic;\">Valor a imprimir</span>}</div><br/><div style=\"padding-left:16px;\">&#09;Esta función nos permite sacar a consola valores del sistema, esta función se rige con las mismas reglas que tienen para asignar un texto, por ejemplo:</div><br/><div class=\"caja\"><div class=\"codigo1\"><span class=\"palabra\">")
            .concat(TPReservada.R.toString())
            .concat("</span> S <span class=\"palabra\">")
            .concat(TPReservada.ES.toString())
            .concat("</span> <span class=\"dato\">")
            .concat(TPReservada.TEXTO.toString())
            .concat("</span></div><div class=\"codigo2\">S = <span class=\"cadena\">\"TEXTO\"</span></div><div class=\"codigo1\"><span class=\"funcion\">")
            .concat(TPReservada.IMPRIME.toString())
            .concat(" </span><span class=\"cadena\">\"El valor de la variable es \"</span> + S</div></div><br/><div class=\"subTitulo\" style=\"padding-left:16px;\">LIMPIAR</div><br/><div class=\"seudoCodigo\" style=\"padding-left:194px;\">[")
            .concat(TPReservada.LIMPIAR.toString())
            .concat("]</div><br/><div style=\"padding-left:16px;\">&#09;Esta función nos sirve para limpiar la consola de RBasic, y con ello podemos hacer más dinámicas los programas. Esta función solo necesitas que la escriba no requiere ningún otro argumento. Ejemplo:</div><br/><div class=\"caja\"><div class=\"codigo1\"><span class=\"palabra\">")
            .concat(TPReservada.R.toString())
            .concat("</span> S <span class=\"palabra\">")
            .concat(TPReservada.ES.toString())
            .concat("</span> <span class=\"dato\">")
            .concat(TPReservada.TEXTO.toString())
            .concat("</span></div><div class=\"codigo2\">S = <span class=\"cadena\">\"TEXTO\"</span></div><div class=\"codigo1\"><span class=\"funcion\">")
            .concat(TPReservada.IMPRIME.toString())
            .concat(" </span><span class=\"cadena\">\"El valor de la variable es \"</span> + S</div><div class=\"codigo2\"><span class=\"funcion\">")
            .concat(TPReservada.LIMPIAR.toString())
            .concat("</span></div></div><br/><div class=\"subTitulo\" style=\"padding-left:16px;\">ESPERAR</div><br/><div class=\"seudoCodigo\" style=\"padding-left:123px;\">[")
            .concat(TPReservada.ESPERAR.toString())
            .concat("] {<span style=\"font-style:italic;\">Tiempo de espera</span>}</div><br/><div style=\"padding-left:16px;\">&#09;Esta función nos permite esperar una cierta cantidad de tiempo(solo acepta numeros enteros), para después continuar con la ejecución del programa. Necesitas enviar un valor entero a la función, puede ser literalmente o por medio de una variable:</div><br/><div class=\"caja\"><div class=\"codigo1\"><span class=\"palabra\">")
            .concat(TPReservada.R.toString())
            .concat("</span> TIEMPO <span class=\"palabra\">")
            .concat(TPReservada.ES.toString())
            .concat("</span> <span class=\"dato\">")
            .concat(TPReservada.ENTERO.toString())
            .concat("</span></div><div class=\"codigo2\">TIEMPO = <span class=\"numero\">2</span></div><div class=\"codigo1\"><span class=\"funcion\">")
            .concat(TPReservada.ESPERAR.toString())
            .concat(" </span> TIEMPO</div></div><br/><div class=\"subTitulo\" style=\"padding-left:16px;\">RECIBE</div><br/><div class=\"seudoCodigo\" style=\"font-size: 14px;padding-left:39px;\">[")
            .concat(TPReservada.RECIBE.toString())
            .concat("] [\"]{<span style=\"font-style:italic;\">Texto que quieres que se despliegue para el usuario</span>}[\"]</div><br/><div style=\"padding-left:16px;\">&#09;Esta función nos permite recibir un valor ingresado por el usuario por medio de la consola de RBasic y asignárselo a una variable, por ejemplo:</div><br/><div class=\"caja\"><div class=\"codigo1\"><span class=\"palabra\">")
            .concat(TPReservada.R.toString())
            .concat("</span> S <span class=\"palabra\">")
            .concat(TPReservada.ES.toString())
            .concat("</span> <span class=\"dato\">")
            .concat(TPReservada.TEXTO.toString())
            .concat("</span></div><div class=\"codigo2\">S = <span class=\"funcion\">")
            .concat(TPReservada.RECIBE.toString())
            .concat("</span> <span class=\"cadena\">\"Ingresa un texto\"</span></div></div><br/><br/><br/>"), "Funciones del sistema", interfaz);
    }
}
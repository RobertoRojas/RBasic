package utileria;

import diccionario.TPReservada;
import java.util.ArrayList;
import java.util.List;

public class Utileria {
    private static final List<String> palabrasReservadas = new ArrayList<>();
    public static void setPalabraReservada(String palabra){
        palabrasReservadas.add(palabra);
    }
    public static boolean isPalabraReservada(String palabra){
        return palabrasReservadas.stream().anyMatch((palabrasReservada) -> (palabrasReservada.equals(palabra)));
    }
    public static String formatRegex(String cadena){
        String aux = "";
        for (int i = 0; i < cadena.length(); i++) {
            aux=aux.concat("[").concat(String.valueOf(cadena.charAt(i))).concat("]");
        }
        return aux;
    }
    public static String booleanoToString(boolean bandera){
        return bandera?TPReservada.VERDADERO.toString():TPReservada.FALSO.toString();
    }
    public static String formatoImpresion(String cadena){
        cadena = cadena.replaceAll("[<]", "&lt;");
        cadena = cadena.replaceAll("[>]", "&gt;");
        cadena = cadena.replaceAll("[ ]", "&nbsp;");
        return cadena.replaceAll("[\t]", "&nbsp;&nbsp;&nbsp;&nbsp;");
    }
    public static boolean isComitas(String cadena){
        return cadena.matches("^([\\s]*[\"].*[\"][\\s]*)$");
    }
    public static boolean isPalabraSimilar(String palabra1, String palabra2){
        return calcularDistanciaLevenshtein(palabra1, palabra2, palabra1.length(), palabra2.length())<3;
    }
    private static int minimo(int a, int b, int c){
        return Math.min(Math.min(a, b), c);
    }  
    private static int calcularDistanciaLevenshtein(String s1, String s2, int n, int m) {
        if(n == 0 && m == 0)return 0;
        if(n == 0)return m;
        if( m == 0 )return n;
        int a = calcularDistanciaLevenshtein(s1, s2, n-1, m-1) + (s1.charAt(n-1)!=s2.charAt(m-1)?1:0);
        int b = calcularDistanciaLevenshtein(s1, s2, n-1, m) + 1;
        int c = calcularDistanciaLevenshtein(s1, s2, n, m-1) + 1;
        return  minimo(a, b, c);
  
    }
}
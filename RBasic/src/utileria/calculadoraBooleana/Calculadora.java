package utileria.calculadoraBooleana;

import diccionario.TPReservada;
import java.util.regex.Pattern;

public class Calculadora {
    public static boolean resultado(String expresion){
        if(Pattern.compile("[\\s]".concat(TPReservada.Y.getRegex()).concat("[\\s]")).matcher(expresion).find())return resultado(getIf(expresion));
        else if(Pattern.compile("[\\s]".concat(TPReservada.O.getRegex()).concat("[\\s]")).matcher(expresion).find())return getOr(expresion);
        return getValor(expresion);
    }
    private static boolean getOr(String expresion){
        String[] tokens = expresion.split("[\\s]+".concat(TPReservada.O.getRegex()).concat("[\\s]+"));
        for (String token : tokens)if (Calculadora.resultado(token))return true;
        return false;
    }
    private static String getIf(String expresion){
        String[] tokens = expresion.split("[\\s]+".concat(TPReservada.Y.getRegex()).concat("[\\s]+"));
        String[] izquierda = tokens[0].split("[\\s]+".concat(TPReservada.O.getRegex()).concat("[\\s]+"));
        String[] derecha = tokens[1].split("[\\s]+".concat(TPReservada.O.getRegex()).concat("[\\s]+"));
        String mensaje = " ".concat(TPReservada.FALSO.toString()).concat("");
        Calculadora calculadora = new Calculadora();
        if(calculadora.resultado(izquierda[izquierda.length-1])&&calculadora.resultado(derecha[0])){
            mensaje = " ".concat(TPReservada.VERDADERO.toString()).concat("");
        }
        expresion="";
        for (int i = 0; i < izquierda.length -1; i++)expresion = expresion.concat(izquierda[i]).concat(" O ");
        expresion = expresion.concat(mensaje);
        for (int i = 1; i < derecha.length; i++) expresion = expresion.concat(" O ").concat(derecha[i]);
        for (int i = 2; i < tokens.length; i++) {
            expresion = expresion.concat(" Y ").concat(tokens[i]);
        }
        return expresion;
    }
    private static boolean getValor(String expresion){
        if(Pattern.compile("[\\s]*".concat(TPReservada.VERDADERO.getRegex()).concat("[\\s]*")).matcher(expresion).matches())return true;
        if(Pattern.compile("[\\s]*.*[\\s]+".concat(TPReservada.EQUIVALE.getRegex()).concat("[\\s]+.*[\\s]*")).matcher(expresion).matches())return equivale(expresion);
        if(Pattern.compile("[\\s]*.*[\\s]+".concat(TPReservada.DIFERENTE.getRegex()).concat("[\\s]+.*[\\s]*")).matcher(expresion).matches())return diferente(expresion);
        if(Pattern.compile("[\\s]*.*[\\s]+".concat(TPReservada.MENOROIGUALQUE.getRegex()).concat("[\\s]+.*[\\s]*")).matcher(expresion).matches())return menorOIgual(expresion);        
        if(Pattern.compile("[\\s]*.*[\\s]+".concat(TPReservada.MENORQUE.getRegex()).concat("[\\s]+.*[\\s]*")).matcher(expresion).matches())return menorQue(expresion);
        if(Pattern.compile("[\\s]*.*[\\s]+".concat(TPReservada.MAYOROIGUALQUE.getRegex()).concat("[\\s]+.*[\\s]*")).matcher(expresion).matches())return mayorOIgualQue(expresion);
        if(Pattern.compile("[\\s]*.*[\\s]+".concat(TPReservada.MAYORQUE.getRegex()).concat("[\\s]+.*[\\s]*")).matcher(expresion).matches())return mayorQue(expresion);
        return false;
    }
    private static boolean mayorQue(String expresion){
        String[] tokens = expresion.split("[\\s]+".concat(TPReservada.MAYORQUE.getRegex()).concat("[\\s]+"));
        double a = Double.parseDouble(tokens[0]);
        double b = Double.parseDouble(tokens[1]);
        return a>b;        
    }
    private static boolean mayorOIgualQue(String expresion){
        String[] tokens = expresion.split("[\\s]+".concat(TPReservada.MAYOROIGUALQUE.getRegex()).concat("[\\s]+"));
        double a = Double.parseDouble(tokens[0]);
        double b = Double.parseDouble(tokens[1]);
        return a>=b;        
    }
    private static boolean menorQue(String expresion){
        String[] tokens = expresion.split("[\\s]+".concat(TPReservada.MENORQUE.getRegex()).concat("[\\s]+"));
        double a = Double.parseDouble(tokens[0]);
        double b = Double.parseDouble(tokens[1]);
        return a<b;        
    }
    private static boolean menorOIgual(String expresion){
        String[] tokens = expresion.split("[\\s]+".concat(TPReservada.MENOROIGUALQUE.getRegex()).concat("[\\s]+"));
        double a = Double.parseDouble(tokens[0]);
        double b = Double.parseDouble(tokens[1]);
        return a<=b;
    }
    private static boolean diferente(String expresion){
        String[] tokens = expresion.split("[\\s]+".concat(TPReservada.DIFERENTE.getRegex()).concat("[\\s]+"));
        tokens[0]=tokens[0].replaceAll("[\\s]", "");
        tokens[1]=tokens[1].replaceAll("[\\s]", "");
        return !tokens[0].equals(tokens[1]);
    }
    private static boolean equivale(String expresion){
        String[] tokens = expresion.split("[\\s]+".concat(TPReservada.EQUIVALE.getRegex()).concat("[\\s]+"));
        tokens[0]=tokens[0].replaceAll("[\\s]", "");
        tokens[1]=tokens[1].replaceAll("[\\s]", "");
        return tokens[0].equals(tokens[1]);
    }
    private static String convertir(boolean valor){
        return valor?TPReservada.VERDADERO.toString():TPReservada.FALSO.toString();
    }
}
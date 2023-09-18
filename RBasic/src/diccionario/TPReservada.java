package diccionario;

import utileria.Utileria;

public enum TPReservada {
    TEXTO("TEXTO"),
    CARACTER("CARACTER"),
    ENTERO("ENTERO"),
    DECIMAL("DECIMAL"),
    BOOLEANO("BOOLEANO"),
    R("R"),
    ES("ES"),
    SI("SI"),
    SINO("SINO"),
    NO("NO"),
    TSI("TSI"),
    DE("DE"),
    HASTA("HASTA"),
    PASO("PASO"),
    TDE("TDE"),
    MIENTRAS("MIENTRAS"),
    TMIENTRAS("TMIENTRAS"),
    VERDADERO("VERDADERO"),
    FALSO("FALSO"),
    ENTONCES("ENTONCES"),
    EQUIVALE("EQUIVALE"),
    DIFERENTE("DIFERENTE"),
    MENORQUE("MENORQUE"),
    MAYORQUE("MAYORQUE"),
    MENOROIGUALQUE("MENOROIGUALQUE"),
    MAYOROIGUALQUE("MAYOROIGUALQUE"),
    Y("Y"),
    O("O"),
    ACCION("ACCION"),
    TACCION("TACCION"),
    OPERACION("OPERACION"),
    RETORNA("RETORNA"),
    TOPERACION("TOPERACION"),
    IMPRIME("IMPRIME"),
    LIMPIAR("LIMPIAR"),
    ESPERAR("ESPERAR"),
    RECIBE("RECIBE");
    private final String valor,regex;
    private TPReservada(String valor){
        this.valor = valor;
        regex = Utileria.formatRegex(valor);
        Utileria.setPalabraReservada(valor);
    }
    public String getRegex(){
        return regex;
    }
    @Override
    public String toString(){
        return valor;
    }
}
package utileria;

import javax.swing.text.Segment;
import org.fife.ui.rsyntaxtextarea.AbstractTokenMaker;
import org.fife.ui.rsyntaxtextarea.RSyntaxUtilities;
import org.fife.ui.rsyntaxtextarea.Token;
import org.fife.ui.rsyntaxtextarea.TokenMap;
import diccionario.TPReservada;

public class RBasic extends AbstractTokenMaker{
    private void setDato(TokenMap tokenMap){
        tokenMap.put(TPReservada.TEXTO.toString(), Token.DATA_TYPE);
        tokenMap.put(TPReservada.CARACTER.toString(), Token.DATA_TYPE);
        tokenMap.put(TPReservada.ENTERO.toString(), Token.DATA_TYPE);
        tokenMap.put(TPReservada.DECIMAL.toString(), Token.DATA_TYPE);
        tokenMap.put(TPReservada.BOOLEANO.toString(), Token.DATA_TYPE);
    }
    @SuppressWarnings("empty-statement")
    private void setPalabrasReservadas(TokenMap tokenMap){
        tokenMap.put(TPReservada.R.toString(), Token.RESERVED_WORD);
        tokenMap.put(TPReservada.ES.toString(), Token.RESERVED_WORD);
        tokenMap.put(TPReservada.DE.toString(), Token.RESERVED_WORD);
        tokenMap.put(TPReservada.HASTA.toString(), Token.RESERVED_WORD);
        tokenMap.put(TPReservada.PASO.toString(), Token.RESERVED_WORD);
        tokenMap.put(TPReservada.MIENTRAS.toString(), Token.RESERVED_WORD);
        tokenMap.put(TPReservada.OPERACION.toString(), Token.RESERVED_WORD);
        tokenMap.put(TPReservada.ACCION.toString(), Token.RESERVED_WORD);
        tokenMap.put(TPReservada.RETORNA.toString(), Token.RESERVED_WORD);
        tokenMap.put(TPReservada.SI.toString(), Token.RESERVED_WORD);
        tokenMap.put(TPReservada.SINO.toString(), Token.RESERVED_WORD);
        tokenMap.put(TPReservada.NO.toString(), Token.RESERVED_WORD);
        tokenMap.put(TPReservada.TSI.toString(), Token.RESERVED_WORD);
        tokenMap.put(TPReservada.TMIENTRAS.toString(), Token.RESERVED_WORD);
        tokenMap.put(TPReservada.TDE.toString(), Token.RESERVED_WORD);       
        tokenMap.put(TPReservada.TOPERACION.toString(), Token.RESERVED_WORD);        
        tokenMap.put(TPReservada.TACCION.toString(), Token.RESERVED_WORD);        
        tokenMap.put(TPReservada.ENTONCES.toString(), Token.RESERVED_WORD);       
        tokenMap.put(TPReservada.EQUIVALE.toString(), Token.RESERVED_WORD);       
        tokenMap.put(TPReservada.DIFERENTE.toString(), Token.RESERVED_WORD);    
        tokenMap.put(TPReservada.MENOROIGUALQUE.toString(), Token.RESERVED_WORD);    
        tokenMap.put(TPReservada.MENORQUE.toString(), Token.RESERVED_WORD);    
        tokenMap.put(TPReservada.MAYOROIGUALQUE.toString(), Token.RESERVED_WORD);    
        tokenMap.put(TPReservada.MAYORQUE.toString(), Token.RESERVED_WORD); 
        tokenMap.put(TPReservada.Y.toString(), Token.RESERVED_WORD);        
        tokenMap.put(TPReservada.O.toString(), Token.RESERVED_WORD);     ;           
        tokenMap.put(TPReservada.VERDADERO.toString(), Token.RESERVED_WORD_2);           
        tokenMap.put(TPReservada.FALSO.toString(), Token.RESERVED_WORD_2);         
    }
    public void setOperacionesYAcciones(TokenMap tokenMap){      
        tokenMap.put(TPReservada.RECIBE.toString(), Token.FUNCTION);        
        tokenMap.put(TPReservada.IMPRIME.toString(), Token.FUNCTION);       
        tokenMap.put(TPReservada.LIMPIAR.toString(), Token.FUNCTION);   
        tokenMap.put(TPReservada.ESPERAR.toString(), Token.FUNCTION);                 
    }
    @Override
    public TokenMap getWordsToHighlight() {
        TokenMap tokenMap = new TokenMap();
        setDato(tokenMap);
        setPalabrasReservadas(tokenMap);
        setOperacionesYAcciones(tokenMap);
        return tokenMap;
    }
    @Override
    public void addToken(Segment segment, int start, int end, int tokenType, int startOffset) {
        // This assumes all keywords, etc. were parsed as "identifiers."
        if (tokenType==Token.IDENTIFIER) {
            int value = wordsToHighlight.get(segment, start, end);
            if (value != -1) {
                tokenType = value;
            }
        }
        super.addToken(segment, start, end, tokenType, startOffset);
    }
    @Override
    public Token getTokenList(Segment text, int startTokenType, int startOffset) {
        resetTokenList();
        char[] array = text.array;
        int offset = text.offset;
        int count = text.count;
        int end = offset + count;
        // Token starting offsets are always of the form:
        // 'startOffset + (currentTokenStart-offset)', but since startOffset and
        // offset are constant, tokens' starting positions become:
        // 'newStartOffset+currentTokenStart'.
        int newStartOffset = startOffset - offset;
        int currentTokenStart = offset;
        int currentTokenType = startTokenType;
        for (int i=offset; i<end; i++) {
            char c = array[i];
            switch (currentTokenType) {
                case Token.NULL:
                    currentTokenStart = i;   // Starting a new token here.
                    switch (c) {
                        case ' ':
                        case '\t':
                            currentTokenType = Token.WHITESPACE;
                            break;
                        case '"':
                        case '\'':
                            currentTokenType = Token.LITERAL_STRING_DOUBLE_QUOTE;
                            break;
                        case '$':
                            currentTokenType = Token.COMMENT_EOL;
                            break;
                        default:
                            if (RSyntaxUtilities.isDigit(c)) {
                                currentTokenType = Token.LITERAL_NUMBER_DECIMAL_INT;
                                break;
                            } else if (RSyntaxUtilities.isLetter(c) || c=='/' || c=='_') {
                                currentTokenType = Token.IDENTIFIER;
                                break;
                            }
                            // Anything not currently handled - mark as an identifier
                            currentTokenType = Token.IDENTIFIER;
                            break;
                    } // End of switch (c).
                    break;
                case Token.WHITESPACE:
                    switch (c) {
                        case ' ':
                        case '\t':
                            break;   // Still whitespace.
                        case '"':    
                        case '\'':
                            addToken(text, currentTokenStart,i-1, Token.WHITESPACE, newStartOffset+currentTokenStart);
                            currentTokenStart = i;
                            currentTokenType = Token.LITERAL_STRING_DOUBLE_QUOTE;
                            break;
                        case '$':
                            addToken(text, currentTokenStart,i-1, Token.WHITESPACE, newStartOffset+currentTokenStart);
                            currentTokenStart = i;
                            currentTokenType = Token.COMMENT_EOL;
                            break;
                        default:   // Add the whitespace token and start anew.
                            addToken(text, currentTokenStart,i-1, Token.WHITESPACE, newStartOffset+currentTokenStart);
                            currentTokenStart = i;
                            if (RSyntaxUtilities.isDigit(c)) {
                                currentTokenType = Token.LITERAL_NUMBER_DECIMAL_INT;
                                break;
                            }
                            else if (RSyntaxUtilities.isLetter(c) || c=='/' || c=='_') {
                                currentTokenType = Token.IDENTIFIER;
                                break;
                            }
                            // Anything not currently handled - mark as identifier
                            currentTokenType = Token.IDENTIFIER;
                    } // End of switch (c).
                    break;
                default: // Should never happen
                    case Token.IDENTIFIER:
                    switch (c) {
                        case ' ':
                        case '\t':
                            addToken(text, currentTokenStart,i-1, Token.IDENTIFIER, newStartOffset+currentTokenStart);
                            currentTokenStart = i;
                            currentTokenType = Token.WHITESPACE;
                            break;
                        case '"':
                        case '\'':
                            addToken(text, currentTokenStart,i-1, Token.IDENTIFIER, newStartOffset+currentTokenStart);
                            currentTokenStart = i;
                            currentTokenType = Token.LITERAL_STRING_DOUBLE_QUOTE;
                            break;
                        default:
                            if (RSyntaxUtilities.isLetterOrDigit(c) || c=='/' || c=='_') {
                                break;   // Still an identifier of some type.
                            }
                          // Otherwise, we're still an identifier (?).
                    } // End of switch (c).
                    break;
                case Token.LITERAL_NUMBER_DECIMAL_INT:
                    switch (c) {
                       case ' ':
                       case '\t':
                            addToken(text, currentTokenStart,i-1, Token.LITERAL_NUMBER_DECIMAL_INT, newStartOffset+currentTokenStart);
                            currentTokenStart = i;
                            currentTokenType = Token.WHITESPACE;
                            break;
                       case '"':
                       case '\'':
                            addToken(text, currentTokenStart,i-1, Token.LITERAL_NUMBER_DECIMAL_INT, newStartOffset+currentTokenStart);
                            currentTokenStart = i;
                            currentTokenType = Token.LITERAL_STRING_DOUBLE_QUOTE;
                            break;
                       default:
                            if (RSyntaxUtilities.isDigit(c)) {
                                break;   // Still a literal number.
                            }
                            // Otherwise, remember this was a number and start over.
                            addToken(text, currentTokenStart,i-1, Token.LITERAL_NUMBER_DECIMAL_INT, newStartOffset+currentTokenStart);
                            i--;
                            currentTokenType = Token.NULL;
                    } // End of switch (c).
                    break;
                case Token.COMMENT_EOL:
                    i = end - 1;
                    addToken(text, currentTokenStart,i, currentTokenType, newStartOffset+currentTokenStart);
                    // We need to set token type to null so at the bottom we don't add one more token.
                    currentTokenType = Token.NULL;
                    break;
                case Token.LITERAL_STRING_DOUBLE_QUOTE:
                    if (c=='"' || c=='\'') {
                        addToken(text, currentTokenStart,i, Token.LITERAL_STRING_DOUBLE_QUOTE, newStartOffset+currentTokenStart);
                        currentTokenType = Token.NULL;
                    }
                    break;
            } // End of switch (currentTokenType).
        } // End of for (int i=offset; i<end; i++).
        switch (currentTokenType) {
            // Remember what token type to begin the next line with.
            case Token.LITERAL_STRING_DOUBLE_QUOTE:
                addToken(text, currentTokenStart,end-1, currentTokenType, newStartOffset+currentTokenStart);
                break;
            // Do nothing if everything was okay.
            case Token.NULL:
                addNullToken();
                break;
            // All other token types don't continue to the next line...
            default:
                addToken(text, currentTokenStart,end-1, currentTokenType, newStartOffset+currentTokenStart);
                addNullToken();
        }
        // Return the first token in our linked list.
        return firstToken;
    }
}
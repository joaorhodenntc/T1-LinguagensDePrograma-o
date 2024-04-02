import java.io.*;

public class Lexer {
    /* Declarações globais */
    /* Variáveis */
    static int charClass;
    static char[] lexeme = new char[100];
    static char nextChar;
    static int lexLen;
    static int token;
    static int nextToken;
    static BufferedReader in_fp;
    
    /* Classes de caracteres */
    static final int LETTER = 0;
    static final int DIGIT = 1;
    static final int UNKNOWN = 99;

    /* Códigos de tokens */
    static final int INT_LIT = 10;
    static final int IDENT = 11;
    static final int FOR_PAL = 20;
    static final int IF_PAL = 21;
    static final int ELSE_PAL = 22;
    static final int WHILE_PAL = 23;
    static final int DO_PAL = 24;
    static final int INT_PAL = 25;
    static final int FLOAT_PAL = 26;
    static final int SWITCH_PAL = 27;

        /* Função principal */
    public static void main(String[] args) throws IOException {
        * Abre o arquivo de entrada e processos seu conteúdo */
        try {
            in_fp = new BufferedReader(new FileReader("front.in"));
            getChar();
            do {
                lex();
            } while (nextToken != -1);
        } catch (FileNotFoundException e) {
            System.out.println("ERROR - cannot open front.in");
        }
    }
    /*
     * lookup - uma função para processar operadores e parênteses e retornar o token*/
    static int lookup(String string) {
        switch (string) {
            case "for":
                nextToken = FOR_PAL;
                break;
            case "if":
                nextToken = IF_PAL;
                break;
            case "else":
                nextToken = ELSE_PAL;
                break;
            case "while":
                nextToken = WHILE_PAL;
                break;
            case "do":
                nextToken = DO_PAL;
                break;
            case "int":
                nextToken = INT_PAL;
                break;
            case "float":
                nextToken = FLOAT_PAL;
                break;
            case "switch":
                nextToken = SWITCH_PAL;
                break;
            default:
                nextToken = IDENT;
                break;
        }
        return nextToken;
    }

/* addChar - uma função para adicionar nextChar a lexeme */
    static void addChar() {
        if (lexLen <= 98) {
            lexeme[lexLen++] = nextChar;
            lexeme[lexLen] = 0;
        } else
            System.out.println("Error - lexeme is too long");
    }

    /*
     * getChar - uma função para obter o próximo caractere de entrada e determinar sua classe
     */
    static void getChar() throws IOException {
        if ((nextChar = (char) in_fp.read()) != (char) -1) {
            if (Character.isLetter(nextChar))
                charClass = LETTER;
            else if (Character.isDigit(nextChar))
                charClass = DIGIT;
            else
                charClass = UNKNOWN;
        } else
            charClass = -1;
    }

    /*
     * getNonBlank - uma função para chamar getCHar até que ele retorne um caractere que não seja um espaço branco
     */
    static void getNonBlank() throws IOException {
        while (Character.isWhitespace(nextChar))
            getChar();
    }

    /* lex - um analisador léxico simples para expressões aritméticas */
    static int lex() throws IOException {
        lexLen = 0;
        lexeme = new char[100];
        getNonBlank();
        switch (charClass) {
            case LETTER:
                addChar();
                getChar();
                while (charClass == LETTER || charClass == DIGIT) {
                    addChar();
                    getChar();
                }
                String word = new String(lexeme).trim();
                lookup(word);
                break;
            case DIGIT:
                addChar();
                getChar();
                while (charClass == DIGIT) {
                    addChar();
                    getChar();
                }
                nextToken = INT_LIT;
                break;
            case UNKNOWN:
                getChar();
                break;
            case -1:
                nextToken = -1;
                lexeme[0] = 'E';
                lexeme[1] = 'O';
                lexeme[2] = 'F';
                lexeme[3] = 0;
                break;
        }
        System.out.println("Next token is: " + nextToken + ", Next lexeme is " + new String(lexeme).trim());
        return nextToken;
    }
}

import java.io.*;

public class Lexer {
    /* Global Declarations */
    /* Variables */
    static int charClass;
    static char[] lexeme = new char[100];
    static char nextChar;
    static int lexLen;
    static int token;
    static int nextToken;
    static BufferedReader in_fp;

    /* Character classes */
    static final int LETTER = 0;
    static final int DIGIT = 1;
    static final int UNKNOWN = 99;

    /* Token codes */
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

    public static void main(String[] args) throws IOException {
        /* Open the input data file and process its contents */
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

    /* addChar - a function to add nextChar to lexeme */
    static void addChar() {
        if (lexLen <= 98) {
            lexeme[lexLen++] = nextChar;
            lexeme[lexLen] = 0;
        } else
            System.out.println("Error - lexeme is too long");
    }

    /*
     * getChar - a function to get the next character of input and determine its
     * character class
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
     * getNonBlank - a function to call getChar until it returns a non-whitespace
     * character
     */
    static void getNonBlank() throws IOException {
        while (Character.isWhitespace(nextChar))
            getChar();
    }

    /* lex - a simple lexical analyzer for arithmetic expressions */
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

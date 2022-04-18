lexer grammar CoolLexer;

tokens { ERROR } 

@header{
    package cool.lexer;
}

@members{
    private void raiseError(String msg) {
        setText(msg);
        setType(ERROR);
    }
}

/* Cuvinte cheie */
CLASS : 'class';
ELSE : 'else';
FALSE : 'false';
FI : 'fi';
IF : 'if';
IN : 'in';
INHERITS : 'inherits';
LET : 'let';
LOOP : 'loop';
POOL : 'pool';
THEN : 'then';
WHILE : 'while';
CASE : 'case';
ESAC : 'esac';
NEW : 'new';
OF : 'of';
NOT : 'not';
TRUE : 'true';

/* Operatori */
POINT: '.';
DELIM : ';';
ATTRIB : '<-';
AT : '@';
NEG : '~';
ISVOID : 'isvoid';
COMMA : ',';
SEMICOLLON : ':';
PLUS : '+';
MINUS : '-';
MUL : '*';
DIV : '/';

/* Paranteze */
CURLY_LPAREN : '{';
LPAREN : '(';
CURLY_RPAREN : '}';
RPAREN : ')';

/* Operatori relationari */
EQUAL : '=';
LESS : '<';
LE : '<=';
GE : '=>';

fragment DIGIT : [0-9];
INT : DIGIT+;

fragment LOWERCASE : [a-z];
fragment UPPERCASE : [A-Z];
fragment LETTER : LOWERCASE | UPPERCASE;
fragment ENDL : '\r' ? '\n';
fragment NAME: (LETTER | '_' | DIGIT)*;

TYPE: UPPERCASE NAME;
ID: LOWERCASE NAME;

STRING : '"' ('\\"' | '\\' ENDL | .)*? ('"' {
    // Varianta in care sirul se termina cu "
        String str = getText();

        str = str.substring(1, str.length() - 1)
                 .replace("\\b", "\b")
                 .replace("\\t", "\t")
                 .replace("\\r", "\r")
                 .replace("\\n", "\n")
                 .replace("\\f", "\f")
                 // \ is escaped once for Java and a second time for regex
                 .replaceAll("\\\\(?!\\\\)", "");

        if (str.length() > 1024) {
            raiseError("String constant too long");
        } else if (str.contains("\0")) {
            raiseError("String contains null character");
        } else {
            setText(str);
        }
    }
    | EOF { raiseError("EOF in string constant"); }
    | ENDL { raiseError("Unterminated string constant"); });

/* Comentarii */
SINGLE_COMMENT : '--'.*?(EOF|ENDL) -> skip;
BLOCK_COMMENT : '(*'(BLOCK_COMMENT|.)*? ('*)' { skip(); } | EOF { raiseError("EOF in comment"); });
UNOPENED_COMMENT : '*)' { raiseError("Unmatched *)"); };

WS:	[ \n\f\r\t\b]+ -> skip;
INVALID: . { raiseError("Invalid character: " + getText()); };

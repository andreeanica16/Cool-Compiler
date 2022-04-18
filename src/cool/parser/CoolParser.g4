parser grammar CoolParser;

options {
    tokenVocab = CoolLexer;
}

@header{
    package cool.parser;
}

program
    :   (classes+=classDef DELIM)+
    ;

varDef
    :   name=ID SEMICOLLON type=TYPE (ATTRIB val=expr)?
    ;

formal
    :   name=ID SEMICOLLON type=TYPE
    ;

feature
    :   name=ID LPAREN (formals+=formal (COMMA formals+=formal)*)? RPAREN SEMICOLLON type=TYPE CURLY_LPAREN (body+=expr)* CURLY_RPAREN # funcDef
    |   name=ID SEMICOLLON type=TYPE (ATTRIB val=expr)?                                                                            # classMemberDef
    ;

classDef
    :   CLASS type=TYPE (INHERITS inherited=TYPE)? CURLY_LPAREN (features+=feature DELIM)* CURLY_RPAREN
    ;

caseOption
    : id=ID SEMICOLLON type=TYPE GE body=expr DELIM
    ;

expr
    :   name=ID LPAREN (args+=expr (COMMA args+=expr)*)? RPAREN                             # implicitDispatch
    |   initial=expr (AT TYPE)? POINT ID LPAREN (args+=expr (COMMA args+=expr)*)? RPAREN    # explicitDispatch
    |   IF cond=expr THEN branch1=expr ELSE branch2=expr FI                                 # if
    |   LPAREN expr RPAREN                                                                  # paren
    |   WHILE cond=expr LOOP body=expr POOL                                                 # while
    |   CURLY_LPAREN (body=expr DELIM)+ CURLY_RPAREN                                        # body
    |   LET vars+=varDef (COMMA vars+=varDef)* IN body=expr                                 # let
    |   CASE expr OF (cases+=caseOption)+ ESAC                                              # case
    |   NEW TYPE                                                                            # new
    |   ISVOID expr                                                                         # isvoid
    |   NEG expr                                                                            # neg
    |   left=expr op=(MUL | DIV) right=expr                                                 # multDiv
    |   left=expr op=(PLUS | MINUS) right=expr                                              # plusMinus
    |   left=expr op=(LESS | LE | EQUAL) right=expr                                         # relational
    |   NOT expr                                                                            # not
    |   ID                                                                                  # id
    |   INT                                                                                 # int
    |   STRING                                                                              # string
    |   name=ID ATTRIB val=expr                                                             # attrib
    |   (TRUE | FALSE )                                                                     # bool
    ;

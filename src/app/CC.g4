grammar CC;

program
    : decl* funcDecl* EOF
    ;

// DECLARATIONS

decl
    : type = (INT_TYPE | FLOAT_TYPE | STR_TYPE) ID (COMMA ID)* SEMICOLON
    ;

// FUNCTION DECLARATIONS

funcDecl
    : FUNC_TYPE any_type ID LBRACE paramList? RBRACE body
    ;

any_type
    : VOID_TYPE
    | INT_TYPE
    | FLOAT_TYPE
    | STR_TYPE
    ;

paramList
    : param (COMMA param)*
    ;

param
    : type = (INT_TYPE | FLOAT_TYPE | STR_TYPE) ID
    ;

body
    : L_CBRACE decl* stmt* R_CBRACE
    ;

funcCall
    : ID LBRACE args? RBRACE
    ;

args
    : (fStr | expr) (COMMA (fStr | expr))*
    ;

// STATEMENTS

stmt
    : assignStmt SEMICOLON
    | cmdStmt SEMICOLON
    | callStmt SEMICOLON
    | branchStmt
    | whileStmt
    | retStmt
    | contStmt
    | breakStmt
    ;

assignStmt
    : ID ASSIGN_OP (expr | fStr)
    ;

cmdStmt
    : CMD fStr
    ;

callStmt
    : funcCall
    ;

branchStmt
    : ifBlock elifBlock* elseBlock?
    ;

ifBlock
    : IF LBRACE cond RBRACE body
    ;

elifBlock
    : ELIF LBRACE cond RBRACE body
    ;

elseBlock
    : ELSE body
    ;

whileStmt
    : WHILE LBRACE cond RBRACE body
    ;

cond
    : expr op = (LT_OP | GT_OP | LE_OP | GE_OP | EQ_OP | NE_OP) expr
    ;

retStmt
    : RETURN (fStr | expr)? SEMICOLON
    ;

contStmt
    : CONTINUE SEMICOLON
    ;

breakStmt
    : BREAK SEMICOLON
    ;

// FORMATTED STRINGS

fStr
    : fStrVal+
    ;

fStrVal
    : STR
    | L_CBRACE (expr | fStr) R_CBRACE
    ;

// EXPRESSIONS

expr
    : expr op = (ADD_OP | SUB_OP) term
    | term
    ;

term
    : term op = (MUL_OP | DIV_OP) factor
    | factor
    ;

factor
    : funcCall
    | LBRACE expr RBRACE
    | INT
    | FLOAT
    | ID
    ;

FUNC_TYPE
    : 'func'
    ;

VOID_TYPE
    : 'void'
    ;

INT_TYPE
    : 'int'
    ;

FLOAT_TYPE
    : 'float'
    ;

STR_TYPE
    : 'str'
    ;

// STATEMENTS

CMD
    : 'cmd'
    ;

IF
    : 'if'
    ;

ELIF
    : 'elif'
    ;

ELSE
    : 'else'
    ;

WHILE
    : 'while'
    ;

RETURN
    : 'return'
    ;

CONTINUE
    : 'continue'
    ;

BREAK
    : 'break'
    ;

// DELIMINATION

LBRACE
    : '('
    ;

RBRACE
    : ')'
    ;

L_CBRACE
    : '{'
    ;

R_CBRACE
    : '}'
    ;

SEMICOLON
    : ';'
    ;

COMMA
    : ','
    ;

// OPERATORS

ASSIGN_OP
    : '='
    ;

ADD_OP
    : '+'
    ;

SUB_OP
    : '-'
    ;

MUL_OP
    : '*'
    ;

DIV_OP
    : '/'
    ;

LT_OP
    : '<'
    ;

GT_OP
    : '>'
    ;

LE_OP
    : '<='
    ;

GE_OP
    : '>='
    ;

EQ_OP
    : '=='
    ;

NE_OP
    : '!='
    ;

// -----

COMMENT
    : '#'~[\r\n]* -> skip
    ;

WHITESPACE
    : [ \t\r\n]+ -> skip
    ;

ID
    : [a-zA-Z_][a-zA-Z0-9_]*
    ;

INT
    : [0-9]+
    ;

FLOAT
    : [0-9]*'.'[0-9]*
    ;

STR
    : '"' (ESCAPE | ~('"' | '\\' | '\n' | '\r'))* '"'
    ;

fragment ESCAPE
    : '\\' ('"' | '\'' | '\\')
    ;
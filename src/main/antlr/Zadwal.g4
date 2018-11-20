grammar Zadwal;
@header {
package com.rplsd.scheduler;
}

defineClassroom
    : CLASSROOM WHITESPACE? (ROOM|NUMBER) WHITESPACE? NUMBER
    ;
defineLecturer
    : LECTURER WORD
    ;

fragment A : ('A' | 'a');
fragment B : ('B' | 'b');
fragment C : ('C' | 'c');
fragment D : ('D' | 'd');
fragment E : ('E' | 'e');
fragment F : ('F' | 'f');
fragment G : ('G' | 'g');
fragment H : ('H' | 'h');
fragment I : ('I' | 'i');
fragment J : ('J' | 'j');
fragment K : ('K' | 'k');
fragment L : ('L' | 'l');
fragment M : ('M' | 'm');
fragment N : ('N' | 'n');
fragment O : ('O' | 'o');
fragment P : ('P' | 'p');
fragment Q : ('Q' | 'q');
fragment R : ('R' | 'r');
fragment S : ('S' | 's');
fragment T : ('T' | 't');
fragment U : ('U' | 'u');
fragment V : ('V' | 'v');
fragment W : ('W' | 'w');
fragment X : ('X' | 'x');
fragment Y : ('Y' | 'y');
fragment Z : ('Z' | 'z');
fragment DASH : '-';
fragment DIGIT : '0'..'9';
fragment OPEN_PARENTHESIS : '[';
fragment CLOSE_PARENTHESIS : ']';
fragment COMMA : ',';
fragment LOWERCASE : [a-z];
fragment UPPERCASE : [A-Z];

WHITESPACE: ' '+;
ANY_CHARACTER: .;
CLASSROOM : C L A S S R O O M;
LECTURER : L E C T U R E R;
CLASS : C L A S S;
PREFERENCE : P R E F E R E N C E;
CONSTRAINT : C O N S T R A I N T;
SCHEDULE : S C H E D U L E;
FIXED : F I X E D;
NONCONFLICT : N O N DASH C O N F L I C T;
UNAVAILABLE : U N A V A I L A B L E;
MAX : M A X;
PARALLEL : P A R A L L E L;
NUMBER: DIGIT+;
WORD : (LOWERCASE | UPPERCASE)+;
ROOM: (UPPERCASE | DIGIT)+;
CLASS_ID : ( UPPERCASE | DIGIT )+;



grammar Zadwal;
@header {
package com.rplsd.scheduler;
}

array_of_lecturers: OPEN_PARENTHESIS (WORD COMMA WHITESPACE*)? WORD CLOSE_PARENTHESIS;
array_of_facilities: OPEN_PARENTHESIS (WORD COMMA WHITESPACE*)* WORD CLOSE_PARENTHESIS;
array_of_teaching_hours: OPEN_PARENTHESIS (NUMBER COMMA WHITESPACE*)* NUMBER CLOSE_PARENTHESIS;

fixed_schedule: FIXED SCHEDULE CLASS_ID array_of_teaching_hours;
pair_of_class_id: CLASS_ID COLON CLASS_ID;
non_conflict: NONCONFLICT OPEN_PARENTHESIS (pair_of_class_id COMMA WHITESPACE*)* pair_of_class_id CLOSE_PARENTHESIS;
unavailable: UNAVAILABLE array_of_teaching_hours;

defineClassroom
    : CLASSROOM WHITESPACE* ROOM_ID WHITESPACE* NUMBER WHITESPACE* array_of_facilities
    ;
defineLecturer
    : LECTURER WHITESPACE* WORD WHITESPACE* array_of_teaching_hours
    ;
defineClass
    : CLASS WHITESPACE* CLASS_ID WHITESPACE* array_of_lecturers WHITESPACE* NUMBER WHITESPACE* array_of_facilities WHITESPACE* NUMBER
    ;
defineConstraint
    : CONSTRAINT (fixed_schedule | non_conflict | unavailable)
    ;
definePreference
    : PREFERENCE (fixed_schedule | non_conflict | unavailable)
    ;
defineSchedule
    : SCHEDULE
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
fragment COLON : ':';
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
NUMBER: DIGIT+;
WORD : (LOWERCASE | UPPERCASE)+;
ROOM_ID: (NUMBER | WORD | (UPPERCASE | DIGIT)+);
CLASS_ID : ( UPPERCASE | DIGIT )+;

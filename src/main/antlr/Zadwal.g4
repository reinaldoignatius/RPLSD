grammar Zadwal;
@header {
package com.rplsd.scheduler;
}

room_id: (NUMBER | WORD | ALPHANUMERIC);
capacity: NUMBER;
facility: WORD;
lecturer_name: WORD;
teaching_hour: NUMBER;
class_id: CLASS_ID;
attendees_count: NUMBER;
duration: NUMBER;

array_of_lecturers: OPEN_PARENTHESIS (lecturer_name COMMA WHITESPACE*)? lecturer_name CLOSE_PARENTHESIS;
array_of_facilities: OPEN_PARENTHESIS (facility COMMA WHITESPACE*)* facility CLOSE_PARENTHESIS;
array_of_teaching_hours: OPEN_PARENTHESIS (teaching_hour COMMA WHITESPACE*)* teaching_hour CLOSE_PARENTHESIS;

fixed_schedule: FIXED SCHEDULE class_id WHITESPACE* array_of_teaching_hours;
pair_of_class_id: class_id COLON class_id;
non_conflict: NONCONFLICT OPEN_PARENTHESIS (pair_of_class_id COMMA WHITESPACE*)* pair_of_class_id CLOSE_PARENTHESIS;
unavailable: UNAVAILABLE array_of_teaching_hours;
teaching_duration_limit: TEACHING DURATION LIMIT duration;

defineClassroom
    : CLASSROOM WHITESPACE* room_id WHITESPACE* capacity WHITESPACE* array_of_facilities SEMICOLON
    ;
defineLecturer
    : LECTURER WHITESPACE* lecturer_name WHITESPACE* array_of_teaching_hours SEMICOLON
    ;
defineClass
    : CLASS WHITESPACE* class_id WHITESPACE* array_of_lecturers WHITESPACE* attendees_count WHITESPACE* array_of_facilities WHITESPACE* duration SEMICOLON
    ;
defineConstraint
    : CONSTRAINT (fixed_schedule | non_conflict | unavailable | teaching_duration_limit) SEMICOLON
    ;
definePreference
    : PREFERENCE (fixed_schedule | non_conflict | unavailable | teaching_duration_limit) SEMICOLON
    ;
defineSchedule
    : SCHEDULE SEMICOLON
    ;
eval
	:	((defineClassroom | defineLecturer | defineClass | defineConstraint | definePreference | defineSchedule) WHITESPACE*)* EOF
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
fragment LOWERCASE : [a-z];
fragment UPPERCASE : [A-Z];
OPEN_PARENTHESIS : '[';
CLOSE_PARENTHESIS : ']';
COMMA : ',';
COLON : ':';
WHITESPACE: (' ' | '\r' | '\n')+;
SEMICOLON: ';';
CLASSROOM : C L A S S R O O M;
LECTURER : L E C T U R E R;
CLASS : C L A S S;
PREFERENCE : P R E F E R E N C E;
CONSTRAINT : C O N S T R A I N T;
SCHEDULE : S C H E D U L E;
FIXED : F I X E D;
NONCONFLICT : N O N DASH C O N F L I C T;
UNAVAILABLE : U N A V A I L A B L E;
TEACHING : T E A C H I N G;
DURATION : D U R A T I O N;
LIMIT : L I M I T;
NUMBER: DIGIT+;
WORD : (LOWERCASE | UPPERCASE)+;
CLASS_ID : ( UPPERCASE | DIGIT )+;
ALPHANUMERIC: (LOWERCASE | UPPERCASE | DIGIT)+;
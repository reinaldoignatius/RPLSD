# Zadwal Language

### Description
Zadwal Language is a Domain Specific Language that can be used to schedule classes.

### Language Vocabulary:

- **room_id**, alphanumeric to identify classroom, e.g. R7602, TVSTB
- **capacity**, number to define classroom/class capacity in person, e.g. 40, 50, 100
- **facilities**, list of word to define facilities that exist/needed, e.g. [projector, ac]
- **lecturer_id**, string to identify lecturer, e.g. YA, BY
- **schedule_list**, list of number that define schedule, e.g.  [11,22]. We use ITB standard encoding to define schedule with syntax <day_id><hour_id>. 11 mean Monday 07.00 A.M., 53 mean Friday 09.00 A.M. e.g.
- **class_id**, alphanumeric to identify class.
- **duration**, number in hour to define how long class conducted per week.

### Language Grammar: 

#### Classroom definition
Define classroom that available in your school/university. 
```$xslt
CLASSROOM <room_id> <capacity> <facilities> 
```

#### Lecturer Availability Definition
Define lecturer availability.
```$xslt
LECTURER <lecturer_id> <schedule_list>
```

#### Class Definititon
Define class.
```$xslt
CLASS <class_id> <list_of_lecturer_id> <capacity> <facilities> <duration> 
```
#### Preferences & Constraint

- Non-conflicting constraint/preference
```$xslt
PREFERENCE NON-CONFLICT [<class_id-1>:<class_id-2>, ...]
``` 
- Fixed schedule constraint/preference
```$xslt
CONSTRAINT FIXED SCHEDULE <class_id> <schedule_list> 
```

- Unavailable schedule constraint/preference
```$xslt
CONSTRAINT UNAVAILABLE <schedule_list>
```

- Duration limit for each lecturer constraint/preference
```$xslt
CONSTRAINT TEACHING DURATION LIMIT <duration>
```

#### Schedule
Run scheduler and print the result! 

## Development

Build:
```$xslt
./gradlew build
```

Run:
```$xslt
./gradlew run
```

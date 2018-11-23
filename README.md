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
- **number_of_days**, number in day to define how many working days conducted per week.
- **days_name_list**, list of word to define names of working days to be displayed, e.g. [M, T, W, T, F]
- **work_hour_duration**, number in hour to define how long a working day lasted.
- **class_duration**, number in hour to customized how long a class period lasted.
- **start_time**, number in hour or numbers separated by colon to customized start time of a working day, e.g. 7, 07:15.
- **class_list**, list of class identifier

### Language Grammar: 

#### Number of Working Days definition
Define number of working days per week that set in your school/university. 
```$xslt
SET WORKDAY COUNT TO <number_of_days> (WITH NAMES <days_name_list>)?
```

#### Number of Working Hours definition
Define number of working hours per day in your school/university. 
```$xslt
SET WORKHOUR DURATION TO <work_hour_duration> (WITH EACH CLASS DURATION <class_duration>)? (START FROM <start_time>)? 
```

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

#### Class Definition
Define class.
```$xslt
CLASS <class_id> <list_of_lecturer_id> <capacity> <facilities> <duration> 
```

#### Preferences and Constraints

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

- Parallel classes constraint/preference
```$xslt
CONSTRAINT PARALLEL [<class_id-1>:<class_id-2>, ...]
```

#### Schedule
Run scheduler
```$xslt
SCHEDULE
```

#### PRINT SCHEDULE
Display the result of the schedule arranged by the scheduler
```$xslt
PRINT SCHEDULE FOR (ALL | LECTURER <lecturer_id> | CLASS <class_list>)
```

## Development

Build:
```$xslt
./gradlew build
```

Run:
```$xslt
./gradlew run
```

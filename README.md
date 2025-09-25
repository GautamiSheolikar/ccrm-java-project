# Campus Course & Records Manager (CCRM)

Console-based Java SE application for managing students, courses, enrollments, grades, import/export, and backups.

## How to Run
- JDK 17+
- On Windows PowerShell:
```
javac -d out $(Get-ChildItem -Recurse src/*.java | % { $_.FullName })
java -cp out edu.ccrm.CCRMMain
```

## Evolution of Java (very short)
- 1995: Java 1.0, applets
- 2004: Java 5, generics, enums
- 2014: Java 8, lambdas, streams
- 2017+: JPMS, records, pattern matching (ongoing)

## Java ME vs SE vs EE
- ME: embedded/mobile profiles
- SE: desktop/server core APIs (this project)
- EE/Jakarta: enterprise specs (JPA, EJB, Servlets)

## JDK/JRE/JVM
- JVM executes bytecode; JRE = JVM + libraries; JDK = JRE + tools (javac, jar)

## Install Java on Windows
- Download JDK, set PATH, verify with `java -version` and `javac -version`.

## Eclipse Setup
- New Java Project → Import existing sources → Run `edu.ccrm.CCRMMain`.

## Assertions
- Enable with `-ea`. Example: `java -ea -cp out edu.ccrm.CCRMMain`

## Demo Flow
- AppConfig (Singleton) initializes data folder.
- Import students/courses from `test_data/*.csv`.
- CLI menu: list, enroll, export, backup, transcript/GPA, reports.

## Mapping: syllabus → code
- OOP (Encapsulation/Inheritance/Abstraction/Polymorphism): `domain/*` (`Person`, `Student`, `Instructor`, `Searchable`)
- Enums: `Semester`, `Grade`
- Builder: `Course.Builder`
- Singleton: `config/AppConfig`
- Exceptions: `DuplicateEnrollmentException`, `MaxCreditLimitExceededException`
- Streams: filters in `CourseService`, GPA calc in `EnrollmentService`
- NIO.2: `io/ImportExportService`, `io/BackupService`
- Date/Time: `Person.createdAt`, `Enrollment.enrolledAt`
- Interfaces: `Persistable`, `Searchable`
- Anonymous inner class: CLI reports (option 6)
- Nested class: `CourseService.ByCreditsThenTitleComparator`
- Immutable value: `Name`
- Arrays & Operators demo: `util.DemoUtils.arraysAndOperatorsDemo()`

## Screenshots
- Place screenshots in `screenshots/` (installation, Eclipse run, CLI, backups)

## Acknowledgements
- Standard Java documentation.



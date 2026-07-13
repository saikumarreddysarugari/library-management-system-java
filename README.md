# Library Management System (Java)

A console-based library management system built in core Java. Demonstrates
object-oriented design, collections, custom exception handling, and file-based
data persistence (no external database required to run).

## Features
- Add books and members
- Issue and return books, with checks for availability and valid members
- Search the catalog by title keyword
- List all books / all members
- Persists data to `books.csv` and `members.csv` so it survives restarts
- Custom checked exceptions: `BookNotFoundException`, `BookNotAvailableException`, `MemberNotFoundException`

## Project Structure
```
src/
  Book.java
  Member.java
  Library.java
  BookNotFoundException.java
  BookNotAvailableException.java
  MemberNotFoundException.java
  Main.java
```

## How to Compile & Run

You'll need a JDK installed (Java 8+ is fine). Check with:
```
java -version
javac -version
```

From inside the `LibraryManagementSystem` folder:

```bash
cd src
javac *.java
java Main
```

The program seeds two sample books and one sample member on first run.
Follow the on-screen menu (1-8) to add books/members, issue/return books,
search, and list records. Choose option 8 to save your data and exit —
it writes `books.csv` and `members.csv` in the same folder, which will be
reloaded automatically the next time you run it.

## Suggested Resume Bullet Points

- Built a Java console application to manage a library's book catalog and
  member loans, using object-oriented design (Book, Member, Library classes)
  and Java Collections (Map, List) for in-memory data management.
- Implemented custom checked exceptions to handle invalid ISBNs, unavailable
  books, and unknown members, ensuring predictable error handling across the
  application.
- Designed a file-based persistence layer (Java I/O) to save and reload
  catalog and membership data between sessions using a structured CSV format.

## Ideas to Extend (if you want to go further before applying)
- Swap the CSV persistence for real JDBC + MySQL/SQLite (matches the SQL
  skill already on your resume and makes this a genuine full-stack-adjacent project).
- Add a simple Swing or JavaFX GUI instead of the console menu.
- Push this to GitHub as a real, working public repo you can link from your resume.

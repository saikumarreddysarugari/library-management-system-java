import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Core business logic for the library: manages the book catalog and members,
 * handles issuing/returning books, and persists state to plain text files
 * so data survives between program runs.
 */
public class Library {
    private Map<String, Book> catalog = new LinkedHashMap<>();
    private Map<String, Member> members = new LinkedHashMap<>();

    private static final String BOOKS_FILE = "books.csv";
    private static final String MEMBERS_FILE = "members.csv";

    public void addBook(Book book) {
        catalog.put(book.getIsbn(), book);
    }

    public void addMember(Member member) {
        members.put(member.getMemberId(), member);
    }

    public Book findBookByIsbn(String isbn) throws BookNotFoundException {
        Book book = catalog.get(isbn);
        if (book == null) {
            throw new BookNotFoundException("No book found with ISBN: " + isbn);
        }
        return book;
    }

    public Member findMember(String memberId) throws MemberNotFoundException {
        Member member = members.get(memberId);
        if (member == null) {
            throw new MemberNotFoundException("No member found with ID: " + memberId);
        }
        return member;
    }

    public List<Book> searchByTitle(String keyword) {
        List<Book> results = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase();
        for (Book book : catalog.values()) {
            if (book.getTitle().toLowerCase().contains(lowerKeyword)) {
                results.add(book);
            }
        }
        return results;
    }

    public void issueBook(String isbn, String memberId)
            throws BookNotFoundException, MemberNotFoundException, BookNotAvailableException {
        Book book = findBookByIsbn(isbn);
        Member member = findMember(memberId);

        if (book.getAvailableCopies() <= 0) {
            throw new BookNotAvailableException(
                    "'" + book.getTitle() + "' currently has no available copies.");
        }
        book.decrementAvailable();
        member.borrowBook(isbn);
    }

    public void returnBook(String isbn, String memberId)
            throws BookNotFoundException, MemberNotFoundException {
        Book book = findBookByIsbn(isbn);
        Member member = findMember(memberId);

        if (!member.getBorrowedIsbns().contains(isbn)) {
            throw new IllegalStateException(
                    member.getName() + " does not currently have '" + book.getTitle() + "' on loan.");
        }
        book.incrementAvailable();
        member.returnBook(isbn);
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(catalog.values());
    }

    public List<Member> getAllMembers() {
        return new ArrayList<>(members.values());
    }

    /** Persists both the catalog and member records to CSV files on disk. */
    public void saveToFile() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BOOKS_FILE))) {
            for (Book book : catalog.values()) {
                writer.write(book.toCsvLine());
                writer.newLine();
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(MEMBERS_FILE))) {
            for (Member member : members.values()) {
                writer.write(member.toCsvLine());
                writer.newLine();
            }
        }
    }

    /** Loads catalog and member records from disk if the files exist. Safe to call on first run. */
    public void loadFromFile() throws IOException {
        loadBooks();
        loadMembers();
    }

    private void loadBooks() throws IOException {
        java.io.File file = new java.io.File(BOOKS_FILE);
        if (!file.exists()) {
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    Book book = Book.fromCsvLine(line);
                    catalog.put(book.getIsbn(), book);
                }
            }
        }
    }

    private void loadMembers() throws IOException {
        java.io.File file = new java.io.File(MEMBERS_FILE);
        if (!file.exists()) {
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    Member member = Member.fromCsvLine(line);
                    members.put(member.getMemberId(), member);
                }
            }
        }
    }
}

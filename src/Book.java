/**
 * Represents a single book title in the library catalog.
 * Tracks total copies owned versus copies currently available to borrow.
 */
public class Book {
    private String isbn;
    private String title;
    private String author;
    private int totalCopies;
    private int availableCopies;

    public Book(String isbn, String title, String author, int totalCopies) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void incrementAvailable() {
        if (availableCopies < totalCopies) {
            availableCopies++;
        }
    }

    public void decrementAvailable() {
        if (availableCopies > 0) {
            availableCopies--;
        }
    }

    /**
     * Serializes this book to a single CSV line for file persistence.
     * Format: isbn,title,author,totalCopies,availableCopies
     */
    public String toCsvLine() {
        return isbn + "," + title + "," + author + "," + totalCopies + "," + availableCopies;
    }

    /**
     * Reconstructs a Book from a CSV line previously written by toCsvLine().
     */
    public static Book fromCsvLine(String line) {
        String[] parts = line.split(",");
        Book book = new Book(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]));
        int available = Integer.parseInt(parts[4]);
        // Adjust available copies to match saved state
        while (book.getAvailableCopies() > available) {
            book.decrementAvailable();
        }
        return book;
    }

    @Override
    public String toString() {
        return String.format("%-12s %-30s %-20s %d/%d available",
                isbn, title, author, availableCopies, totalCopies);
    }
}

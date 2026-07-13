import java.util.List;
import java.util.Scanner;

/**
 * Console entry point for the Library Management System.
 * Presents a menu-driven interface to manage books, members, and loans.
 */
public class Main {

    public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);

        try {
            library.loadFromFile();
        } catch (Exception e) {
            System.out.println("Could not load saved data, starting fresh: " + e.getMessage());
        }

        seedSampleDataIfEmpty(library);

        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    handleAddBook(library, scanner);
                    break;
                case "2":
                    handleAddMember(library, scanner);
                    break;
                case "3":
                    handleIssueBook(library, scanner);
                    break;
                case "4":
                    handleReturnBook(library, scanner);
                    break;
                case "5":
                    handleSearchBook(library, scanner);
                    break;
                case "6":
                    printAllBooks(library);
                    break;
                case "7":
                    printAllMembers(library);
                    break;
                case "8":
                    saveAndExit(library);
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please choose 1-8.");
            }
        }

        scanner.close();
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("===== Library Management System =====");
        System.out.println("1. Add Book");
        System.out.println("2. Add Member");
        System.out.println("3. Issue Book");
        System.out.println("4. Return Book");
        System.out.println("5. Search Book by Title");
        System.out.println("6. List All Books");
        System.out.println("7. List All Members");
        System.out.println("8. Save & Exit");
        System.out.print("Choose an option: ");
    }

    private static void handleAddBook(Library library, Scanner scanner) {
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine().trim();
        System.out.print("Title: ");
        String title = scanner.nextLine().trim();
        System.out.print("Author: ");
        String author = scanner.nextLine().trim();
        System.out.print("Total copies: ");
        int copies = readInt(scanner);

        library.addBook(new Book(isbn, title, author, copies));
        System.out.println("Book added: " + title);
    }

    private static void handleAddMember(Library library, Scanner scanner) {
        System.out.print("Member ID: ");
        String id = scanner.nextLine().trim();
        System.out.print("Name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();

        library.addMember(new Member(id, name, email));
        System.out.println("Member added: " + name);
    }

    private static void handleIssueBook(Library library, Scanner scanner) {
        System.out.print("ISBN to issue: ");
        String isbn = scanner.nextLine().trim();
        System.out.print("Member ID: ");
        String memberId = scanner.nextLine().trim();

        try {
            library.issueBook(isbn, memberId);
            System.out.println("Book issued successfully.");
        } catch (BookNotFoundException | MemberNotFoundException | BookNotAvailableException e) {
            System.out.println("Could not issue book: " + e.getMessage());
        }
    }

    private static void handleReturnBook(Library library, Scanner scanner) {
        System.out.print("ISBN to return: ");
        String isbn = scanner.nextLine().trim();
        System.out.print("Member ID: ");
        String memberId = scanner.nextLine().trim();

        try {
            library.returnBook(isbn, memberId);
            System.out.println("Book returned successfully.");
        } catch (BookNotFoundException | MemberNotFoundException | IllegalStateException e) {
            System.out.println("Could not return book: " + e.getMessage());
        }
    }

    private static void handleSearchBook(Library library, Scanner scanner) {
        System.out.print("Keyword: ");
        String keyword = scanner.nextLine().trim();
        List<Book> results = library.searchByTitle(keyword);

        if (results.isEmpty()) {
            System.out.println("No books matched that search.");
        } else {
            results.forEach(System.out::println);
        }
    }

    private static void printAllBooks(Library library) {
        List<Book> books = library.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("Catalog is empty.");
        } else {
            books.forEach(System.out::println);
        }
    }

    private static void printAllMembers(Library library) {
        List<Member> members = library.getAllMembers();
        if (members.isEmpty()) {
            System.out.println("No members registered.");
        } else {
            members.forEach(System.out::println);
        }
    }

    private static void saveAndExit(Library library) {
        try {
            library.saveToFile();
            System.out.println("Data saved. Goodbye!");
        } catch (Exception e) {
            System.out.println("Warning: failed to save data - " + e.getMessage());
        }
    }

    private static int readInt(Scanner scanner) {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }

    /** Adds a few starter records on first run so the menu isn't empty to demo. */
    private static void seedSampleDataIfEmpty(Library library) {
        if (library.getAllBooks().isEmpty()) {
            library.addBook(new Book("978-0134685991", "Effective Java", "Joshua Bloch", 3));
            library.addBook(new Book("978-0596009205", "Head First Design Patterns", "Freeman & Robson", 2));
        }
        if (library.getAllMembers().isEmpty()) {
            library.addMember(new Member("M001", "Asha Rao", "asha.rao@example.com"));
        }
    }
}

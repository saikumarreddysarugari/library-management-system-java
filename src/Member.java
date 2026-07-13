import java.util.ArrayList;
import java.util.List;

/**
 * Represents a library member and the books they currently have on loan.
 */
public class Member {
    private String memberId;
    private String name;
    private String email;
    private List<String> borrowedIsbns;

    public Member(String memberId, String name, String email) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.borrowedIsbns = new ArrayList<>();
    }

    public String getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public List<String> getBorrowedIsbns() {
        return borrowedIsbns;
    }

    public void borrowBook(String isbn) {
        borrowedIsbns.add(isbn);
    }

    public void returnBook(String isbn) {
        borrowedIsbns.remove(isbn);
    }

    public String toCsvLine() {
        return memberId + "," + name + "," + email + "," + String.join(";", borrowedIsbns);
    }

    public static Member fromCsvLine(String line) {
        String[] parts = line.split(",", -1);
        Member member = new Member(parts[0], parts[1], parts[2]);
        if (parts.length > 3 && !parts[3].isEmpty()) {
            for (String isbn : parts[3].split(";")) {
                member.borrowBook(isbn);
            }
        }
        return member;
    }

    @Override
    public String toString() {
        return String.format("%-10s %-20s %-25s Borrowed: %s",
                memberId, name, email, borrowedIsbns.isEmpty() ? "none" : borrowedIsbns);
    }
}

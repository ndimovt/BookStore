package bookstoreV2;

public class Book {
    private String bookName;
    private String authorName;
    private int year;
    private double price;
    @Override
    public String toString() {
        return "AuthorName: " + authorName + "/ BookName: " + bookName + "/ Year of release: " + year + "/ Price: " + price;
    }
    public Book(String authorname, String bookName, int year, double price) {
        this.authorName = authorname;
        this.bookName = bookName;
        this.year = year;
        this.price = price;
    }

    public String getBookName() {
        return bookName;
    }
    public double setPrice(double price) {
        this.price = price;
        return price;
    }
}

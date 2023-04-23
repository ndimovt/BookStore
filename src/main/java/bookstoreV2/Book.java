package bookstoreV2;

public class Book {
    private String bookName;
    private String authorName;
    private int year;
    private double price;
    @Override
    public String toString() {
        return "Book{" +
                "bookName='" + bookName + '\'' +
                ", year=" + year +
                ", price=" + price +
                '}';
    }
    public Book(String bookName, int year, double price) {
        this.bookName = bookName;
        this.year = year;
        this.price = price;
    }

    public String getBookName() {
        return bookName;
    }
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
    public String getAuthorName() {
        return authorName;
    }
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
}

package bookstoreV2;

import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.InputMismatchException;
public class GetDataFromDB {
    private static Connection con;
    private static GetDataFromDB gdfDB;

    private GetDataFromDB() {
    }
    public static GetDataFromDB getInstance() {
        if (gdfDB == null) {
            gdfDB = new GetDataFromDB();
        }
        return gdfDB;
    }
    private static Connection getConnection(){
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstore", "root", "sheeuser123456@");
        }catch (SQLException e){
            System.out.println("Connection error!");
            e.printStackTrace();
        }
        return con;
    }
    protected void addBookToDB(String bName, String aName, int year, double bookPrice){
        try (Connection DBconnection = this.getConnection();
             PreparedStatement ps = DBconnection.prepareStatement("call bookstore.add_book(?,?,?,?)"))
        {
            ps.setString(1, aName);
            ps.setString(2, bName);
            ps.setInt(3, year);
            ps.setDouble(4, bookPrice);
            ps.executeUpdate();
        } catch (SQLException sqe){
            System.out.println("Connection error!");
            sqe.printStackTrace();
        }catch (InputMismatchException imme){
            System.out.println("Wrong value entered");
            imme.printStackTrace();
        }
    }
    protected ArrayList<Book> bookInfo(){
        ArrayList<Book> bookData = new ArrayList<>();
        try (Connection c = this.getConnection();
             PreparedStatement pst = c.prepareStatement(" call bookstore.get_info()");
             ResultSet rs = pst.executeQuery())
        {
            while (rs.next()) {
                Book book = new Book(rs.getString("author_name"), rs.getString("book_title"), rs.getInt("release_year"), rs.getDouble("price"));
                bookData.add(book);
            }
        } catch (SQLException SE){
            SE.printStackTrace();
        }
        return bookData;
    }
    protected void showInfo() {
        ArrayList<Book> bookData = bookInfo();
        for (Book b : bookData) {
            System.out.println(b.toString());
        }
    }
    protected ArrayList<Book> addChangedBookToDB(String bookTitle, double updatedPrice){
        ArrayList<Book> bookData = bookInfo();
        for (Book changedBook : bookData) {
            if (bookTitle.equals(changedBook.getBookName())) {
                try (Connection DBCon =  this.getConnection();
                     PreparedStatement pst = DBCon.prepareStatement("call bookstore.change_price(?,?)"))
                {
                    pst.setString(1, bookTitle);
                    pst.setDouble(2, changedBook.setPrice(updatedPrice));
                    pst.executeUpdate();
                }catch (SQLException sqe){
                    System.out.println("Connection error!");
                    sqe.printStackTrace();
                }
            }
        }
        return bookData;
    }
}

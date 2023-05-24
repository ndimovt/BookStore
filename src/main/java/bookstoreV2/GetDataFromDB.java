package bookstoreV2;

import java.sql.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
public class GetDataFromDB {
    private static GetDataFromDB gdfDB;

    private GetDataFromDB() {
    }
    public static GetDataFromDB getInstance() {
        if (gdfDB == null) {
            gdfDB = new GetDataFromDB();
        }
        return gdfDB;
    }
    private static Connection getConnection() throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstore", "root", "sheeuser123456@");
        return con;
    }
    protected void addBookToDB(String bName, String aName, int year, double bookPrice) throws SQLException, InputMismatchException {
        Connection DBconnection = null;
        PreparedStatement ps = null;
        try {
            DBconnection = this.getConnection();
            ps = DBconnection.prepareStatement("INSERT INTO book_info(author_name,book_title,release_year,price) VALUES (?,?,?,?)");
            ps.setString(1, aName);
            ps.setString(2, bName);
            ps.setInt(3, year);
            ps.setDouble(4, bookPrice);
            ps.executeUpdate();
        } finally {
            closeConnection(DBconnection,ps,null);
        }
    }
    protected ArrayList<Book> bookInfo(){
        ArrayList<Book> bookData = new ArrayList<>();
        Connection c = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            c = this.getConnection();
            pst = c.prepareStatement("""
                    SELECT bf.book_title, bf.release_year,bf.price
                    FROM book_info bf
                    """);
            rs = pst.executeQuery();
            while (rs.next()) {
                Book book = new Book(rs.getString("book_title"), rs.getInt("release_year"), rs.getDouble("price"));
                bookData.add(book);
            }
        } catch (SQLException SE){
            SE.printStackTrace();
        } finally{
            closeConnection(c,pst, rs);
        }
        return bookData;
    }
    protected void showInfo() {
        ArrayList<Book> bookData = bookInfo();
        for (Book b : bookData) {
            System.out.println(b.toString());
        }
    }
    protected ArrayList<Book> addChangedBookToDB(String bookTitle, double updatedPrice) throws SQLException {
        ArrayList<Book> bookData = bookInfo();
        for (Book changedBook : bookData) {
            if (bookTitle.equals(changedBook.getBookName())) {
                Connection DBCon = null;
                PreparedStatement pst = null;
                try {
                    DBCon = this.getConnection();
                    pst = DBCon.prepareStatement("UPDATE book_info SET price = ? WHERE book_title = ?");
                    pst.setDouble(1, changedBook.setPrice(updatedPrice));
                    pst.setString(2, bookTitle);
                    pst.executeUpdate();
                } finally {
                    closeConnection(DBCon,pst,null);
                }
            }
        }
        return bookData;
    }
    private void closeConnection(Connection con, PreparedStatement pst, ResultSet re) {
        try {
            if (con != null) {
                con.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (re != null) {
                re.close();
            }
        } catch (SQLException E) {
            System.out.println("No con");
        }
    }
}

package bookstoreV2;

import java.sql.*;
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
        Connection DBconnection = null;
        PreparedStatement ps = null;
        try {
            DBconnection = this.getConnection();
            ps = DBconnection.prepareStatement("call bookstore.add_book(?,?,?,?)");
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
        }finally {
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
            pst = c.prepareStatement(" call bookstore.get_info()");
            rs = pst.executeQuery();
            while (rs.next()) {
                Book book = new Book(rs.getString("author_name"), rs.getString("book_title"), rs.getInt("release_year"), rs.getDouble("price"));
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
    protected ArrayList<Book> addChangedBookToDB(String bookTitle, double updatedPrice){
        ArrayList<Book> bookData = bookInfo();
        for (Book changedBook : bookData) {
            if (bookTitle.equals(changedBook.getBookName())) {
                Connection DBCon = null;
                PreparedStatement pst = null;
                try {
                    DBCon = this.getConnection();
                    pst = DBCon.prepareStatement("call bookstore.change_price(?,?)");
                    pst.setString(1, bookTitle);
                    pst.setDouble(2, changedBook.setPrice(updatedPrice));
                    pst.executeUpdate();
                }catch (SQLException sqe){
                    System.out.println("Connection error!");
                    sqe.printStackTrace();
                }finally {
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
            System.out.println("No connection");
        }
    }
}

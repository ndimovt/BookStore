package bookstoreV2;

import java.sql.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
public class GetDataFromDB {
    private ArrayList<Book> bookData = new ArrayList<>();
    private static GetDataFromDB gdfDB;
    private GetDataFromDB() {
    }
    public static GetDataFromDB getInstance(){
        if(gdfDB == null){
            gdfDB = new GetDataFromDB();
        }
        return gdfDB;
    }
    private static Connection getConnection()throws SQLException{
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstore", "root", "sheeuser123456@");
        return con;
    }
    protected void addBookToDB(String bName, String aName, int year, double bookPrice) throws SQLException, InputMismatchException{
        Connection DBconnection = null;
        PreparedStatement ps = null;
        try{
            DBconnection = this.getConnection();
            ps = DBconnection.prepareStatement("INSERT INTO book_info(author_name,book_title,release_year,price) VALUES (?,?,?,?)");
            ps.setString(1,aName);
            ps.setString(2,bName);
            ps.setInt(3,year);
            ps.setDouble(4,bookPrice);
            ps.executeUpdate();
        }finally {
            if(DBconnection != null){
                DBconnection.close();
            }if(ps != null){
                ps.close();
            }
        }
    }
    protected Book bookInfo(String authorName)throws SQLException, InputMismatchException {
        Book book = null;
            Connection c = null;
            PreparedStatement pst = null;
            ResultSet rs = null;
            try {
                c = this.getConnection();
                pst = c.prepareStatement("""
                        SELECT bf.book_title, bf.release_year,bf.price
                        FROM book_info bf
                        WHERE bf.author_name = ?
                        """);
                pst.setString(1,authorName);
                rs = pst.executeQuery();
                while (rs.next()) {
                    book = new Book(rs.getString("book_title"),rs.getInt("release_year"), rs.getDouble("price"));
                    System.out.println(book);
                }

            } finally {
                if (c != null) {
                    c.close();
                }
                if (pst != null) {
                    pst.close();
                }
                if (rs != null) {
                    rs.close();
                }
        }
        return book;
    }
    protected ArrayList<Book> addBook (Book b){
        bookData.add(b);
        return bookData;
    }
    protected Book changeBookPrice (String bName, double newPrice){
        for (Book b : bookData) {
            if (b.getBookName().equals(bName)){
                b.setPrice(newPrice);
                return b;
            }
        }
        return null;
    }
    protected void addChangedBookToDB(String bookTitle)throws SQLException{
        Connection DBCon = null;
        PreparedStatement pst = null;
        try{
            DBCon = this.getConnection();
            pst = DBCon.prepareStatement("UPDATE book_info SET price = ? WHERE book_title = ?");
            for(Book b : bookData) {
                pst.setDouble(1, b.getPrice());
                pst.setString(2,bookTitle);
                pst.executeUpdate();
            }
        }finally {
            if(DBCon != null){
                DBCon.close();
            }if(pst != null){
                pst.close();
            }
        }
    }
}

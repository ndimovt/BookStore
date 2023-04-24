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
    protected ArrayList<Book> bookInfo() throws SQLException{
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
                    Book book = new Book(rs.getString("book_title"),rs.getInt("release_year"), rs.getDouble("price"));
                    bookData.add(book);
                }
            }finally{
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
        return bookData;
    }
    protected void showInfo(){
        for(Book b : bookData){
            System.out.println(b.toString());
        }
    }
    protected ArrayList<Book> addChangedBookToDB(String bookTitle, double updatedPrice)throws SQLException {
        for (Book changedBook : bookData) {
            if (bookTitle.equals(changedBook.getBookName())) {
                Connection DBCon = null;
                PreparedStatement pst = null;
                try {
                    DBCon = this.getConnection();
                    pst = DBCon.prepareStatement("UPDATE book_info SET price = ? WHERE book_title = ?");
                    pst.setDouble(1,changedBook.setPrice(updatedPrice));
                    pst.setString(2,bookTitle);
                    pst.executeUpdate();
                } finally {
                    if (DBCon != null) {
                        DBCon.close();
                    }
                    if (pst != null) {
                        pst.close();
                    }
                }
            }else {
                System.out.println("Book "+bookTitle+" does not exist in Database");
            }
        }
        return bookData;
    }
}

package bookstoreV2;

import java.sql.SQLException;
public class Main {
    public static void main(String[] args) {
        GetDataFromDB gd = GetDataFromDB.getInstance();
        try {
            gd.bookInfo();
            gd.showInfo();
            gd.addChangedBookToDB("Sea Wolf",10.12);
            gd.showInfo();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
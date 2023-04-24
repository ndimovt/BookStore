package bookstoreV2;

import java.sql.SQLException;
public class Main {
    public static void main(String[] args) {
        GetDataFromDB gd = GetDataFromDB.getInstance();
        try {
            gd.bookInfo();
            gd.showInfo();
            //gd.addChangedBookToDB("Whirlwind",16.32);
            //gd.showInfo();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
package bookstoreV2;

import java.sql.SQLException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        GetDataFromDB gd = GetDataFromDB.getInstance();
        try {
            gd.bookInfo();
            gd.showInfo();
            gd.addBookToDB("Beyond the Dark Portal","Aaron Rosenberg & Christie Golden",2008,9.12);
            gd.showInfo();
        } catch (SQLException e) {
           e.printStackTrace();
        }

    }
}
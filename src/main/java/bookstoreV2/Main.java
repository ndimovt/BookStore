package bookstoreV2;

import java.sql.SQLException;
public class Main {
    public static void main(String[] args) {
        GetDataFromDB gd = GetDataFromDB.getInstance();
        try {
            gd.addBook(gd.bookInfo("James Clavel"));
            //gd.changeBookPrice("Whirlwind",6.50);
            //gd.addChangedBookToDB("Whirlwind");

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
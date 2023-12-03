package bookstoreV2;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner inn = new Scanner(System.in);
        GetDataFromDB gd = GetDataFromDB.getInstance();
        System.out.println("Welcome to the city bookstore");
        System.out.println("Choose option from the menu:");
        boolean isTrue = true;
        while (isTrue) {
            System.out.println("1)Show complete book collection     2)Change book price     3)Add new book      4)Exit");
            int ch = inn.nextInt();
            switch (ch){
                case 1:
                    gd.showInfo();
                    break;
                case 2:
                    System.out.println("Enter book title:");
                    String title = inn.nextLine();
                    System.out.println("Enter new Price");
                    double price = inn.nextDouble();
                    gd.addChangedBookToDB(title, price);
                    break;
                case 3:
                    System.out.println("Enter author name" );
                    String authorName = inn.nextLine();
                    System.out.println("Enter book name");
                    String bookName = inn.nextLine();
                    System.out.println("Year of release");
                    int year = inn.nextInt();
                    System.out.println("Enter price");
                    double newPrice = inn.nextDouble();
                    gd.addBookToDB(bookName, authorName, year, newPrice);
                    break;
                case 4:
                    isTrue = false;
                    break;
                default:
                    break;
            }
        }
    }
}
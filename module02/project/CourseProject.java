import java.util.Scanner;

/**
 * This program will reverse a number entered by the user using a while loop.
 * By Leonardo Vallejos
 */
public class CourseProject {

   // get user choice
   public static int getMenuOption() {
      int choice = 0;

      Scanner input = new Scanner(System.in);

      // get the number to be reversed
      choice = input.nextInt();

      return choice;         
   }

   // main menu options
   public static void printMainMenu() {
      System.out.println("\n\nMain Menu");
      System.out.println("---------------------------");
      System.out.println("1. Manage Users");
      System.out.println("2. Manage Stock");
      System.out.println("3. Manage Stock Holdings");
      System.out.println("4. List Users");
      System.out.println("5. View Users");
      System.out.println("6. Run Utility Program");
      System.out.println("7. Exit Program");
      System.out.println("---------------------------");
      System.out.print("> Select an option: ");
   }

   // users menu options
   public static void printUserMenu() {
      System.out.println("\n\nManage Users");
      System.out.println("---------------------------");
      System.out.println("1. Add new User");
      System.out.println("2. Edit User");
      System.out.println("3. Delete User");
      System.out.println("4. Return to Main Screen");
      System.out.println("---------------------------");
      System.out.print("> Select an option: ");
   }

   // stocks menu options
   public static void printStockMenu() {
      System.out.println("\n\nManage Stocks");
      System.out.println("---------------------------");
      System.out.println("1. Add new Stock");
      System.out.println("2. View Stock");
      System.out.println("3. Return to Main Screen");
      System.out.println("---------------------------");
      System.out.print("> Select an option: ");
   }

   // stock holding menu options
   public static void printStockHoldingMenu() {
      System.out.println("\n\nManage Stock Holding");
      System.out.println("---------------------------");
      System.out.println("1. Add Stock Holding");
      System.out.println("2. Delete Stock Holding");
      System.out.println("3. Return to Main Screen");
      System.out.println("---------------------------");
      System.out.print("> Select an option: ");
   }

   public static void main(String[] args) {
      // declare needed variables
      boolean quitApp = false;
      int choice = 0;
      
      while (!quitApp) {
         printMainMenu();
         choice = getMenuOption();

         switch (choice) {
            case 1:
               printUserMenu();
               int umChoice = getMenuOption();

               break;

            case 2:
               printStockMenu();
               int smChoice = getMenuOption();

               break;

            case 3:
               printStockHoldingMenu();
               int shmChoice = getMenuOption();
               
               break;

            case 4:
               System.out.println("\nList Users");
               break;

            case 5:
               System.out.println("\nView User");
               break;

            case 6:
               System.out.println("\nUtility Program");
               break;

            case 7:
               quitApp = true;
               break;
            default:
               System.out.println("\nIncorrect option. Try again.");
         } // switch

      } // while

      System.out.println("Bye...");

   } // /main

} // /class

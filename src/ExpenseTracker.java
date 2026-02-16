import java.util.*;
import java.io.*;

public class ExpenseTracker {

    static ArrayList<Expense> expenses = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);
    static final String FILE_NAME = "expenses.txt";

    public static void main(String[] args) {
        loadExpenses();

        while (true) {
            System.out.println("\n==== Expense Tracker ====");
            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. View Total Spending");
            System.out.println("4. Exit");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addExpense();
                    break;
                case 2:
                    viewExpenses();
                    break;
                case 3:
                    viewTotal();
                    break;
                case 4:
                    saveExpenses();
                    System.out.println("Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    static void addExpense() {
        System.out.print("Enter amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Enter category: ");
        String category = scanner.nextLine();

        System.out.print("Enter date (dd-mm-yyyy): ");
        String date = scanner.nextLine();

        Expense expense = new Expense(amount, category, date);
        expenses.add(expense);

        System.out.println("Expense added successfully!");
    }

    static void viewExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses found.");
            return;
        }

        for (Expense e : expenses) {
            System.out.println("Amount: " + e.getAmount() +
                    " | Category: " + e.getCategory() +
                    " | Date: " + e.getDate());
        }
    }

    static void viewTotal() {
        double total = 0;
        for (Expense e : expenses) {
            total += e.getAmount();
        }
        System.out.println("Total Spending: " + total);
    }

    static void saveExpenses() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Expense e : expenses) {
                writer.println(e);
            }
        } catch (IOException e) {
            System.out.println("Error saving data.");
        }
    }

    static void loadExpenses() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                expenses.add(new Expense(
                        Double.parseDouble(parts[0]),
                        parts[1],
                        parts[2]
                ));
            }
        } catch (IOException e) {
            System.out.println("No previous data found.");
        }
    }
}

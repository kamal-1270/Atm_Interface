import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class User {
    private String userId;
    private String pin;
    private double balance;

    public User(String userId, String pin, double balance) {
        this.userId = userId;
        this.pin = pin;
        this.balance = balance;
    }

    public String getUserId() {
        return userId;
    }

    public String getPin() {
        return pin;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}

class ATM {
    private Map<String, User> users;
    private User currentUser;

    public ATM() {
        users = new HashMap<>();
        users.put("kamal1270", new User("kamal1270", "1270", 10000.0));
        users.put("rahul123", new User("rahul123", "1234", 500.0));
        users.put("alok1246", new User("alok1246", "1246", 5000.0));
        users.put("vicky1261", new User("vicky1261", "1261", 1500.0));
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter User ID: ");
        String userId = scanner.next();

        System.out.print("Enter PIN: ");
        String pin = scanner.next();

        if (authenticateUser(userId, pin)) {
            System.out.println("Authentication successful. Welcome, " + currentUser.getUserId() + "!");
            showOptions(scanner);
        } else {
            System.out.println("Authentication failed. Exiting...");
        }
    }

    private boolean authenticateUser(String userId, String pin) {
        if (users.containsKey(userId)) {
            User user = users.get(userId);
            if (user.getPin().equals(pin)) {
                currentUser = user;
                return true;
            }
        }
        return false;
    }

    private void showOptions(Scanner scanner) {
        while (true) {
            System.out.println("1. Transactions History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");

            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Transactions History: Not implemented in this example.");
                    break;
                case 2:
                    System.out.print("Enter amount to withdraw: ");
                    double withdrawAmount = scanner.nextDouble();
                    withdraw(withdrawAmount);
                    break;
                case 3:
                    System.out.print("Enter amount to deposit: ");
                    double depositAmount = scanner.nextDouble();
                    deposit(depositAmount);
                    break;
                case 4:
                    System.out.print("Enter recipient's User ID: ");
                    String recipientUserId = scanner.next();
                    System.out.print("Enter amount to transfer: ");
                    double transferAmount = scanner.nextDouble();
                    transfer(recipientUserId, transferAmount);
                    break;
                case 5:
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please choose again.");
            }
        }
    }

    private void withdraw(double amount) {
        if (amount > 0 && amount <= currentUser.getBalance()) {
            currentUser.setBalance(currentUser.getBalance() - amount);
            System.out.println("Withdrawal successful. Remaining balance: " + currentUser.getBalance());
        } else {
            System.out.println("Invalid withdrawal amount or insufficient balance.");
        }
    }

    private void deposit(double amount) {
        if (amount > 0) {
            currentUser.setBalance(currentUser.getBalance() + amount);
            System.out.println("Deposit successful. New balance: " + currentUser.getBalance());
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    private void transfer(String recipientUserId, double amount) {
        if (amount > 0 && users.containsKey(recipientUserId) && !recipientUserId.equals(currentUser.getUserId())) {
            User recipient = users.get(recipientUserId);
            if (amount <= currentUser.getBalance()) {
                currentUser.setBalance(currentUser.getBalance() - amount);
                recipient.setBalance(recipient.getBalance() + amount);
                System.out.println("Transfer successful. Remaining balance: " + currentUser.getBalance());
            } else {
                System.out.println("Insufficient balance for transfer.");
            }
        } else {
            System.out.println("Invalid recipient User ID or transfer amount.");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        ATM atm = new ATM();
        atm.start();
    }
}

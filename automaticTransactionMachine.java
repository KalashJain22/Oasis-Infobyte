import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Users {
    private String userId;
    private String pin;

    public Users(String userId, String pin) {
        this.userId = userId;
        this.pin = pin;
    }

    public boolean authenticate(String inputId, String inputPin) {
        return this.userId.equals(inputId) && this.pin.equals(inputPin);
    }
}

class Transaction {
    String type;
    double amount;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return type + " of $" + amount;
    }
}

class BankAccount {
    private double balance;
    private List<Transaction> history;

    public BankAccount() {
        balance = 0.0;
        history = new ArrayList<>();
    }

    public void deposit(double amount) {
        balance += amount;
        history.add(new Transaction("Deposit", amount));
    }

    public boolean withdraw(double amount) {
        if(amount <= balance) {
            balance -= amount;
            history.add(new Transaction("Withdraw", amount));
            return true;
        }
        return false;
    }

    public boolean transfer(double amount, BankAccount receiver) {
        if (withdraw(amount)) {
            receiver.deposit(amount);
            history.add(new Transaction("Transfer", amount));
            return true;
        }
        return false;
    }

    public void printTransactionHistory() {
        if(history.isEmpty()) {
            System.out.println("\n----No transactions yet.----");
        } else {
            System.out.println("\n----Transaction History----\n");
            for(Transaction t : history) {
                System.out.println("--> " + t);
            }
        }
    }

    public double getBalance() {
        return balance;
    }
}

class ATMOperations {
    private BankAccount account;
    private Scanner scanner;

    public ATMOperations(BankAccount account) {
        this.account = account;
        scanner = new Scanner(System.in);
    }

    public void showMenu() {
        while(true) {
            System.out.println("\n======== ATM MENU ========");
            System.out.println("1. Transaction History");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Check Balance");
            System.out.println("6. Quit");
            System.out.print("\nEnter your choice: ");
            int choice = scanner.nextInt();

            switch(choice) {
                case 1 -> account.printTransactionHistory();
                case 2 -> {
                    System.out.print("Enter the amount you want to deposit: ");
                    double amt = scanner.nextDouble();
                    account.deposit(amt);
                    System.out.println("\n----Deposit of $" + amt + " successful.----");
                }
                case 3 -> {
                    System.out.print("Enter the amount you want to withdraw: ");
                    double amt = scanner.nextDouble();
                    if (account.withdraw(amt)) {
                        System.out.println("\n----Withdrawal of $" + amt + " successful.----");
                    } else {
                        System.out.println("\n----Insufficient balance.----");
                    }
                }
                case 4 -> {
                    System.out.print("Enter the amount you want to transfer: ");
                    double amt = scanner.nextDouble();
                    BankAccount dummyReceiver = new BankAccount();
                    if (account.transfer(amt, dummyReceiver)) {
                        System.out.println("\n----Transfer of $" + amt + " successful,----");
                    } else {
                        System.out.println("\n----Transfer failed. Not enough funds.----");
                    }
                }
                case 5 -> System.out.println("\n----Current Balance is: $" + account.getBalance() + "----");
                case 6 -> {
                    System.out.println("\n--------Thank you for using the ATM. Goodbye.--------");
                    return;
                }
                default -> System.out.println("\n----Invalid option. Please choose between 1-6.----");
            }
        }
    }
}

public class automaticTransactionMachine {
    public static void main(String[] args) {
        Users user = new Users("user123", "1234");
        Scanner scanner = new Scanner(System.in);

        System.out.println("---------------------------------------------");
        System.out.println("\tWelcome to online ATM");
        System.out.println("---------------------------------------------");
        System.out.print("\nEnter User ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();

        if(user.authenticate(userId, pin)) {
            System.out.println("\n----Login Successfull.----");
            BankAccount account = new BankAccount();
            ATMOperations atmOps = new ATMOperations(account);
            atmOps.showMenu();
        } else {
            System.out.println("\n----Invalid credentials. Access denied.----");
        }
    }
}

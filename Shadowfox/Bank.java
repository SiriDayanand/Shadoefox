import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.*;

class BankAccount {
    private String accountNumber;
    private double balance;
    private String accountType;
    private String currency;
    private String customerName; // Added customer name
    private List<String> transactionHistory;

    public BankAccount(String accountNumber, double initialBalance, String accountType, String currency, String customerName) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
        this.accountType = accountType;
        this.currency = currency;
        this.customerName = customerName; // Set customer name
        this.transactionHistory = new ArrayList<>();
        recordTransaction("Account created with initial balance: " + initialBalance);
    }

    public void deposit(double amount) {
        balance += amount;
        recordTransaction("Deposited: " + amount);
    }

    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            recordTransaction("Withdrew: " + amount);
        } else {
            recordTransaction("Withdrawal failed: Insufficient funds.");
        }
    }

    public void applyInterest() {
        if (accountType.equals("Savings")) {
            double interest = balance * 0.05; // 5% interest
            deposit(interest);
            recordTransaction("Interest applied: " + interest);
        }
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public String getCustomerName() { // Added getter for customer name
        return customerName;
    }

    public List<String> getTransactionHistory() {
        return transactionHistory;
    }

    private void recordTransaction(String transaction) {
        transactionHistory.add(transaction);
    }
}

public class Bank extends Application { // Changed class name here
    private Map<String, BankAccount> accounts = new HashMap<>();
    private CurrencyConverter currencyConverter = new CurrencyConverter();
    private List<String> notifications = new ArrayList<>();

    public static void main(String[] args) {
        launch(args); // Launch JavaFX application
    }

    @Override
    public void start(Stage primaryStage) {
        // Set up the primary stage
        primaryStage.setTitle("Advanced Bank Account Management System");

        // GridPane layout for the main screen
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);

        // Creating labels, text fields, choice boxes, and buttons
        Label accountNumberLabel = new Label("Account Number:");
        TextField accountNumberField = new TextField();

        Label customerNameLabel = new Label("Customer Name:"); // Added customer name label
        TextField customerNameField = new TextField(); // Added customer name field

        Label balanceLabel = new Label("Initial Balance:");
        TextField balanceField = new TextField();

        Label accountTypeLabel = new Label("Account Type:");
        ChoiceBox<String> accountTypeBox = new ChoiceBox<>();
        accountTypeBox.getItems().addAll("Savings", "Checking");

        Label currencyLabel = new Label("Currency:");
        ChoiceBox<String> currencyBox = new ChoiceBox<>();
        currencyBox.getItems().addAll("USD", "EUR", "INR");

        Button createAccountButton = new Button("Create Account");

        // Transaction section
        Label transactionAmountLabel = new Label("Transaction Amount:");
        TextField transactionAmountField = new TextField();

        Label transferToLabel = new Label("Transfer To Account:");
        TextField transferToField = new TextField();

        Button depositButton = new Button("Deposit");
        Button withdrawButton = new Button("Withdraw");
        Button transferButton = new Button("Transfer");

        // Transaction history
        TextArea transactionHistoryArea = new TextArea();
        transactionHistoryArea.setEditable(false);

        // Notifications
        TextArea notificationArea = new TextArea();
        notificationArea.setEditable(false);

        // Buttons for actions
        Button viewHistoryButton = new Button("View Transaction History");
        Button applyInterestButton = new Button("Apply Interest");
        Button viewNotificationsButton = new Button("View Notifications");

        // Add components to the grid
        grid.add(accountNumberLabel, 0, 0);
        grid.add(accountNumberField, 1, 0);

        grid.add(customerNameLabel, 0, 1); // Added customer name label to the grid
        grid.add(customerNameField, 1, 1); // Added customer name field to the grid

        grid.add(balanceLabel, 0, 2);
        grid.add(balanceField, 1, 2);

        grid.add(accountTypeLabel, 0, 3);
        grid.add(accountTypeBox, 1, 3);

        grid.add(currencyLabel, 0, 4);
        grid.add(currencyBox, 1, 4);

        grid.add(createAccountButton, 1, 5);

        grid.add(transactionAmountLabel, 0, 6);
        grid.add(transactionAmountField, 1, 6);

        grid.add(transferToLabel, 0, 7);
        grid.add(transferToField, 1, 7);

        grid.add(depositButton, 0, 8);
        grid.add(withdrawButton, 1, 8);
        grid.add(transferButton, 2, 8);

        grid.add(viewHistoryButton, 1, 9);
        grid.add(applyInterestButton, 2, 9);

        grid.add(viewNotificationsButton, 1, 10);
        grid.add(notificationArea, 1, 11, 2, 1);

        grid.add(transactionHistoryArea, 1, 12, 2, 1);

        // Set up the scene and show the stage
        Scene scene = new Scene(grid, 700, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Event handling for account creation
        createAccountButton.setOnAction(e -> {
            String accountNumber = accountNumberField.getText();
            String customerName = customerNameField.getText(); // Get customer name
            double initialBalance;
            try {
                initialBalance = Double.parseDouble(balanceField.getText());
            } catch (NumberFormatException ex) {
                transactionHistoryArea.appendText("Invalid balance amount.\n");
                return;
            }
            String accountType = accountTypeBox.getValue();
            String currency = currencyBox.getValue();

            if (accountNumber != null && !accountNumber.isEmpty() && initialBalance >= 0 && accountType != null && currency != null && customerName != null && !customerName.isEmpty()) {
                BankAccount account = new BankAccount(accountNumber, initialBalance, accountType, currency, customerName); // Pass customer name
                accounts.put(accountNumber, account);
                transactionHistoryArea.appendText("Account created for: " + customerName + " (Account Number: " + accountNumber + ")\n");
                notifications.add("New account created for: " + customerName);
            } else {
                transactionHistoryArea.appendText("Failed to create account.\n");
            }
        });

        // Deposit event
        depositButton.setOnAction(e -> {
            String accountNumber = accountNumberField.getText();
            BankAccount account = accounts.get(accountNumber);
            if (account != null) {
                double amount;
                try {
                    amount = Double.parseDouble(transactionAmountField.getText());
                } catch (NumberFormatException ex) {
                    transactionHistoryArea.appendText("Invalid deposit amount.\n");
                    return;
                }
                account.deposit(amount);
                transactionHistoryArea.appendText("Deposited " + amount + " to account: " + accountNumber + " (Customer: " + account.getCustomerName() + ")\n");
                notifications.add("Deposited " + amount + " to account: " + accountNumber);
            } else {
                transactionHistoryArea.appendText("Account not found.\n");
            }
        });

        // Withdraw event
        withdrawButton.setOnAction(e -> {
            String accountNumber = accountNumberField.getText();
            BankAccount account = accounts.get(accountNumber);
            if (account != null) {
                double amount;
                try {
                    amount = Double.parseDouble(transactionAmountField.getText());
                } catch (NumberFormatException ex) {
                    transactionHistoryArea.appendText("Invalid withdrawal amount.\n");
                    return;
                }
                account.withdraw(amount);
                transactionHistoryArea.appendText("Withdrew " + amount + " from account: " + accountNumber + " (Customer: " + account.getCustomerName() + ")\n");
                notifications.add("Withdrew " + amount + " from account: " + accountNumber);
            } else {
                transactionHistoryArea.appendText("Account not found.\n");
            }
        });

        // Transfer event
        transferButton.setOnAction(e -> {
            String fromAccountNumber = accountNumberField.getText();
            String toAccountNumber = transferToField.getText();
            BankAccount fromAccount = accounts.get(fromAccountNumber);
            BankAccount toAccount = accounts.get(toAccountNumber);
            if (fromAccount != null && toAccount != null) {
                double amount;
                try {
                    amount = Double.parseDouble(transactionAmountField.getText());
                } catch (NumberFormatException ex) {
                    transactionHistoryArea.appendText("Invalid transfer amount.\n");
                    return;
                }
                fromAccount.withdraw(amount); // Withdraw from sender's account
                toAccount.deposit(amount); // Deposit into receiver's account
                transactionHistoryArea.appendText("Transferred " + amount + " from account: " + fromAccountNumber + " to account: " + toAccountNumber + "\n");
                notifications.add("Transferred " + amount + " from account: " + fromAccountNumber + " to account: " + toAccountNumber);
            } else {
                transactionHistoryArea.appendText("One or both accounts not found.\n");
            }
        });

        // Apply interest event
        applyInterestButton.setOnAction(e -> {
            String accountNumber = accountNumberField.getText();
            BankAccount account = accounts.get(accountNumber);
            if (account != null) {
                account.applyInterest();
                transactionHistoryArea.appendText("Interest applied to account: " + accountNumber + "\n");
            } else {
                transactionHistoryArea.appendText("Account not found.\n");
            }
        });

        // View transaction history
        viewHistoryButton.setOnAction(e -> {
            String accountNumber = accountNumberField.getText();
            BankAccount account = accounts.get(accountNumber);
            if (account != null) {
                transactionHistoryArea.clear();
                for (String transaction : account.getTransactionHistory()) {
                    transactionHistoryArea.appendText(transaction + "\n");
                }
            } else {
                transactionHistoryArea.appendText("Account not found.\n");
            }
        });

        // View notifications
        viewNotificationsButton.setOnAction(e -> {
            notificationArea.clear();
            for (String notification : notifications) {
                notificationArea.appendText(notification + "\n");
            }
        });
    }
}

class CurrencyConverter {
    // Placeholder for currency conversion logic
}

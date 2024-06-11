package org.example.service;


import org.example.Entities.TransactionType;
import org.example.InputUtils.InputUtils;
import org.example.db.AccountDBService;

public class Manager {
    private final AccountDBService accountDBService;

    public Manager() {
        this.accountDBService = new AccountDBService();
    }

    public void start(){
        System.out.println("------------Welcome to Bank--------");
        while(true){
            System.out.println("1. Account balance");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4 Exit");
            final int choice = InputUtils.readInt();
            switch (choice) {
                case 1 -> balance();
                case 2 -> withdraw();
                case 3 -> deposit();
                case 4 -> {
                    System.out.println("Good bye");
                    return;
                }
                default -> System.out.println("Invalid choice");
            }
        }
    }

    public void balance(){
        System.out.println("Your balance is");
        System.out.println(accountDBService.readAccountById(1));
    }

    public void withdraw(){
        System.out.println("How much do you want to withdraw?");
        final int withdraw = InputUtils.readInt();
        accountDBService.updateAccountById(1, withdraw, TransactionType.WITHDRAWAL);
        System.out.println(accountDBService.readAccountById(1));
    }

    public void deposit(){
        System.out.println("How much do you want to deposit?");
        final int deposit = InputUtils.readInt();
        accountDBService.updateAccountById(1, deposit, TransactionType.DEPOSIT);
        System.out.println(accountDBService.readAccountById(1));
    }
}
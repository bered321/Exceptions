import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message) {
        super(message);
    }
}

class MaxBalanceExceededException extends Exception {
    public MaxBalanceExceededException(String message) {
        super(message);
    }
}

class BankAccount {

    private int balance;
    private int maxBalance;

    public BankAccount(int balance, int maxBalance) {
        this.balance = balance;
        this.maxBalance = maxBalance;
    }

    public synchronized void deposit(int amount) throws MaxBalanceExceededException {
        if (balance + amount > maxBalance) {
            throw new MaxBalanceExceededException("Maximum balance exceeded");
        }
        balance += amount;
    }

    public synchronized void withdraw(int amount) throws InsufficientFundsException {
        if (balance < amount) {
            throw new InsufficientFundsException("Insufficient funds");
        }
        balance -= amount;
    }

}

class Bank {

    private ConcurrentHashMap<BankAccount, Boolean> accounts;

    public Bank() {
        accounts = new ConcurrentHashMap<>();
    }

    public BankAccount createAccount(int balance, int maxBalance) {
        BankAccount account = new BankAccount(balance, maxBalance);
        accounts.put(account, true);
        return account;
    }

    public void transaction(BankAccount account, int amount) {
        try {
            if (amount > 0) {
                account.deposit(amount);
            } else {
                account.withdraw(-amount);
            }
        } catch (InsufficientFundsException | MaxBalanceExceededException e) {
            System.out.println(e.getMessage());
        }
    }

}

public class Task04 {

    public static void main(String[] args) {
        Bank bank = new Bank();
        ExecutorService executor = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 10; i++) {
            BankAccount account = bank.createAccount(0, 1000);
            for (int j = 0; j < 10; j++) {
                int amount = (int)(Math.random() * 1000) - 500;
                executor.submit(() -> bank.transaction(account, amount));
            }
        }

        executor.shutdown();
    }

}
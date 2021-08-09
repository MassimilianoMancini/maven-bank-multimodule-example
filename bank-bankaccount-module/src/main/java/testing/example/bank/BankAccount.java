package testing.example.bank;

public class BankAccount {
	private int id;
	private double balance = 0;
	private static int lastId = 0;
	
	public BankAccount() {
		this.id = ++lastId;
	}

	public int getId() {
		return id;
	}

	public double getBalance() {
		return balance;
	}
	
	public void deposit(double amount) {
		handleNegativeAmount(amount);
		balance += amount;
	}

	private void handleNegativeAmount(double amount) {
		if (amount < 0) {
			throw new IllegalArgumentException("Negative amount: " + amount);
		}
	}
	
	public void withdraw(double amount) {
		handleNegativeAmount(amount);
		
		if (balance - amount < 0) {
			throw new IllegalArgumentException("Cannot withdraw " + amount + " from " + balance);
		}
		
		balance -= amount;
	}

	void setBalance(double balance) {
		this.balance = balance;
	}
}

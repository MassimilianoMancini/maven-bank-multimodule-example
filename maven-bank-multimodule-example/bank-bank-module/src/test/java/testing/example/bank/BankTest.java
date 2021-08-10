package testing.example.bank;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

public class BankTest {

	private static final double AMOUNT = 7.0;

	private static final double INITIAL_BALANCE = 10.0;

	private static final String INEXISTENT_ID_MESSAGE = "No account found with id: 1";

	private static final int INEXISTENT_ID = 1;

	private static final double NEGATIVE_AMOUNT = -1.0;

	private static final String NEGATIVE_AMOUNT_MESSAGE = "Negative amount: -1.0";

	private Bank bank;

	// the collaborator of Bank that we manually instrument and inspect
	private List<BankAccount> bankAccounts;

	@Before
	public void setup() {
		bankAccounts = new ArrayList<BankAccount>();
		bank = new Bank(bankAccounts);
	}

	@Test
	public void testOpenNewAccountShouldReturnAPositiveIdAndStoreTheAccount() {
		int newAccountId = bank.openNewBankAccount(0);
		assertThat(newAccountId).isGreaterThan(0);
		assertThat(bankAccounts).hasSize(1).extracting(BankAccount::getId).contains(newAccountId);
	}

	@Test
	public void testDepositWhenAccountIsNotFoundShouldThrow() {
		assertThatThrownBy(()->bank.deposit(INEXISTENT_ID, INITIAL_BALANCE))
			.isInstanceOf(NoSuchElementException.class)
			.hasMessage(INEXISTENT_ID_MESSAGE);
	}

	@Test
	public void testDepositWhenAccountIsFoundShouldIncrementBalance() {
		// setup
		BankAccount testAccount = createTestAccount(INITIAL_BALANCE);
		bankAccounts.add(testAccount);
		// exercise
		bank.deposit(testAccount.getId(), AMOUNT);
		// verify
		assertThat(INITIAL_BALANCE + AMOUNT).isEqualTo(testAccount.getBalance());
	}

	@Test
	public void testWithdrawWhenAccountIsNotFoundShouldThrow() {
		assertThatThrownBy(()->bank.withdraw(INEXISTENT_ID, AMOUNT))
			.isInstanceOf(NoSuchElementException.class)
			.hasMessage(INEXISTENT_ID_MESSAGE);
	}

	@Test
	public void testWithdrawWhenAccountIsFoundShouldDecrementBalance() {
		// setup
		BankAccount testAccount = createTestAccount(INITIAL_BALANCE);
		bankAccounts.add(testAccount);
		// exercise
		bank.withdraw(testAccount.getId(), AMOUNT);
		// verify
		assertThat(INITIAL_BALANCE - AMOUNT).isEqualTo(testAccount.getBalance());
	}

	@Test
	public void testIdIsAutomaticallyAssignedAsPositiveNumber() {
		// setup
		BankAccount bankAccount = new BankAccount();
		// verify
		assertThat(bankAccount.getId()).isPositive();
	}

	@Test
	public void testDepositWhenAmountIsNegativeShouldThrow() {
		BankAccount bankAccount = new BankAccount();
		assertThatThrownBy(() -> bankAccount.deposit(NEGATIVE_AMOUNT)).isInstanceOf(IllegalArgumentException.class)
				.hasMessage(NEGATIVE_AMOUNT_MESSAGE);
		// further assertions after the exception is thrown
		assertThat(bankAccount.getBalance()).isZero();
	}

	@Test
	public void testDepositWhenAmountIsCorrectShouldIncreaseBalance() {
		BankAccount bankAccount = new BankAccount();
		bankAccount.setBalance(INITIAL_BALANCE);
		bankAccount.deposit(AMOUNT);
		assertThat(bankAccount.getBalance()).isEqualTo(INITIAL_BALANCE + AMOUNT);
	}

	private BankAccount createTestAccount(double initialBalance) {
		BankAccount bankAccount = new BankAccount();
		bankAccount.setBalance(initialBalance);
		return bankAccount;
	}

}

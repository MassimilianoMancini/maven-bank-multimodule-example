package testing.example.bank;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.Test;

public class BankAccountTest {
	

	private static final String CANNOT_WITHDRAW_MESSAGE = "Cannot withdraw 7.0 from 0.0";
	private static final String NEGATIVE_AMOUNT_MESSAGE = "Negative amount: -1.0";
	private static final double NEGATIVE_AMOUNT = -1.0;
	private static final double INITIAL_BALANCE = 10.0;
	private static final double AMOUNT = 7.0;

	@Test
	public void testIdIsAutomaticallyAssignedAsPositiveNumber() {
		// setup
		BankAccount bankAccount = new BankAccount();
		// verify
		assertThat(bankAccount.getId()).isPositive();
	}
	
	@Test
	public void testIdsAreIncremental() {
		BankAccount firstAccount = new BankAccount();
		BankAccount secondAccout = new BankAccount();
		assertThat(firstAccount.getId()).isLessThan(secondAccout.getId());
	}

	@Test
	public void testDepositWhenAmountIsCorrectShouldIncreaseBalance() {
		// setup
		BankAccount bankAccount = new BankAccount();
		bankAccount.setBalance(AMOUNT);
		// exercise
		bankAccount.deposit(INITIAL_BALANCE);
		// verify
		assertThat(INITIAL_BALANCE + AMOUNT).isEqualTo(bankAccount.getBalance());
	}
	
	@Test
	public void testDepositWhenAmountIsNegativeShouldThrow() {
		// setup
		BankAccount bankAccount = new BankAccount();
		assertThatThrownBy(()->bankAccount.deposit(NEGATIVE_AMOUNT))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage(NEGATIVE_AMOUNT_MESSAGE);
		assertThat(bankAccount.getBalance()).isZero();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testDepositWhenAmountIsNegativeShouldThrowWithExpected() {
		BankAccount bankAccount = new BankAccount();
		bankAccount.deposit(NEGATIVE_AMOUNT);
	}
			
	@Test
	public void testWithdrawWhenAmountIsNegativeShouldThrow() {
		BankAccount bankAccount = new BankAccount();
		assertThatThrownBy(()->bankAccount.withdraw(NEGATIVE_AMOUNT))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage(NEGATIVE_AMOUNT_MESSAGE);
		assertThat(bankAccount.getBalance()).isZero();
	}
	
	@Test
	public void testWithdrawWhenBalanceIsUnsufficientShouldThrow() {
		BankAccount bankAccount = new BankAccount();
		assertThatThrownBy(()->bankAccount.withdraw(AMOUNT))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage(CANNOT_WITHDRAW_MESSAGE);
		assertThat(bankAccount.getBalance()).isZero();
	}
	
	@Test
	public void testWithdrawWhenBalanceIsSufficientShouldDecreaseBalance() {
		// setup
		BankAccount bankAccount = new BankAccount();
		bankAccount.setBalance(INITIAL_BALANCE);
		// exercise
		bankAccount.withdraw(AMOUNT);
		// verify
		assertThat(INITIAL_BALANCE - AMOUNT).isEqualTo(bankAccount.getBalance());
	}
}

package application.test;

import org.junit.Before;
import org.junit.Test;

import application.model.LoanExpense;

public class LoanExpenseTest {
	//private Expense firstExpense;
	private LoanExpense loan;
	@Before
	public void setUp() throws Exception {
		loan = new LoanExpense("Loan", 100000.0, 360, 6.0);
	}

	@Test
	public final void testComputeMonthlyCost() {
		System.out.println("Monthly Cost: " + loan.computeMonthlyCost());
		assert(loan.computeMonthlyCost() >= 599): "Error in calculating cost";
		
		
	}

}

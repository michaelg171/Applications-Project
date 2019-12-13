package application.test;

import org.junit.Before;
import org.junit.Test;

import application.model.RecurringExpense;

public class RecurringExpenseTest {
	private RecurringExpense newExpense;
	@Before
	public void setUp() throws Exception {
		newExpense = new RecurringExpense("Expense", 30.0, 12);
		
	}

	@Test
	public final void test() {
		System.out.println(newExpense.computeMonthlyCost());
		assert(newExpense.computeMonthlyCost() == 30.0): "Error, didn't return correctly";
	}

}

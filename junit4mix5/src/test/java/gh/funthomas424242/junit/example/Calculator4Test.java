package gh.funthomas424242.junit.example;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class Calculator4Test {

	private final Long wertA;
	private final Long wertB;
	private final Double result;

	public Calculator4Test(final Long wertA, final Long wertB,
						   final Double result) {
		this.wertA = wertA;
		this.wertB = wertB;
		this.result = result;
	}

	@Test
	public void testAdd() {
		final Calculator calculator = new Calculator();
		final Double wertC = calculator.add(wertA.doubleValue(),
				wertB.doubleValue());
		assertEquals(result, wertC);
	}

	@Parameters
	public static Collection<Object[]> testCases() {
		Collection<Object[]> parameters = new ArrayList<Object[]>();
		parameters.add(new Object[] { 1L, 2L, 3.0 });
		parameters.add(new Object[] { -1L, 2L, 1.0 });
		parameters.add(new Object[] { 2L, 2L, 4.0 });
		parameters.add(new Object[] { 4L, 0L, 4.0 });
		return parameters;

	}

}

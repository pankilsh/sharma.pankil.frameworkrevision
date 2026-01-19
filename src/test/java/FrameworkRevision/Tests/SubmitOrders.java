package FrameworkRevision.Tests;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.Assert;
import org.testng.annotations.Test;

import FrameworkRevision.CustomExceptions.ProductOutOfStockException;
import FrameworkRevision.TestComponents.BaseTest;

public class SubmitOrders extends BaseTest {

	@Test
	public void testOne() {
		Assert.assertTrue(false);
	}
	
	@Test(expectedExceptions = ArithmeticException.class)
	public void testTwo() {
		int i = 5/1;
		System.out.println(i);
	}
	
	public void validateProductStock(int size) throws ProductOutOfStockException {
		if (size == 0) {
			throw new ProductOutOfStockException();
		}
	}
	
	@Test(expectedExceptions = FrameworkRevision.CustomExceptions.ProductOutOfStockException.class)
	public void testExceptions() {
		try {
			validateProductStock(0);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}

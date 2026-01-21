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
		int i = 5/0;
	}
	
	

}

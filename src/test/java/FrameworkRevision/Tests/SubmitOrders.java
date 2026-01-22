package FrameworkRevision.Tests;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

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
	
	@Test
	public void parallelTest() throws InterruptedException, MalformedURLException, URISyntaxException {
		Assert.assertTrue(false);
		}

}

package FrameworkRevision.Tests;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

import org.testng.Assert;
import org.testng.annotations.Test;

import FrameworkRevision.CustomExceptions.ProductOutOfStockException;
import FrameworkRevision.PageObjects.LandingPage;
import FrameworkRevision.TestComponents.BaseTest;
import FrameworkRevision.TestComponents.BaseTestGrid;

public class SubmitOrdersGrid extends BaseTestGrid {
	
	@Test
	public void parallelTest() throws InterruptedException, MalformedURLException, URISyntaxException {
		Assert.assertTrue(false);
		}

}

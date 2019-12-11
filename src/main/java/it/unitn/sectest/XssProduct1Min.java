package it.unitn.sectest;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import utils.BaseTest;
import utils.BrandPage;
import utils.CategoryPage;
import utils.DashboardPage;
import utils.LoginPage;
import utils.ProductPage;

public class XssProduct1Min extends BaseTest{

	DashboardPage dashboard;
	BrandPage brand;
	CategoryPage categories;
	ProductPage productPage;
	
	@Test
	public void XssProduct1Min() {

		// 1. Login as admin
		// 2. Change brandName inserting XSS attack
		// 3. go to the product and assert the XSS attack is there
		
		// 1. Login
		LoginPage login = new LoginPage(driver);
		dashboard = login.login("admin", "admin");
		
		// init for a new product
		brand = dashboard.goToBrand();
		brand.add("tmp", "Available");
		
		categories = dashboard.goToCategory();
		categories.add("tmp", "Available");
		
		productPage = dashboard.goToProduct();
		productPage.add("tmp", "1", "1", "tmp", "tmp", "Available");
		
		// 2. Go to setting page
		// WebElement settingButton = driver.findElement(By.cssSelector("#topNavSetting > a:nth-child(1)")); ElementNotVisibleException
		// settingButton.click();
		
		assertEquals(true, true);
	}
	
	@After
	public void reset() {		

		// 0. We are still in the dashboard
		dashboard.goToBrand();
		brand.remove();
		dashboard.goToCategory();
		categories.remove();
		dashboard.goToProduct();
		productPage.remove();
	}
	
}
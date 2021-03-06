package it.unitn.sectest;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import utils.AddOrdersPage;
import utils.BaseTest;
import utils.BrandPage;
import utils.CategoryPage;
import utils.DashboardPage;
import utils.LoginPage;
import utils.ManageOrdersPage;
import utils.ProductPage;

public class XssOrdersPhp27Min extends BaseTest{

	DashboardPage dashboard;
	BrandPage brandPage;
	CategoryPage categoriesPage;
	ProductPage productPage;
	AddOrdersPage addOrdersPage;
	ManageOrdersPage manageOrdersPage;
	
	@Test
	public void testXssOrdersPhp27Min() {
		// 1. Login
		LoginPage login = new LoginPage(driver);
		dashboard = login.login("admin", "admin");
		
		// add brand
		String brand = "brand";
		brandPage = dashboard.goToBrand();
		brandPage.add(brand, "Available");
		
		// add category
		String category = "category";
		categoriesPage = dashboard.goToCategory();
		categoriesPage.add(category, "Available");
		
		// add products
		String productName = "product";
		String quantity = "30";
		String rate = "1";
		String status = "Available";
		productPage = dashboard.goToProduct();
		productPage.add(productName, quantity, rate, brand, category, status);
		
		// add order
		String paid = "0";
		String discount = "0";
		myWait();
		addOrdersPage = dashboard.goToAddOrders();
		myWait();
		addOrdersPage.add("21/12/2012", "name", "contact", productName, discount, paid, "Cash", "No Payment", "In Gujarat");
		myWait();
		
		// add another product
		productName = "<script>alert(\"test\")<\\/script>";
		quantity = "1";
		rate = "1";
		status = "Available";
		productPage = dashboard.goToProduct();
		productPage.add(productName, quantity, rate, brand, category, status);
		
		myWait();
		manageOrdersPage = dashboard.goToManageOrders();
		manageOrdersPage.edit();

		
		String alertText = dashboard.getAlertText();
		assertEquals("test", alertText);
	}
	
	@After
	public void reset() {		
		dashboard.closeAlert();
		dashboard.closeAlert();
		dashboard.goToManageOrders();
		manageOrdersPage.remove();
		dashboard.goToBrand();
		brandPage.remove();
		dashboard.goToCategory();
		categoriesPage.remove();
		dashboard.goToProduct();
		productPage.remove();
	}
	private void myWait() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}

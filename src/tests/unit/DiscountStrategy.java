package tests.unit;

import static org.junit.Assert.*;

import java.time.Duration;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import com.shashi.beans.ProductBean;
import com.shashi.service.impl.ProductServiceImpl;

public class DiscountStrategy {

	static WebDriver driver;

	@BeforeClass
	public static void initDriver() {
		// Create the web driver that will be used for testing, and set it to wait 500ms on page load, just in case
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
	}
	
	@Test
	public void testSuggestedDiscountsWhenLoggedOut() {
		// Navigate to the home page
		driver.get("http://localhost:8080/shopping-cart/index.jsp");
		
		// Get the number of Discount elements
		List<WebElement> discountList = driver.findElements(By.id("suggested-discount"));
		
		// There should be 0 of them
		assertEquals(0, discountList.size());
	}
	
	@Test
	public void testSuggestedDiscountsAsCustomer() {
		// Navigate to Login page
		driver.get("http://localhost:8080/shopping-cart/login.jsp");
		
		// Get the various Login elements to manipulate
		WebElement email = driver.findElement(By.id("email"));
		WebElement password = driver.findElement(By.id("password"));
		Select dropdown = new Select(driver.findElement(By.id("dropdown")));
		WebElement button = driver.findElement(By.id("login"));
		
		// Fill in the required values to log in as CUSTOMER, and select CUSTOMER from the dropdown
		email.sendKeys("guest@gmail.com");
		password.sendKeys("guest");
		dropdown.selectByValue("customer");
		
		// Actually log in
		button.click();
		
		// Get the number of Discount elements
		List<WebElement> discountList = driver.findElements(By.id("suggested-discount"));
		
		// There should be 0 of them
		assertEquals(0, discountList.size());
		
		// Logout
		WebElement logoutButton = driver.findElement(By.linkText("Logout"));
		logoutButton.click();
	}
	
	@Test
	public void testGetSuggestedDiscounts() {
		// Navigate to Login page
		driver.get("http://localhost:8080/shopping-cart/login.jsp");
		
		// Get the various Login elements to manipulate
		WebElement email = driver.findElement(By.id("email"));
		WebElement password = driver.findElement(By.id("password"));
		Select dropdown = new Select(driver.findElement(By.id("dropdown")));
		WebElement button = driver.findElement(By.id("login"));
		
		// Fill in the required values to log in as ADMIN, and select ADMIN from the dropdown
		email.sendKeys("admin@gmail.com");
		password.sendKeys("admin");
		dropdown.selectByValue("admin");
		
		// Actually log in
		button.click();
		
		// Get the number of Discount elements
		List<WebElement> discountList = driver.findElements(By.id("suggested-discount"));
		
		// There should be 10 of them
		assertEquals(10, discountList.size());
		
		// Logout
		WebElement logoutButton = driver.findElement(By.linkText("Logout"));
		logoutButton.click();
	}
	
	@Test
	public void testSuggestedDiscountVariableDependencies() {

		ProductServiceImpl service = new ProductServiceImpl();

        List<ProductBean> leastProducts = service.getLeastSellingProducts();
        List<ProductBean> mostProducts = service.getMostSellingProducts();
        
        for (ProductBean product : leastProducts) {
        	assertTrue(product.getAmountSold() >= 0);
        }
        for (ProductBean product : mostProducts) {
        	assertTrue(product.getAmountSold() >= 0);
        }
        assertEquals(10, leastProducts.size() + mostProducts.size());
	}
	
	
	@AfterClass
	public static void closeDriver() {
		driver.quit();
	}
}

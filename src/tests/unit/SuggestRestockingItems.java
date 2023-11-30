package tests.unit;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;


public class SuggestRestockingItems{
	//declare webdriver
		static WebDriver driver;
		
		@BeforeClass
		public static void initDriver() {
		
			driver = new FirefoxDriver();
		}
public void SuggestRestockingItems() {
	
driver.get("http://localhost:8080/shopping-cart/login.jsp");
		
		// Declare web form elements
		WebElement email = driver.findElement(By.id("email"));
		WebElement password = driver.findElement(By.id("password"));
		Select dropdown = new Select(driver.findElement(By.id("dropdown")));
		WebElement button = driver.findElement(By.id("login"));
		
		// Fill in input values to log in as an admin user
		email.sendKeys("admin@gmail.com");
		password.sendKeys("admin");
		dropdown.selectByValue("admin");
		button.click();
		
		// Get a list of all low stock elements.
		List<WebElement> lowStockList = driver.findElements(By.id("low-stock"));
		
		//proceed if there is in fact low stock items which is assumed to be the case
		if (lowStockList.size()>0) {
			
			//if it is low stock, the element should 
			assertTrue("Test failed, suggestion not displayed", ((WebElement) lowStockList).isDisplayed());
			
			
		}
}
}
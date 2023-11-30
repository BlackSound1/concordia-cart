package tests.unit;
import static org.junit.Assert.*;


import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.junit.Test;


public class RegisterWithoutPin {
	//declare web driver
	static WebDriver driver;
	
	@BeforeClass
	public static void initDriver() {
		driver = new FirefoxDriver();
	}
	
	@Test
	public void register() {
		//go to registration page
		driver.get("http://localhost:8080/shopping-cart/register.jsp");
		
		//declare form elements 
		WebElement username = driver.findElement(By.name("username"));
		WebElement email = driver.findElement(By.name("email"));
		// WebElement last = driver.findElement(By.name("last_name"));
		WebElement mobile = driver.findElement(By.name("mobile"));
		WebElement password = driver.findElement(By.name("password"));
		WebElement confirm = driver.findElement(By.name("confirmPassword"));
		WebElement address = driver.findElement(By.name("address"));
		WebElement submit = driver.findElement(By.id("submit"));
		
		//filling inputs 
		username.sendKeys("guest");
		//last.sendKeys("test");
		email.sendKeys("guest_test@gmail.com");
		password.sendKeys("guest1");
		confirm.sendKeys("guest1");
		mobile.sendKeys("123456789");
		address.sendKeys("123");
		submit.click();
		
		/**tests if after inputting and submitting the modified registration
		the page redirects to the page which declares successful registration
		**/
		
		assertEquals(driver.getCurrentUrl(),"http://localhost:8080/shopping-cart/RegisterSrv");

	}

	@AfterClass
	public static void closeDriver() {
		driver.quit();
	}
}
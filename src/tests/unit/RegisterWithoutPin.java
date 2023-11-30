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
	
	static WebDriver driver;
	
	@BeforeClass
	public static void initDriver() {
		// Web driver
		driver = new FirefoxDriver();
		
	}
	
	public void register() {
		driver.get("http://localhost:8080/shopping-cart/register.jsp");
		
		WebElement username = driver.findElement(By.name("username"));
		WebElement email = driver.findElement(By.name("email"));
		WebElement last = driver.findElement(By.name("last_name"));
		WebElement mobile = driver.findElement(By.name("mobile"));
		WebElement password = driver.findElement(By.name("password"));
		WebElement confirm = driver.findElement(By.name("confirmPassword"));
		WebElement submit = driver.findElement(By.name("submit"));
		
		username.sendKeys("guest");
		last.sendKeys("test");
		email.sendKeys("guest_test@gmail.com");
		password.sendKeys("guest1");
		confirm.sendKeys("guest1");
		submit.click();
		
		assertEquals(driver.getCurrentUrl(),"http://localhost:8080/shopping-cart/RegisterSrv");

	}}
package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TestLoginPage {
	
	public By userName = By.cssSelector("input#username");
	By passWord = By.cssSelector("input#passwd");
	By logInbtn = By.cssSelector(".btn.btn-block.btn-dark.text-left");
	
	public WebElement getUserName(WebDriver driver) {
		return driver.findElement(userName);
	}
	
	public WebElement getPassword(WebDriver driver) {
		return driver.findElement(passWord);
	}
	
	public WebElement getLoginBtn(WebDriver driver) {
		return driver.findElement(logInbtn);
	}

}

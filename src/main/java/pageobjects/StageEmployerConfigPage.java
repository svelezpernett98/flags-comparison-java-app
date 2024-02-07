package pageobjects;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class StageEmployerConfigPage {
	
	By employerGroupSpan = By.cssSelector("#groupId_chzn > a");
	By employerGroupInput = By.cssSelector("div#groupId_chzn input[type='text']");
	By branchSpan = By.cssSelector("#branchId_chzn > a");
	By branchInput = By.cssSelector("div#branchId_chzn input[type='text']");
    By productSpan = By.cssSelector("#planCodeId_chzn > a");
	By productInput = By.cssSelector("#planCodeId_chzn > div > div > input[type=text]");
	WebElement tableElement;
	List<WebElement> rows;
	public Map<String, String> stageFlagValuelinkedHashMap;
	
	public WebElement getEmployerGroupSpan(WebDriver driver) {
		return driver.findElement(employerGroupSpan);
	}
	
	public WebElement getEmployerGroupInput(WebDriver driver) {
		return driver.findElement(employerGroupInput);
	}

	public WebElement getBranchSpan(WebDriver driver) {
		return driver.findElement(branchSpan);
	}
	
	public WebElement getBranchInput(WebDriver driver) {
		return driver.findElement(branchInput);
	}
	
	public WebElement getProductSpan(WebDriver driver) {
		return driver.findElement(productSpan);
	}
	
	public WebElement getProductInput(WebDriver driver) {
		return driver.findElement(productInput);
	}
	
	
	//See description of this method at AppTest.java
	public void getStageHashMapWithValues(WebDriver driver) {
		stageFlagValuelinkedHashMap = new LinkedHashMap<>();
		
		// Find all <tr> elements in the table
	    List<WebElement> allRows = driver.findElements(By.cssSelector("table#dimensionTools tr"));

	    // Iterate through each <tr> element
	    for (WebElement row : allRows) {
	        // Find the 1st and 2nd <td> elements inside the current <tr>
	        List<WebElement> cells = row.findElements(By.cssSelector("td"));
	        
	        if (row.isDisplayed()) {
	            // Process the visible row
	        	// Process the 1st and 2nd <td> elements
	            if (cells.size() >= 2) {
	                WebElement flagName = cells.get(0);
	                WebElement flagValue = cells.get(1);

	                // Process the 1st and 2nd cell
	                try {
	                	stageFlagValuelinkedHashMap.put(flagName.getText(), flagValue.getText());
	                }
	                catch(Exception e){
	                	e.printStackTrace();
	                	}
	            }
	        }
	        
	    }
	}

}

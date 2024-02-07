package pageobjects;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TestEmployerConfigPage {
	
	By employerGroupSpan = By.cssSelector("#groupId_chzn > a");
	By employerGroupInput = By.cssSelector("div#groupId_chzn input[type='text']");
	By branchSpan = By.cssSelector("#branchId_chzn > a");
	By branchInput = By.cssSelector("div#branchId_chzn input[type='text']");
    By productSpan = By.cssSelector("#planCodeId_chzn > a");
	By productInput = By.cssSelector("#planCodeId_chzn > div > div > input[type=text]");
	WebElement tableElement;
	List<WebElement> rows;
	public Map<String, String> testFlagValuelinkedHashMap;
	
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
	
//	public void getTableLenght(WebDriver driver){
//		tableElement = driver.findElement(By.id("dimensionTools"));
//		rows = tableElement.findElements(By.tagName("tr"));
//		System.out.println("length:" + rows.size());
//	}
//	
//	public void getTestHashMapWithValues(WebDriver driver){
//		testFlagValuelinkedHashMap = new LinkedHashMap<>();
//		for (int i = 1; i < rows.size() + 1; i++) {
//			  System.out.println(i);
//			  try {
//			  testFlagValuelinkedHashMap.put(driver.findElement(By.xpath("/html/body/div[17]/div[2]/div[3]/div[6]/table/tbody/tr[" + i + "]/td[1]")).getText(), driver.findElement(By.xpath("/html/body/div[17]/div[2]/div[3]/div[6]/table/tbody/tr[" + i + "]/td[2]")).getText());
//			  }catch(Exception e) {
//				  e.printStackTrace();
//			  }
//			}
//		System.out.println(testFlagValuelinkedHashMap);
//		System.out.println("stage hasmap size:" + testFlagValuelinkedHashMap.size());
//	}

	public void getTestHashMapWithValues(WebDriver driver) {
		testFlagValuelinkedHashMap = new LinkedHashMap<>();
		
		// Find all <tr> elements in the table
	    List<WebElement> allRows = driver.findElements(By.cssSelector("table#dimensionTools tr"));

	    // Iterate through each <tr> element
	    for (WebElement row : allRows) {
	        // Find the 1st and 2nd <td> elements inside the current <tr>
	        List<WebElement> cells = row.findElements(By.cssSelector("td"));
	        
	        if (row.isDisplayed()) {
	            // Your code to process the visible row
	        	// Process the 1st and 2nd <td> elements
	            if (cells.size() >= 2) {
	                WebElement flagName = cells.get(0);
	                WebElement flagValue = cells.get(1);

	                // Your code to process the 1st and 2nd cells
	                try {
	                	testFlagValuelinkedHashMap.put(flagName.getText(), flagValue.getText());
	                }
	                catch(Exception e){
	                	e.printStackTrace();
	                	}
	            }
	        }
	        
	    }
	}
	
	
	
	

	
	
}
















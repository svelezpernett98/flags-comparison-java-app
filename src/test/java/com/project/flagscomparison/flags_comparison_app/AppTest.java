package com.project.flagscomparison.flags_comparison_app;

import java.io.IOException;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import base.BasePage;
import pageobjects.ProdEmployerConfigPage;
import pageobjects.ProdLoginPage;
import pageobjects.StageEmployerConfigPage;
import pageobjects.StageLoginPage;
import pageobjects.TestEmployerConfigPage;
import pageobjects.TestLoginPage;

public class AppTest 
{	
	
	public static void main(String[] args) throws IOException {
		
		WebDriver driver;
		BasePage base = new BasePage();

		//Creates workesheet where the data will be stored
		base.createWorksheet();

		//For each branch from the branches listed in the config.properties file
		for(String branch : base.getJHBranchesArray()) {
			//Initializes the WebDriver
			driver = base.getDriver();

			//Go to Flag management page in PROD environment
			driver.get(base.getProdUrl());

			//Instance of prodLoginPageObject to access the webElements from this page
			ProdLoginPage prodLoginPageObject = new ProdLoginPage();
			
			//use of explicit wait:
//			WebElement loginButton = base.wait.until(ExpectedConditions.visibilityOfElementLocated(prodLoginPageObject.userName));
//			loginButton.sendKeys(base.getUsername());
			
			//Login retrieving username and password set in config.properties file through the getUsername(), and getPassword() methods
			prodLoginPageObject.getUserName(driver).sendKeys(base.getUsername());
			prodLoginPageObject.getPassWord(driver).sendKeys(base.getPassword());
			prodLoginPageObject.getLoginbtn(driver).click();

			//Instance of ProdEmployerConfigPageObject to access the webElements from this page, then enter the employer number and the branch to see the flag configurations for that branch
			ProdEmployerConfigPage ProdEmployerConfigPageObject = new ProdEmployerConfigPage();
			ProdEmployerConfigPageObject.getEmployerGroupSpan(driver).click();
			ProdEmployerConfigPageObject.getEmployerGroupInput(driver).sendKeys("ProdEmployerNumberGoesHere");
			ProdEmployerConfigPageObject.getEmployerGroupInput(driver).sendKeys(Keys.ENTER);
			ProdEmployerConfigPageObject.getBranchSpan(driver).click();
			ProdEmployerConfigPageObject.getBranchInput(driver).sendKeys(branch);
			ProdEmployerConfigPageObject.getBranchInput(driver).sendKeys(Keys.ENTER);
			ProdEmployerConfigPageObject.getProductSpan(driver).click();
			ProdEmployerConfigPageObject.getProductInput(driver).sendKeys("J");
			ProdEmployerConfigPageObject.getProductInput(driver).sendKeys(Keys.ENTER);

			//getProdHashMapWithValues()  creates a hasmap with the flag name, and its value (Enabled or Disabled)
			ProdEmployerConfigPageObject.getProdHashMapWithValues(driver);

			//Same process done for PROD, but in STAGE environment
			driver.get(base.getStageUrl());
			StageLoginPage StageLoginPageObject = new StageLoginPage();
			StageLoginPageObject.getUserName(driver).sendKeys(base.getUsername());
			StageLoginPageObject.getPassword(driver).sendKeys(base.getPassword());
			StageLoginPageObject.getLoginBtn(driver).click();
			
			StageEmployerConfigPage StageEmployerConfigPageObject = new StageEmployerConfigPage();
			StageEmployerConfigPageObject.getEmployerGroupSpan(driver).click();
			StageEmployerConfigPageObject.getEmployerGroupInput(driver).sendKeys("stageEmployerNumberGoesHere");
			StageEmployerConfigPageObject.getEmployerGroupInput(driver).sendKeys(Keys.ENTER);
			StageEmployerConfigPageObject.getBranchSpan(driver).click();
			StageEmployerConfigPageObject.getBranchInput(driver).sendKeys(branch);
			StageEmployerConfigPageObject.getBranchInput(driver).sendKeys(Keys.ENTER);
			StageEmployerConfigPageObject.getProductSpan(driver).click();
			StageEmployerConfigPageObject.getProductInput(driver).sendKeys("J");
			StageEmployerConfigPageObject.getProductInput(driver).sendKeys(Keys.ENTER);
			
			StageEmployerConfigPageObject.getStageHashMapWithValues(driver);

			//Same process done for PROD and STAGE, but in TEST environment
			driver.get(base.getTestUrl());
			TestLoginPage testLoginPageObject = new TestLoginPage();
			testLoginPageObject.getUserName(driver).sendKeys(base.getUsername());
			testLoginPageObject.getPassword(driver).sendKeys(base.getPassword());
			testLoginPageObject.getLoginBtn(driver).click();
			
			TestEmployerConfigPage testEmployerConfigPageObject = new TestEmployerConfigPage();
			testEmployerConfigPageObject.getEmployerGroupSpan(driver).click();
			testEmployerConfigPageObject.getEmployerGroupInput(driver).sendKeys("testEmployerNumberGoesHere");
			testEmployerConfigPageObject.getEmployerGroupInput(driver).sendKeys(Keys.ENTER);
			testEmployerConfigPageObject.getBranchSpan(driver).click();
			testEmployerConfigPageObject.getBranchInput(driver).sendKeys(branch);
			testEmployerConfigPageObject.getBranchInput(driver).sendKeys(Keys.ENTER);
			testEmployerConfigPageObject.getProductSpan(driver).click();
			testEmployerConfigPageObject.getProductInput(driver).sendKeys("J");
			testEmployerConfigPageObject.getProductInput(driver).sendKeys(Keys.ENTER);
			
			testEmployerConfigPageObject.getTestHashMapWithValues(driver);

			//compareHashmaps() compares the 3 hasmaps created for each environment, and adds a sheet to the worksheet with the comparison of the values of the flags across the 3 environments, marking which flags are different from what we have in PROD
			base.compareHashmaps(ProdEmployerConfigPageObject.prodFlagValuelinkedHashMap, StageEmployerConfigPageObject.stageFlagValuelinkedHashMap, testEmployerConfigPageObject.testFlagValuelinkedHashMap);
			base.addSheet(branch);

			//Closes the window
			driver.close();
		}
		
		
	}
	
}


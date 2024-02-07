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
		
		base.createWorksheet();
		
		for(String branch : base.getJHBranchesArray()) {
			driver = base.getDriver();
			driver.get(base.getProdUrl());
			
			ProdLoginPage prodLoginPageObject = new ProdLoginPage();
			//use of explicit wait:
//			WebElement loginButton = base.wait.until(ExpectedConditions.visibilityOfElementLocated(prodLoginPageObject.userName));
//			loginButton.sendKeys(base.getUsername());
			prodLoginPageObject.getUserName(driver).sendKeys(base.getUsername());
			prodLoginPageObject.getPassWord(driver).sendKeys(base.getPassword());
			prodLoginPageObject.getLoginbtn(driver).click();
			
			ProdEmployerConfigPage ProdEmployerConfigPageObject = new ProdEmployerConfigPage();
			ProdEmployerConfigPageObject.getEmployerGroupSpan(driver).click();
			ProdEmployerConfigPageObject.getEmployerGroupInput(driver).sendKeys("1420131938");
			ProdEmployerConfigPageObject.getEmployerGroupInput(driver).sendKeys(Keys.ENTER);
			ProdEmployerConfigPageObject.getBranchSpan(driver).click();
			ProdEmployerConfigPageObject.getBranchInput(driver).sendKeys(branch);
			ProdEmployerConfigPageObject.getBranchInput(driver).sendKeys(Keys.ENTER);
			ProdEmployerConfigPageObject.getProductSpan(driver).click();
			ProdEmployerConfigPageObject.getProductInput(driver).sendKeys("J");
			ProdEmployerConfigPageObject.getProductInput(driver).sendKeys(Keys.ENTER);
			
//			ProdEmployerConfigPageObject.getTableLenght(driver);
			ProdEmployerConfigPageObject.getProdHashMapWithValues(driver);
			
			driver.get(base.getStageUrl());
			StageLoginPage StageLoginPageObject = new StageLoginPage();
			StageLoginPageObject.getUserName(driver).sendKeys(base.getUsername());
			StageLoginPageObject.getPassword(driver).sendKeys(base.getPassword());
			StageLoginPageObject.getLoginBtn(driver).click();
			
			StageEmployerConfigPage StageEmployerConfigPageObject = new StageEmployerConfigPage();
			StageEmployerConfigPageObject.getEmployerGroupSpan(driver).click();
			StageEmployerConfigPageObject.getEmployerGroupInput(driver).sendKeys("1420131938");
			StageEmployerConfigPageObject.getEmployerGroupInput(driver).sendKeys(Keys.ENTER);
			StageEmployerConfigPageObject.getBranchSpan(driver).click();
			StageEmployerConfigPageObject.getBranchInput(driver).sendKeys(branch);
			StageEmployerConfigPageObject.getBranchInput(driver).sendKeys(Keys.ENTER);
			StageEmployerConfigPageObject.getProductSpan(driver).click();
			StageEmployerConfigPageObject.getProductInput(driver).sendKeys("J");
			StageEmployerConfigPageObject.getProductInput(driver).sendKeys(Keys.ENTER);
			
//			StageEmployerConfigPageObject.getTableLenght(driver);
			StageEmployerConfigPageObject.getStageHashMapWithValues(driver);
		
			driver.get(base.getTestUrl());
			TestLoginPage testLoginPageObject = new TestLoginPage();
			testLoginPageObject.getUserName(driver).sendKeys(base.getUsername());
			testLoginPageObject.getPassword(driver).sendKeys(base.getPassword());
			testLoginPageObject.getLoginBtn(driver).click();
			
			TestEmployerConfigPage testEmployerConfigPageObject = new TestEmployerConfigPage();
			testEmployerConfigPageObject.getEmployerGroupSpan(driver).click();
			testEmployerConfigPageObject.getEmployerGroupInput(driver).sendKeys("1414258879");
			testEmployerConfigPageObject.getEmployerGroupInput(driver).sendKeys(Keys.ENTER);
			testEmployerConfigPageObject.getBranchSpan(driver).click();
			testEmployerConfigPageObject.getBranchInput(driver).sendKeys(branch);
			testEmployerConfigPageObject.getBranchInput(driver).sendKeys(Keys.ENTER);
			testEmployerConfigPageObject.getProductSpan(driver).click();
			testEmployerConfigPageObject.getProductInput(driver).sendKeys("J");
			testEmployerConfigPageObject.getProductInput(driver).sendKeys(Keys.ENTER);
			
//			testEmployerConfigPageObject.getTableLenght(driver);
			testEmployerConfigPageObject.getTestHashMapWithValues(driver);
			
			base.compareHashmaps(ProdEmployerConfigPageObject.prodFlagValuelinkedHashMap, StageEmployerConfigPageObject.stageFlagValuelinkedHashMap, testEmployerConfigPageObject.testFlagValuelinkedHashMap);
			
			base.addSheet(branch);
			
			driver.close();
		}
		
		
	}
	
}


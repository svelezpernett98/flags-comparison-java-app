package base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {
	public WebDriver driver;
	private Properties prop;
	private String prodUrl;
	private String stageUrl;
	private String testUrl;
	private String[] jhBranchesArray;
	public WebDriverWait wait;
	public Map<String, String> flagsNotDisplayedForStage = new LinkedHashMap<>();
	public Map<String, String> flagsNotDisplayedForTest = new LinkedHashMap<>();
	public Map<String, List<String>> multiValueMapForComparison = new LinkedHashMap<>();

	public BasePage() throws IOException {
		//Gets the data from config.properties, and loads it into prop
		prop = new Properties();
		FileInputStream data = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\main\\java\\resources\\config.properties");
		prop.load(data);
	}

	//Based on the browser set in config.properties, it initializes the desired WebDriver (Only Chrome is supperted, because it is the only web driver I've downloaded at the moment)
	public WebDriver getDriver() {
		if (prop.getProperty("browser").equals("chrome")) {
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + "\\src\\main\\java\\drivers\\chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--ignore-ssl-errors=yes");
			options.addArguments("--ignore-certificate-errors");
			driver = new ChromeDriver(options);
		}

		if (prop.getProperty("browser").equals("firefox")) {
			System.setProperty("webdriver.chrome.driver", "TBD");
			driver = new FirefoxDriver();
		}

		if (prop.getProperty("browser").equals("edge")) {
			System.setProperty("webdriver.chrome.driver", "TBD");
			driver = new EdgeDriver();
		}

		//Window will start maximized
		driver.manage().window().maximize();

		// Implicit wait:
//		driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);

		// Explicit wait, in case it will be needed:
		wait = new WebDriverWait(driver, 100);

		return driver;
	}

	//Returns variables from config.properties:
	public String getUsername() {
		return prop.getProperty("username");
	}

	//Returns variables from config.properties:
	public String getPassword() {
		return prop.getProperty("password");
	}

	//Returns variables from config.properties:
	public String getProdUrl() {
		prodUrl = prop.getProperty("prodUrl");
		return prodUrl;
	}

	//Returns variables from config.properties:
	public String getStageUrl() {
		stageUrl = prop.getProperty("stageUrl");
		return stageUrl;
	}

	//Returns variables from config.properties:
	public String getTestUrl() {
		testUrl = prop.getProperty("testUrl");
		return testUrl;
	}

	//Returns variables from config.properties:
	public String[] getJHBranchesArray() {
		jhBranchesArray = prop.getProperty("JHbranches").split(",");
		return jhBranchesArray;
	}

	//See description of this method at AppTest.java
	public void compareHashmaps(Map<String, String> mapProd, Map<String, String> mapStage,
			Map<String, String> mapTest) {
		for (Map.Entry<String, String> entry : mapProd.entrySet()) {
			if (mapStage.containsKey(entry.getKey()) || mapTest.containsKey(entry.getKey())) {
				multiValueMapForComparison.put(entry.getKey(), new ArrayList<String>());
				multiValueMapForComparison.get(entry.getKey()).add("prod: " + mapProd.get(entry.getKey()));
				// comparison with prod and stage
				boolean stageIsEqual;
				boolean testIsEqual;
				if (mapStage.containsKey(entry.getKey())) {
					if (mapProd.get(entry.getKey()).equals(mapStage.get(entry.getKey()))) {
						multiValueMapForComparison.get(entry.getKey()).add("stage: " + mapStage.get(entry.getKey()));
						stageIsEqual = true;

					} else {
						multiValueMapForComparison.get(entry.getKey()).add("stage: " + mapStage.get(entry.getKey()));
						stageIsEqual = false;
					}
				} else {
					multiValueMapForComparison.get(entry.getKey()).add("stage: n/a");
					stageIsEqual = false;
					flagsNotDisplayedForStage.put(entry.getKey(), mapProd.get(entry.getKey()));
				}

				// comparison between prod and test
				if (mapTest.containsKey(entry.getKey())) {
					if (mapProd.get(entry.getKey()).equals(mapTest.get(entry.getKey()))) {
						multiValueMapForComparison.get(entry.getKey()).add("test: " + mapTest.get(entry.getKey()));
						testIsEqual = true;
					} else {
						multiValueMapForComparison.get(entry.getKey()).add("test: " + mapTest.get(entry.getKey()));
						testIsEqual = false;
					}
				} else {
					multiValueMapForComparison.get(entry.getKey()).add("test: n/a");
					testIsEqual = false;
					flagsNotDisplayedForTest.put(entry.getKey(), mapProd.get(entry.getKey()));
				}

				if (testIsEqual == false || stageIsEqual == false) {
					multiValueMapForComparison.get(entry.getKey()).add("isNotEqual");
				} else {
					multiValueMapForComparison.get(entry.getKey()).add("isEqual");
				}

			}
			
			else {
				multiValueMapForComparison.put(entry.getKey(), new ArrayList<String>());
				multiValueMapForComparison.get(entry.getKey()).add("prod: " + mapProd.get(entry.getKey()));
				multiValueMapForComparison.get(entry.getKey()).add("stage: n/a");
				multiValueMapForComparison.get(entry.getKey()).add("test: n/a");
				
				flagsNotDisplayedForStage.put(entry.getKey(), mapProd.get(entry.getKey()));
				flagsNotDisplayedForTest.put(entry.getKey(), mapProd.get(entry.getKey()));
			}

		}

	}

	//See description of this method at AppTest.java
	public void createWorksheet() {
		// Specify the file path where you want to save the Excel file
		String filePath = System.getProperty("user.dir") + "\\src\\main\\java\\resources\\worksheet.xlsx";

		try (XSSFWorkbook workbook = new XSSFWorkbook(); FileOutputStream fileOut = new FileOutputStream(filePath)) {
			// Write the workbook content to the Excel file
			workbook.write(fileOut);
			System.out.println("Excel file created successfully at: " + filePath);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	//See description of this method at AppTest.java
	public void addSheet(String branchName) {
		String filePath = System.getProperty("user.dir") + "\\src\\main\\java\\resources\\worksheet.xlsx";

		try (FileInputStream fis = new FileInputStream(filePath); XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

			// Create a new sheet
			XSSFSheet sheet = workbook.createSheet(branchName);

			// Modify the new sheet as needed
			// create cell style
			XSSFCellStyle yellowCellStyle = workbook.createCellStyle();
			yellowCellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex()); // Set the background color to yellow for the roes where entry contains 'isNotEqual'
																						
			yellowCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

			XSSFCellStyle boldStyle = workbook.createCellStyle();
			XSSFFont boldFont = workbook.createFont();
			boldFont.setBold(true);
			boldStyle.setFont(boldFont);

			XSSFRow firstRow = sheet.createRow(0);
			firstRow.createCell(0).setCellValue("Flag name");
			firstRow.createCell(1).setCellValue("Prod");;
			firstRow.createCell(2).setCellValue("Stage");
			firstRow.createCell(3).setCellValue("Test");
			firstRow.createCell(4).setCellValue("isEqual/isNotEqual");

			// Add data from the HashMap to the Excel sheet
			int rownum = 1;
			for (Map.Entry<String, List<String>> entry : multiValueMapForComparison.entrySet()) {
				String key = entry.getKey();
				List<String> values = entry.getValue();

				XSSFRow row = sheet.createRow(rownum++);
				Cell cellFlag = row.createCell(0);
				cellFlag.setCellValue(key);

				// Add list elements to the subsequent cells in the same row
				for (int i = 0; i < values.size(); i++) {
					Cell cell = row.createCell(i + 1);
					cell.setCellValue(values.get(i));

					if (values.contains("isNotEqual")) {
						cell.setCellStyle(yellowCellStyle);
					}
				}
			}

			//Creation of the 2nd table with flags that are in PROD, and not in STAGE
			XSSFRow secondTableFirstRow = sheet.createRow(multiValueMapForComparison.size() + 2);
			secondTableFirstRow.createCell(0).setCellValue("Flags not displayed for Stage");
			secondTableFirstRow.createCell(1).setCellValue("Value in prod");
			int secondTableRowNum = 3;
			for (Map.Entry<String, String> entry : flagsNotDisplayedForStage.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();

				XSSFRow row = sheet.createRow(multiValueMapForComparison.size() + secondTableRowNum++);
				row.createCell(0).setCellValue(key);
				row.createCell(1).setCellValue(value);
			}


			//Creatio of the 3rd table with flags that are in PROD, and not in TEST
			XSSFRow thirdTableFirstRow = sheet
					.createRow(multiValueMapForComparison.size() + flagsNotDisplayedForStage.size() + 4);
			thirdTableFirstRow.createCell(0).setCellValue("Flags not displayed for Test");
			thirdTableFirstRow.createCell(1).setCellValue("Value in prod");
			int thirdTableRowNum = 5;
			for (Map.Entry<String, String> entry : flagsNotDisplayedForTest.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();

				XSSFRow row = sheet.createRow(
						multiValueMapForComparison.size() + flagsNotDisplayedForStage.size() + thirdTableRowNum++);
				row.createCell(0).setCellValue(key);
				row.createCell(1).setCellValue(value);
			}

			// Save the changes to the existing file
			try (FileOutputStream fos = new FileOutputStream(filePath)) {
				workbook.write(fos);
			}

			System.out.println("Sheet successfully added at: " + filePath);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}

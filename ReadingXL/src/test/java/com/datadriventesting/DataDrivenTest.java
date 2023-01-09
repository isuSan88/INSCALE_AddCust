package com.datadriventesting;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DataDrivenTest {
	
	WebDriver driver;
	
	@BeforeClass
	public void setup()
	{
	System.setProperty("webdriver.chrome.driver","./Drivers/chromedriver.exe");
	driver=new ChromeDriver();
	driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
	driver.manage().window().maximize();
			
	}
	@Test(dataProvider = "AddData")
	public void addDataTest(String firstname, String lastname, String postcode) throws InterruptedException
	{
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		
		driver.get("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/manager/addCust");
		
		WebElement txtfname= driver.findElement(By.xpath("//input[@ng-model='fName']"));
		txtfname.clear();
		txtfname.sendKeys(firstname);
		
		WebElement txtlname= driver.findElement(By.xpath("//input[@ng-model='lName']"));
		txtlname.clear();
		txtlname.sendKeys(lastname);
		
		WebElement txtpcode=driver.findElement(By.xpath("//input[@ng-model='postCd']"));
		txtpcode.clear();
		txtpcode.sendKeys(postcode);
		
		driver.findElement(By.xpath("//button[@class='btn btn-default' and text()='Add Customer']")).click();
		Thread.sleep(2000);
		driver.switchTo().alert().accept();
		
	}
	@DataProvider(name="AddData")
	public String [][] getData() throws IOException
	{
		/*String addData[][]= {
				{"F1","L1","P1"},
				{"F2","L2","P2"},
				{"F3","L3","P3"},
				{"F4","L4","P4"},
				
			
		};*/
		
		String path=("./TestData/AddData.xlsx");
		XLUtility xlutil=new XLUtility(path);
		
		int totalrows=xlutil.getRowCount("addingData");
		int totalcols=xlutil.getCellCount("addingData",1);
		
		String addData[] []= new String [totalrows][totalcols];
		
		for(int i=1;i<=totalrows;i++) 
		{
			for(int j=0; j<totalcols;j++)
			{
				addData[i-1][j]=xlutil.getCellData("addingData", i, j) ;
				
			}
			
		}
		
		
				
		
		
		return addData;
	}
	
	/*@AfterClass
	void tearDown() 
	{
		driver.close();
	}*/
}

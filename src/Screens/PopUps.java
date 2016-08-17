package Screens;

import io.appium.java_client.android.AndroidDriver;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

public class PopUps extends CommonPage {
	
	By sysPopUp = By.xpath("//*[@resource-id='android:id/title' and @text='Choose account for Smartprix']");
	
	By tabletTextLink = By.xpath("//*[@resource-id='com.smartprix.main:id/listItemText' and @text='Tablets']");
	
	
	By seeAllTablesLink = By.xpath("//*[@content-desc='See All Tablets Link']");
	
	
	public PopUps(AndroidDriver<WebElement> driver) {
		super(driver);
	}
	
	private void checkSystemPopUp()
	{
		try{
			driver.findElement(sysPopUp);				
			clickBackButton();			
		}catch(Exception e){			
		}
	}
	

	
	private void clickTabletTextLink()
	{
		try{
			driver.findElement(tabletTextLink).click();
			driver.findElement(seeAllTablesLink).click();	
		}catch(Exception e){			
		}		
	}	
	
	public HomeScreenPage checkForPopUp(){
		checkSystemPopUp();
		clickTabletTextLink();
		return new HomeScreenPage(driver);		
	}
}

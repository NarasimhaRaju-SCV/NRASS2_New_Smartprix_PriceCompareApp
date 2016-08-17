package Screens;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class HomeScreenPage extends CommonPage {
	
	By homesScreenLoadChkImage = By.xpath("//*[contains(@resource-id,'/fragment_picture_image')]");
	By homeSearchBtn = By.xpath("//*[@resource-id='com.smartprix.main:id/action_search']");   //menu_home_search
	By homeSearchTextBox = By.xpath("//*[@resource-id='com.smartprix.main:id/search_src_text']");
	By selectFirstDisplayedProduct = By.xpath("(//android.view.View[contains(@content-desc, ' Link')])[1]");
	
	public HomeScreenPage(AndroidDriver<WebElement> driver) {
		super(driver);
		try {
			driver.findElement(homeSearchBtn);
		} catch (NoSuchElementException e) {
			Assert.fail("Home Screen Page did not get displayed");
		}
	}
	
	private void clickHomeSearchBtn() throws Exception
	{
		driver.findElement(homeSearchBtn).click();
		
	}
	
	private void setDataHomeSearchTextBox(String searchText) throws Exception
	{
		driver.findElement(homeSearchTextBox).sendKeys(searchText);		
		driver.pressKeyCode(AndroidKeyCode.ENTER);		
	}
	
	private ProductDisplayPage selectFirstDisplayedProductClick() throws Exception
	{
		driver.findElement(selectFirstDisplayedProduct).click();
		return new ProductDisplayPage(driver);
	}
	
	public ProductDisplayPage searchAndSelectDevice(String deviceName) throws Exception{
		clickHomeSearchBtn();
		setDataHomeSearchTextBox(deviceName);
		return this.selectFirstDisplayedProductClick();
	}
	
	
	
}

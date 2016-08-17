package Screens;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class ProductDisplayPage  extends CommonPage {
	
	

	By productDisplayConfrmation = By.xpath("//*[contains(@content-desc,'Heading')]");
	String ratingTextXpathStr = "//*[contains(@content-desc, '/10 ')]";
	By ratingText = By.xpath(ratingTextXpathStr);
	
	By comparePricesLink = By.xpath("//*[@content-desc='Compare Prices']");	
	String thirdLowestPriceXpathStr = "(//*[contains(@content-desc, 'Shop Now Link')])[3]/../android.view.View[2]";
	By thirdLowestPrice = By.xpath(thirdLowestPriceXpathStr);
	
	
	public ProductDisplayPage(AndroidDriver<WebElement> driver) throws Exception {
		super(driver);
		try {
			driver.findElement(productDisplayConfrmation);
		} catch (NoSuchElementException e) {
			Assert.fail("Element not found in the Product Display page");
		}
	}
	
	public String getRatingText() throws XPathExpressionException, ParserConfigurationException, SAXException, IOException
	{		
		driver.findElement(ratingText);
		String ratingStr = getAttribute(ratingTextXpathStr, "content-desc").split(" ")[0];		
		return ratingStr;
	}	
	
	public ProductDisplayPage clickComparePricesLink()
	{
		driver.findElement(comparePricesLink).click();
		return this;
	}
		
	
	public String fetchThirdLowestPriceText() throws Exception
	{
		driver.findElement(thirdLowestPrice);
		String thirdLowestPriceStr = getAttribute(thirdLowestPriceXpathStr, "content-desc");		
		return thirdLowestPriceStr;				
	}	

}

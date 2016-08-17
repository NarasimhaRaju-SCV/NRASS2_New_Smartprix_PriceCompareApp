/**
 * 
 */
package Screens;
import io.appium.java_client.android.AndroidDriver;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang3.StringEscapeUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author Narasimha Raju
 *
 */
public class CommonPage {

	protected final AndroidDriver<WebElement> driver;

	//constructor returns the driver instance
	public CommonPage(AndroidDriver<WebElement> driver) {
		this.driver = driver;
	}

	public void clickBackButton()
	{
		Map<String, Object> params = new HashMap<>();
		params.put("keySequence", "BACK");
		Object result2 = driver.executeScript("mobile:presskey", params);
	}
	
	public String getAttribute(String xpathExpression, String attributeName) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException
	{
		String retStr = "";
		String pageSource = driver.getPageSource();
				
		Pattern p = Pattern.compile("\"([^\"]*)\"");
		Matcher m = p.matcher(pageSource);
		while (m.find()) {
			String current = m.group(1);
			String encodedCurr = StringEscapeUtils.escapeXml(current);		  
			pageSource = pageSource.replace("\""+current+"\"", "\""+encodedCurr+"\"");
		}
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(new InputSource(new StringReader(pageSource)));
		XPathFactory xPathfactory = XPathFactory.newInstance();
		XPath xpath = xPathfactory.newXPath();
		XPathExpression expr = xpath.compile(xpathExpression);
		NodeList nList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
		
		if(nList.getLength() >0)
			retStr = ((Element) nList.item(0)).getAttribute(attributeName);
		
		return retStr;
				
	}
}

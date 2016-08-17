/**
 * 
 */
package Test;

import io.appium.java_client.android.AndroidDriver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import Screens.PopUps;
import Screens.ProductDisplayPage;
import com.perfectomobile.selenium.util.EclipseConnector;
import Utilities.Common;
import Utilities.DButil;
import Utilities.PerfectoLabUtils;


/**
 * @author Narasimha Raju
 *
 */
@Test
public class MainTest {
	private AndroidDriver<WebElement> driver;
	private DesiredCapabilities capabilities;
	private String reportPath;
	
	 @Parameters({ "host", "user" , "password", "persona", "platformName", "manufacturer", "model", "apkfilename", "appPackage"})
	   @BeforeMethod 
	   public void beforeClass(String host, String user, String password, String persona, String platformName, String manufacturer, String model, String apkfilename, String appPackage) throws IOException, InterruptedException{
	         
	        capabilities = new DesiredCapabilities("mobileOS", "", Platform.ANY);
	        capabilities.setCapability("automationName", "Appium");
	        capabilities.setCapability("platformName", platformName);	        
	        capabilities.setCapability("manufacturer", manufacturer);
	        capabilities.setCapability("model", model);
	     
	        capabilities.setCapability("user", user);
	        capabilities.setCapability("password", password);
		    
        
		    //Uploading the apk file in the Perfecto cloud
		    String localAPKPath = System.getProperty("user.dir")+"\\TestApps\\"+apkfilename;
		    String repositoryAPKPath = "PRIVATE:Narasimha_Per/"+apkfilename;
		    PerfectoLabUtils.uploadMedia(host, user, password, localAPKPath, repositoryAPKPath);
		    
		    //Installing Aplication
			capabilities.setCapability("app", repositoryAPKPath);
			capabilities.setCapability("autoInstrument", true);
			capabilities.setCapability("fullReset",true); 
			capabilities.setCapability("appPackage", appPackage);
			
			//persona
			capabilities.setCapability("windTunnelPersona", persona);
			
		    setExecutionIdCapability(capabilities, host);
	        driver = new AndroidDriver<WebElement>(new URL("https://" + host + "/nexperience/perfectomobile/wd/hub"), capabilities);
	        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);	 
	        driver.context("NATIVE_APP");
			setReportPath();				
			
	   }
	
	   @DataProvider(name="priceCompareInput")
		public Iterator<Object[]> compareTabPrcInputDataWorkbook() throws FileNotFoundException, IOException, ClassNotFoundException, SQLException {	
			
			ArrayList<Object[]> da = new ArrayList<Object[]>();
			
			DButil dbObj = new DButil("localhost", "3306", "testdatadb", "narasimha", "qatest");
			List<HashMap<String, Object>> resulSet = dbObj.executeQuery("select * from pricecompare where isenabled = 1;");
			
			for(HashMap<String, Object> dataRow: resulSet)			
				da.add(new Object[]{dataRow.get("devicename")});
			
//			da.add(new Object[]{"Samsung Galaxy Tab E"});
//			da.add(new Object[]{"APPLE IPAD MINI 2"});
//			da.add(new Object[]{"Micromax Canvas Tablet P680"});
			
			return da.iterator();	
		}
	
	 @Test(dataProvider="priceCompareInput")
	 public void compareTabletPrices(String deviceName) throws Exception  {
		 
		 String pagesourse = driver.getPageSource();
		 System.out.println(pagesourse);
		 ProductDisplayPage productDisplayPageObj = new PopUps(driver).checkForPopUp()
				 																.searchAndSelectDevice(deviceName);				 												
					
		 String reviewRating = productDisplayPageObj.getRatingText();
		 System.out.println("Rating is :"+reviewRating);			
		 Reporter.log("Rating is :"+reviewRating+"\n");
					
		 String thirdLowestPrice = productDisplayPageObj.clickComparePricesLink()
				 											.fetchThirdLowestPriceText();
		 System.out.println("Third Lowest Price is :"+thirdLowestPrice);
		 Reporter.log("Third Lowest Price is :"+thirdLowestPrice+"\n");
	 }
	 
		@AfterMethod
		   public void afterClass() {
		     try{
		         // Close the browser
		         driver.close();
		             
		         // Download a pdf version of the execution report		         
		         PerfectoLabUtils.downloadReport(driver, "pdf", reportPath+"\\report");
		         }
		         catch(Exception e){
		             e.printStackTrace();
		         }
		     driver.quit();
		   }
	 
	 
	 
	   private void setReportPath() {
		   reportPath = System.getProperty("user.dir")+"\\reports\\DeviceID-"+getDeviceID()+"_TimeStamp-"+Common.getTimeStamp();
	        new File(reportPath).mkdirs();	
	   }
	   private String getDeviceID()
	   {
		   Map<String, Object> params = new HashMap<>();
		   params.put("property", "deviceId");
		   return (String)driver.executeScript("mobile:handset:info", params);	
	   }
	 

		
	    private static void setExecutionIdCapability(DesiredCapabilities capabilities, String host) throws IOException  {
	        EclipseConnector connector = new EclipseConnector();
	        String eclipseHost = connector.getHost();
	        if ((eclipseHost == null) || (eclipseHost.equalsIgnoreCase(host))) {
	            String executionId = connector.getExecutionId();
	            capabilities.setCapability(EclipseConnector.ECLIPSE_EXECUTION_ID, executionId);
	        }
	    }
}

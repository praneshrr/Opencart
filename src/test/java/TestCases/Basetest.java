package TestCases;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class Basetest {
	
	public static WebDriver driver;
	public Logger logger;
	public Properties p;
	
	@BeforeClass(groups={"regression", "master","sanity"})
	@Parameters({"os", "browser"})
	public void setup(String os, String br) throws IOException
	{
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
		options.setExperimentalOption("useAutomationExtension", false);
		options.addArguments("--disable-blink-features=AutomationControlled");
		options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36");
		options.addArguments("--proxy-server=http://<proxy-ip>:<proxy-port>");


		
		FileReader file = new FileReader("./src//test//resources//config.properties");
		p= new Properties();
		p.load(file);
		
		
		logger = LogManager.getLogger(this.getClass());
		
		if (p.getProperty("execution_env").equalsIgnoreCase("remote")) {
		    DesiredCapabilities capabilities = new DesiredCapabilities();
		    
		    // Set platform based on OS
		    if (os.equalsIgnoreCase("windows")) {
		        capabilities.setPlatform(Platform.WIN11);
		    } else if (os.equalsIgnoreCase("mac")) {
		        capabilities.setPlatform(Platform.MAC);
		    } else {
		        System.out.println("No matching OS");
		        return;
		    }

		    // Set browser based on input
		    switch (br.toLowerCase()) {
		        case "chrome":
		            capabilities.setBrowserName("chrome");
		            break;
		        case "edge":
		            capabilities.setBrowserName("MicrosoftEdge");
		            break;
		        default:
		            System.out.println("No matching browser");
		            return;
		    }

		    // Continue with the remote WebDriver setup
		    // e.g., RemoteWebDriver driver = new RemoteWebDriver(new URL("http://remote-server-url"), capabilities);
		    
		    driver=new RemoteWebDriver (new URL ("http://localhost:4444/wd/hub"), capabilities);
		}

		if (p.getProperty("execution_env").equalsIgnoreCase("local")) {		
			switch(br.toLowerCase())
			{
			case "chrome" : driver = new ChromeDriver(options); break;
			case "edge" : driver = new EdgeDriver(); break;
			case "firefox": driver = new FirefoxDriver(); break;
			default : System.out.println("Invailed browser name"); return;
			}
		}
		
		
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.get(p.getProperty("appurl"));
		driver.manage().window().maximize();
		
	}
	
	@AfterClass(groups={"regression", "master","sanity"})
	public void teardown()
	{
		driver.quit();
	}
	
	public String randomalphabetic()
	{
		String randomstring = RandomStringUtils.randomAlphabetic(5);
		return randomstring;
	}
	
	public String randomnumeric()
	{
		String randomnumber = RandomStringUtils.randomNumeric(5);
		return randomnumber;
	}
	
	public String randomalphanumeric()
	{
		String randomstring = RandomStringUtils.randomAlphanumeric(10);
		return randomstring;
	}
	
	public String captureScreen(String name) throws IOException {
	    String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	    
	    TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
	    File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
	    
	    // Use the correct file separator and variable names
	    String targetFilePath = System.getProperty("user.dir") + "\\Screenshots\\" + name + "_" + timeStamp + ".png";
	    File targetFile = new File(targetFilePath);

	    sourceFile.renameTo(targetFile);
	    
	    return targetFilePath;
	}


}

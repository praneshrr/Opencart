package PageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyAccountPage extends Basepage{

	public MyAccountPage(WebDriver driver)
	{
		super(driver);
	}	
	
	@FindBy(xpath="//h2[normalize-space()='My Account']") WebElement msgmyaccount;
	@FindBy(xpath="//a[@class='list-group-item'][normalize-space()='Logout']") WebElement btnlogout;	
	
  
    public String verifylogin() 
    {
	  String statusmsg = msgmyaccount.getText();
	  return statusmsg;
    }
    
    public boolean verifypageexists()
    {
    	try
    	{
    		return (msgmyaccount.isDisplayed());
    	}
    	catch(Exception e)
    	{
    		return false;
    	}
    }
    
    
    public void logout()
    {
    	btnlogout.click();
    }
    
}

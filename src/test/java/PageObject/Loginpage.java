package PageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Loginpage extends Basepage{

	
	public Loginpage(WebDriver driver)
	{
		super(driver);
	}

	@FindBy(xpath="//input[@id='input-email']") WebElement txtemail;
	@FindBy(xpath="//input[@id='input-password']") WebElement txtpwd;
	@FindBy(xpath="//input[@value='Login']") WebElement btnlogin;
    @FindBy(xpath="//h2[normalize-space()='My Account']") WebElement msgconfirmation ;
	
	public void email(String email)
	{
		txtemail.sendKeys(email);
	}
	
	public void password(String pwd)
	{
		txtpwd.sendKeys(pwd);
	}
	
	public void login()
	{
		btnlogin.click();
	}
	
	public String getconfirmationmsg()
	{
		String text = msgconfirmation.getText();
		return text;
		
	}
}

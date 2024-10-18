package PageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RegisterAccountpage extends Basepage{
	
	public RegisterAccountpage(WebDriver driver)
	{
		super(driver);
	}
	

	@FindBy(xpath="//input[@id='input-lastname']") WebElement txtlname;
	@FindBy(xpath="//input[@id='input-firstname']") WebElement txtfname;
	@FindBy(xpath="//input[@id='input-email']") WebElement txtemail;
	@FindBy(xpath="//input[@id='input-telephone']") WebElement txtphone;
	@FindBy(xpath="//input[@id='input-password']") WebElement txtpwd;
	@FindBy(xpath="//input[@id='input-confirm']") WebElement txtcpwd;
	@FindBy(xpath="//input[@name='agree']") WebElement chkprivacypolicy;
	@FindBy(xpath="//input[@value='Continue']") WebElement btncontinue;
	@FindBy(xpath="//h1[normalize-space()='Your Account Has Been Created!']") WebElement msgconfirmation;
	
	public void firstname(String fname)
	{
		txtfname.sendKeys(fname);
	}
	
	public void lastname(String lname)
	{
		txtlname.sendKeys(lname);
	}
	
	public void email(String email)
	{
		txtemail.sendKeys(email);
	}
	
	public void phone(String phone)
	{
		txtphone.sendKeys(phone);
	}
	
	public void password(String pwd)
	{
		txtpwd.sendKeys(pwd);
	}
	
	public void confirmpassword(String cpwd)
	{
		txtcpwd.sendKeys(cpwd);
	}
	
	public void privacypolicy()
	{
		chkprivacypolicy.click();
	}
	
	public void clickcontinue()
	{
		btncontinue.click();;
	}
	
//	/Your Account Has Been Created!
	
	public String getconfirmationmsg()
	{
		try {
			return (msgconfirmation.getText());
		}
		catch (Exception e)
		{
			return (e.getMessage());
		}
	}
}

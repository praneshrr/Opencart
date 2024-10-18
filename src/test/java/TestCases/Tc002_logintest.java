package TestCases;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import PageObject.HomePage;
import PageObject.Loginpage;


public class Tc002_logintest extends Basetest {
	
  @Test(groups={"regression", "master"})
  public void login() throws InterruptedException {
	  
	  logger.info("**** Stared Test case *****");
	  logger.info("**** Homepage *****");
	  HomePage myaccount = new HomePage(driver);
	  
	  myaccount.clickmyaccount();
	  myaccount.clicklogin();
	  
	  logger.info("**** LoginPage *****");
	  Loginpage lp = new Loginpage(driver);
	  
	  lp.email(p.getProperty("email"));
	  lp.password(p.getProperty("pwd")); 
	  lp.login();	  
	  String msg = lp.getconfirmationmsg();
	  Assert.assertEquals(msg,"My Account");  
  }
}

package TestCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import PageObject.HomePage;
import PageObject.Loginpage;
import PageObject.MyAccountPage;
import Utilities.Dataprovider;

public class Tc003_datadriventest extends Basetest{
	
  @Test(dataProvider="LoginData", dataProviderClass=Dataprovider.class, groups={"datadriven", "master"})
  public void loginDDT(String email, String pwd, String result) throws InterruptedException 
  { 
	    logger.info("**** Stared Tc003_datadriventest *****");
		
		logger.info("**** Homepage *****");
		HomePage account = new HomePage(driver);
		account.clickmyaccount();
		account.clicklogin();
		
		logger.info("**** LoginPage *****");
		Loginpage lp = new Loginpage(driver);
		lp.email(email);
		lp.password(pwd);
		lp.login();
		
		logger.info("**** MyAccountPage *****");
		MyAccountPage acc = new MyAccountPage(driver);
		boolean statusmsg = acc.verifypageexists();

		if(result.equalsIgnoreCase("vaild"))
		{
			if(statusmsg)
			{
				acc.logout();
				Assert.assertTrue(true);
			}
			else
			{
				Assert.assertTrue(false);
			}
		}
		else
		{
			if(statusmsg)
			{
				acc.logout();
				Assert.assertTrue(false);				
			}
			else
			{
				Assert.assertTrue(true);
			}
		}
		
		/* vaild -> login suc -> test pass -> logot
		 *       -> login fail -> test fail -> 
		 * Invaild -> login suc -> test fail -> logout
		 * 			-> login fail -> test pass ->      
		 */
				
		logger.info("**** Finished Tc003_datadriventest *****");
  }
}

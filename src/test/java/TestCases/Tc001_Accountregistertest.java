package TestCases;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import PageObject.HomePage;
import PageObject.RegisterAccountpage;

public class Tc001_Accountregistertest extends Basetest {

	
	@Test(groups={"sanity", "master"})
	public void accregistation() throws InterruptedException
	{
		try
		{
			logger.info("**** Stared Test case *****");
			
			logger.info("**** Homepage *****");
			HomePage myaccount = new HomePage(driver);
			myaccount.clickmyaccount();
			myaccount.clickregister();
			
			logger.info("**** RegisterAccountPage *****");
			RegisterAccountpage accountcreation = new RegisterAccountpage(driver);
			accountcreation.firstname(randomalphabetic().toUpperCase());
			accountcreation.lastname(randomalphabetic().toUpperCase());
			accountcreation.email(randomalphabetic().toLowerCase() + "@gmail.com");
			accountcreation.phone(randomnumeric());
			String pwd = randomalphabetic();
			accountcreation.password(pwd);
			accountcreation.confirmpassword(pwd);
			accountcreation.privacypolicy();
	//		Thread.sleep(9000);
			accountcreation.clickcontinue();	
			logger.info("**** validating *****");
//			SoftAssert sa = new SoftAssert();		
//			sa.assertEquals(accountcreation.getconfirmationmsg(), "Your Account Has Been Ceated!");	
//			sa.assertAll();
			
			if (accountcreation.getconfirmationmsg().equals("Your Account Has Been Created!"))
			{
				Assert.assertTrue(true);				
			}
			else
			{
				logger.error("Test failed");
				Assert.assertTrue(false);
			}
			logger.info("**** Finished Test case *****");
		}
		catch (Exception e)
		{
//			logger.debug("Debug logs");
			logger.error("Test failed");
		}
	}

	
	
}

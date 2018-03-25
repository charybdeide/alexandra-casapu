package ro.ale.com;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CoCoinTests {

    AndroidDriver driver;

    @BeforeSuite
    void getDriver() throws MalformedURLException {

        File appPath = new File("CoCoin.apk");

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("app", appPath);
        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("automationName", "UiAutomator2");
        capabilities.setCapability("deviceName", "emulator-5554");
        capabilities.setCapability("appWaitActivity", "com.nightonke.saver.activity.ShowActivity");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @BeforeMethod
    void restartApp() {
        driver.resetApp();
    }

    @AfterSuite
    void tearDown() {
        driver.quit();
    }

    void swipe(int startX, int startY, int endX, int endY, int durationMilis) {
        TouchAction touchAction = new TouchAction(driver);
        touchAction.press(startX, startY).waitAction(durationMilis).moveTo(endX, endY).release().perform();
    }

    void swipeThroughInitialTabs() {
        MobileElement firstTab = (MobileElement) driver.findElementByClassName("android.support.v4.view.ViewPager");
        int startX = firstTab.getSize().width - 5;
        int startY = firstTab.getSize().height/12;
        int endX = 5;
        int endY = startY;

        for(int i = 0; i < 4; i++)
        {
            swipe(startX, startY, endX, endY, 500);
        }
    }

    @Test(priority=1)
    void appFirstScreenIsDisplayed() {
        DriverActivity activity = new DriverActivity();
        Assert.assertTrue(activity.checkCurrentActivity(driver, "com.nightonke.saver.activity.ShowActivity"));
    }

    @Test(priority=2)
    void canSetPassword() {
        AppKeyboard keyboard = new AppKeyboard(driver);
        DriverActivity activity = new DriverActivity();

        swipeThroughInitialTabs();

        keyboard.insertPassword("com.nightonke.cocoin:id/textview");

        MobileElement passwordTip = (MobileElement) driver.findElementById("com.nightonke.cocoin:id/password_tip");
        Assert.assertTrue(passwordTip.isDisplayed());

        keyboard.insertPassword("com.nightonke.cocoin:id/textview");

        MobileElement hamburgerMenu = (MobileElement) driver.findElementById("com.nightonke.cocoin:id/content_hamburger");
        Assert.assertTrue(hamburgerMenu.isDisplayed());
        Assert.assertTrue(activity.checkCurrentActivity(driver, "com.nightonke.saver.activity.MainActivity"));
    }

    @Test(priority=3)
    void canInsertExpense() {
        AppKeyboard keyboard = new AppKeyboard(driver);

        swipeThroughInitialTabs();
        keyboard.insertPassword("com.nightonke.cocoin:id/textview");
        MobileElement passwordTip = (MobileElement) driver.findElementById("com.nightonke.cocoin:id/password_tip");
        keyboard.insertPassword("com.nightonke.cocoin:id/textview");
        MobileElement hamburgerMenu = (MobileElement) driver.findElementById("com.nightonke.cocoin:id/content_hamburger");

        keyboard.clickOnKeyWithValue("2");
        keyboard.clickOnKeyWithValue("3");
        Assert.assertEquals(driver.findElementById("com.nightonke.cocoin:id/money").getText(), "23");

        driver.findElementByXPath("//android.widget.GridView/android.widget.LinearLayout[1]").click();
        List<MobileElement> icons = (List<MobileElement>) driver.findElementsById("com.nightonke.cocoin:id/icon");
        icons.get(1).click();

        long startingTime = System.currentTimeMillis();
        long currentTime = System.currentTimeMillis();
        while(currentTime - startingTime < 3000)
        {
            if(driver.findElementsById("com.nightonke.cocoin:id/tag_image").size() == 8)
            {
                break;
            }
            currentTime = System.currentTimeMillis();
        }

        Assert.assertEquals(driver.findElementById("com.nightonke.cocoin:id/money").getText(), "0");

    }

}

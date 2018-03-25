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

    
    @Test
    void checkAppFirstScreenIsDisplayed() {
        DriverActivity activity = new DriverActivity();
        Assert.assertTrue(activity.checkCurrentActivity(driver, "com.nightonke.saver.activity.ShowActivity"));
    }

    @Test
    void setPassword() {
        swipeThroughInitialTabs();

        AppKeyboard keyboard = new AppKeyboard();
        keyboard.insertPassword(driver, "com.nightonke.cocoin:id/textview");

        MobileElement passwordTip = (MobileElement) driver.findElementById("com.nightonke.cocoin:id/password_tip");
        Assert.assertTrue(passwordTip.isDisplayed());

        keyboard.insertPassword(driver, "com.nightonke.cocoin:id/textview");

        MobileElement hamburgerMenu = (MobileElement) driver.findElementById("com.nightonke.cocoin:id/content_hamburger");
        Assert.assertTrue(hamburgerMenu.isDisplayed());
        DriverActivity activity = new DriverActivity();
        Assert.assertTrue(activity.checkCurrentActivity(driver, "com.nightonke.saver.activity.MainActivity"));

    }


}

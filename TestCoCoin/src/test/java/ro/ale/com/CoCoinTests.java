package ro.ale.com;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class CoCoinTests {

    AndroidDriver driver;

    @BeforeClass
    void getDriver() throws MalformedURLException {

        File appPath = new File("CoCoin.apk");

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("app", appPath);
        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("automationName", "UiAutomator2");
        capabilities.setCapability("deviceName", "emulator-5554");
        capabilities.setCapability("appWaitActivity", "com.nightonke.saver.activity.ShowActivity");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @AfterClass
    void tearDown() {
        driver.quit();
    }

    @Test
    void appFirstScreenIsDisplayed() {
        DriverActivity activity = new DriverActivity();
        Assert.assertTrue(activity.checkCurrentActivity(driver, "com.nightonke.saver.activity.ShowActivity"));
    }
}

package ro.ale.com;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.testng.Assert;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AppKeyboard {

    private final AndroidDriver driver;

    public AppKeyboard(AndroidDriver driver) {
        this.driver = driver;
    }

    protected void insertPassword(String keyId) {
        MobileElement key = (MobileElement) driver.findElementById(keyId);
        for(int i = 0; i < 4; i++)
        {
            key.click();
        }
    }

    protected void clickOnKeyWithValue(String value) {
        List<MobileElement> allKeys = (List<MobileElement>) driver.findElementsById("com.nightonke.cocoin:id/textview");
        Optional<MobileElement> key = allKeys.stream()
                .filter(keyElement -> keyElement.getText().equals(value))
                .findFirst();
        Assert.assertTrue(key.isPresent(), "Key with value " + value + " was not found");
        key.get().click();
    }
}


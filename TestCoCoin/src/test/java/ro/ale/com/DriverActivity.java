package ro.ale.com;

import io.appium.java_client.android.AndroidDriver;

public class DriverActivity {
    public boolean checkCurrentActivity(AndroidDriver driver, String name) {
        return driver.currentActivity().equals(name);
    }
}

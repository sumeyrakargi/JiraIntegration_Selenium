package utilities;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.time.Duration;

public class Driver {

    private static ThreadLocal< WebDriver> driver=new ThreadLocal<>();

    private Driver(){

    }

    public static WebDriver getDriver(){

        if(driver.get() == null){
            String browser = ConfigReader.getProperty("browser");
            switch (browser){

                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    driver.set(new ChromeDriver());
                    break;

                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    driver.set(new FirefoxDriver());
                    break;
            }

            driver.get().manage().window().maximize();
            driver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        }

        return driver.get();
    }

    public static void closeDriver(){
        if(driver != null){

            driver.get().quit();
            driver = null;

        }
    }

}


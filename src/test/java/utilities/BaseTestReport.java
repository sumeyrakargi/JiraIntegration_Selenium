package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.openqa.selenium.interactions.Actions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseTestReport {

    protected Actions actions;

    protected ExtentReports extentReports; //extent report'a ilk atamayi yapar
    protected ExtentHtmlReporter extentHtmlReporter; // Html raporu duzenler
    protected ExtentTest extentTest;


    @BeforeSuite
    public void beforeSuite(){

        //Create extent report
        extentReports = new ExtentReports(); // Raporlamayi baslatir

        // Report PATH= creates the html report right under test-output
        //rapor oluştuktan sonra raporunuz nereye eklensin istiyorsanız buraya yazıyorsunuz.
        String currentDate = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss").format(new Date());
        String filePath = System.getProperty("user.dir") + "/test-output/report/" + currentDate + "test_report.html";

        //Attach html and extent reports
        //oluşturmak istediğimiz raporu (html formatında) başlatıyoruz, filePath ile dosya yolunu belirliyoruz.
        extentHtmlReporter = new ExtentHtmlReporter(filePath);
        extentReports.attachReporter(extentHtmlReporter);

        // Add custom information
        // İstediğiniz bilgileri buraya ekeyebiliyorsunuz.
        extentReports.setSystemInfo("Environment", "QA");
        extentReports.setSystemInfo("Browser", "Chrome"); // chrome, firefox
        extentReports.setSystemInfo("Team", "05");
        extentHtmlReporter.config().setDocumentTitle("Report");
        extentHtmlReporter.config().setReportName("TestNG Reports");
        extentHtmlReporter.config().setDocumentTitle(new SimpleDateFormat("ddMMyyyy_hh_mm_ss").format(new Date()) + "_Son Test");//test doc uzerinde tarih yazili olacak


    }


    @AfterMethod
    public void teardown(ITestResult result) throws IOException {

        if(result.getStatus() == ITestResult.FAILURE){
            String screenShotLocation = ReusableMethods.getScreenshot();
            extentTest.fail(result.getName());
            extentTest.addScreenCaptureFromPath(screenShotLocation);
            extentTest.fail(result.getThrowable());
        }
        else if (result.getStatus() == ITestResult.SKIP) { // eğer test çalıştırılmadan geçilmezse
            extentTest.skip("Test Case is skipped: " + result.getName()); // Ignore olanlar
        }

//        Driver.getDriver().quit();
    }

    @AfterSuite
    public void afterSuite(){
        extentReports.flush();
    }
}

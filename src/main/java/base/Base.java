package base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.ExtentReportManager;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.google.common.io.Files;

public class Base {
	public static WebDriver driver;
	public static Properties prop;
	public static WebDriverWait wait;
	public ExtentReports report = ExtentReportManager.getReportInstance();
	public ExtentTest logger;

	// To call different browsers
	public void invokeBrowser() {
		prop = new Properties();

		try {
			prop.load(new FileInputStream("src/main/java/Config/config.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		// To Open Chrome Browser
		if (prop.getProperty("browserName").matches("chrome")) {

			WebDriverManager.chromedriver().setup();
//			System.setProperty("webdriver.chrome.driver",
//					System.getProperty("user.dir") + "\\Webdrivers\\chromedriver.exe");
			driver = new ChromeDriver();
		}

		// To Open Edge Browser
		if (prop.getProperty("browserName").matches("edge")) {
			WebDriverManager.chromedriver().setup();
//			System.setProperty("webdriver.edge.driver",
//					System.getProperty("user.dir") + "\\Webdrivers\\msedgedriver.exe");
			driver = new EdgeDriver();
		}

		// To maximize the Browser Window
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

	}

	// To open the Main Page URL
	public void openURL(String websiteURLKey) {
		driver.get(prop.getProperty(websiteURLKey));
	}

	// Finding WebElement using By method
	public WebElement findElement(By by) throws Exception {
		WebElement element = driver.findElement(by);
		return element;
	}

	// To submit Data in Corporate Wellness Page
	public void submitData() throws InterruptedException {

		By button = By.xpath("//*[@id='app']/div/div/header[2]/div[2]/div/form/button");

		WebElement submit = driver.findElement(button);
		submit.click();
		wait = new WebDriverWait(driver, 200);
		wait.until(ExpectedConditions.alertIsPresent());
	}

	// To show failed test cases in the report
	public void reportFail(String report) {
		logger.log(Status.FAIL, report);
		takeScreenShotOnFailure();
	}

	// To show passed test cases in the report
	public void reportPass(String report) {
		logger.log(Status.PASS, report);
	}

	// To take Screenshots
	public void Screenshot(String fileName) throws IOException {
		TakesScreenshot capture = (TakesScreenshot) driver;
		File srcFile = capture.getScreenshotAs(OutputType.FILE);
		Long dateTime = System.currentTimeMillis();
		File destFile = new File(System.getProperty("user.dir") + "/Screenshot/" + dateTime + "_" + fileName + ".png");
		Files.copy(srcFile, destFile);
	}

	// To take Screenshot of failed test
	public void takeScreenShotOnFailure() {

		TakesScreenshot takeScreenshot = (TakesScreenshot) driver;
		File src = takeScreenshot.getScreenshotAs(OutputType.FILE);
		File dest = new File(System.getProperty("user.dir") + "/Screenshot/FailedCases/Screenshot.png");
		try {
			FileUtils.copyFile(src, dest);
			logger.addScreenCaptureFromPath(System.getProperty("user.dir") + "/Screenshot/FailedCases/Screenshot.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// To input all data to the report
	public void endReport() {
		report.flush();
	}

	// To close the Browser
	public void closeBrowser() {
		driver.quit();
	}

}

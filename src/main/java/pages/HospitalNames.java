package pages;

import java.io.FileReader;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.json.simple.*;

import base.Base;

public class HospitalNames extends Base {
	public static JSONArray data = null;
	By Location = By.xpath("//input[@placeholder='Search location']");
	By place = By.xpath("//div[text()='Bangalore']");
	By hospital = By.xpath("//input[@data-input-box-id='omni-searchbox-keyword']");
	By search = By.xpath("//div[text()='Hospital']");
	By CB24x7 = By.xpath("//div[@data-qa-id='Open_24X7_checkbox']");
	By Filters = By.xpath("//span[text()='All Filters']");
	By Parking = By.xpath("//span[text()='Has Parking']");
	By Ratings = By.xpath("//span[@class='common__star-rating__value']");
	By HNames = By.xpath("//*[@data-qa-id='hospital_name']");
	By total = By.xpath("//span[@data-qa-id='results_count']");

	public JSONArray getDataFromJSON() {
		try {
			JSONParser parser = new JSONParser();
			FileReader reader = new FileReader(
					System.getProperty("user.dir") + "/src/test/resources/InputDetails.json");
			Object obj = parser.parse(reader);
			JSONObject obj1 = (JSONObject) obj;
			JSONArray data1 = (JSONArray) obj1.get("Data");
			data = data1;
		} catch (Exception e) {
			reportFail("Error reading JSON file");
		}
		return data;
	}

	public void getHospitalNames() throws InterruptedException {

		logger = report.createTest("Finding Hospital Names");
		// get data from JSON
		data = getDataFromJSON();

		// To select the place as Banglore
		try {
			WebElement location = findElement(Location);
			location.clear();
			// to get location from json file
			JSONObject data1 = (JSONObject) data.get(0);
			String locationFromJSON = (String) data1.get("Location");

			location.sendKeys(locationFromJSON);
			wait = new WebDriverWait(driver, 15);
			wait.until(ExpectedConditions.visibilityOfElementLocated(place));
			findElement(place).click();
			reportPass("Bangalore is selected Successfully");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}

		// To find Hospitals
		try {
			// to get search data from json file
			JSONObject data2 = (JSONObject) data.get(0);
			String searchFromJSON = (String) data2.get("Search");

			findElement(hospital).sendKeys(searchFromJSON);
			wait.until(ExpectedConditions.visibilityOfElementLocated(search));
			findElement(search).click();
			reportPass("Hospital is selected Successfully");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}

		// To select the filter "Open 24X7"
		try {
			findElement(CB24x7).click();
			wait.until(ExpectedConditions.urlContains("&filters%5Bscout_has_24x7_timings%5D=true"));
			reportPass("Open 24X7 is selected Successfully");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}

		// To select the filter "Has Parking"
		try {
			findElement(Filters).click();
			findElement(Parking).click();
			reportPass("Has Parking is selected Successfully");
			TimeUnit.SECONDS.sleep(4);
			Screenshot("Hospitals");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}

		// To find the names of the hospitals with rating > 3.5
		try {
			System.out.println("*************************************************************");
			System.out.println("            Hospitals with ratings > 3.5 :");
			System.out.println("*************************************************************");
			int totalresults = Integer.parseInt(driver.findElement(total).getText());
			int pages = totalresults / 10 + 1;
			for (int p = 2; p <= pages; p++) {
				List<WebElement> ratings = driver.findElements(Ratings);
				List<WebElement> hnames = driver.findElements(HNames);
				for (int i = 0; i < ratings.size(); i++) {
					float rate = Float.parseFloat(ratings.get(i).getText());
					if (rate > 3.5) {
						System.out.println(ratings.get(i).getText() + " - " + hnames.get(i).getText());
					}
				}

				// Proceeds to next Page
				findElement(By.xpath("//a[contains(@data-qa-id,'pagination_" + p + "')]")).click();
				TimeUnit.SECONDS.sleep(1);
			}
			reportPass("All Hospital are obtained Successfully");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

}

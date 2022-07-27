package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.ui.ExpectedConditions;

import base.Base;

public class TopCities extends Base {

	By diagnostics = By.xpath("//span[text()='Book Diagnostic Tests']");
	By city = By.xpath(
			"//ul[@class='u-br-rule u-marginb--std-half u-pointer u-padb--dbl o-flex o-flex__justify--between']");

	public void getCities() {

		logger = report.createTest("List Top Cities");

		// Select the diagnostics link
		try {
			findElement(diagnostics).click();
			reportPass("Diagnostics Link Clicked Successfully");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}

		// Prints the top cities
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(city));
			Screenshot("TopCities");
			System.out.println("*****************************************");
			System.out.println("            Top Cities are: ");
			System.out.println("*****************************************");
			List<WebElement> cities = driver.findElements(city);
			for (WebElement city : cities) {
				System.out.println(city.getText());
			}
			reportPass("All Top cities are obtained Successfully");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}

	}

}

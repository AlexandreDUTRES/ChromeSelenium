package main;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Main {
	
	public static PrintWriter writer = null;
	public static String filename = "result";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Lancement de l'automatisation Chrome ...");
		
		System.setProperty("webdriver.chrome.driver","chromedriver.exe");
		
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		WebDriver driver = new ChromeDriver(options);
		
		// EXERCICE 1
		
		driver.get("https://en.wikipedia.org/wiki/Stallion");
		
		System.out.println("Titre de la page : " + driver.getTitle());
		System.out.println("URL de la page : " + driver.getCurrentUrl());
		System.out.println("Code source de la page : " + driver.getPageSource());

		String title_xpath = "/html[@class='client-js ve-available']/body[@class='mediawiki ltr sitedir-ltr mw-hide-empty-elt ns-0 ns-subject mw-editable page-Étalon_cheval rootpage-Étalon_cheval skin-vector action-view']/div[@id='content']/div[@id='bodyContent']/div[@id='mw-content-text']/div[@class='mw-parser-output']/p[1]";

		if (!driver.findElements(By.xpath(title_xpath)).isEmpty()) {
			WebElement intro = driver.findElement(By.xpath(title_xpath));
			System.out.println("Intro de la page : " + intro.getText());
		}
		
		// EXERCICE 2
		
		driver.get("https://clickspeedtest.com/"); //http://clicker.arckade.fr/en
		 
		String button_xpath = "/html/body/div[@class='container']/div[@class='row align-items-center my-2']/div[@class='col-lg-8']/div/button[@id='clicker']";

		if (!driver.findElements(By.xpath(button_xpath)).isEmpty()) {
			WebElement button = driver.findElement(By.xpath(button_xpath));
			for(int i=0; i<1000; i++)
				button.click();
		}
		
		// EXERCICE 3
		
		driver.get("https://stackoverflow.com/questions?tab=Active");
		if (!driver.findElements(By.className("question-hyperlink")).isEmpty()) {						
			
			for(WebElement question : driver.findElements(By.className("question-hyperlink"))) {
				System.out.println("Question : " + question.getText());				
				
				((JavascriptExecutor) driver).executeScript("window.open()");
				
				ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
				String url = question.getAttribute("href");
				driver.switchTo().window(tabs.get(1));
			    driver.get(url);							
				
				String answer_xpath = "/html[@class='html__responsive']/body[@class='question-page unified-theme']/div[@class='container']/div[@id='content']/div/div[@class='inner-content clearfix']/div[@id='mainbar']/div[@id='answers']/div/div[@class='post-layout']/div[@class='answercell post-layout--right']/div[@class='post-text']";
				if (!driver.findElements(By.xpath(answer_xpath)).isEmpty()) {
					WebElement response = driver.findElement(By.xpath(answer_xpath));
					System.out.println("Réponse : " + response.getText());
					/* EXERCICE 4
					writeToFile(response.getText());
					 */
				}
				
				driver.close();
				driver.switchTo().window(tabs.get(0));			
				System.out.println("------------------------");
			}
		}				
	}
	
    public static void writeToFile(String line) {
		try {
			writer = new PrintWriter(new FileOutputStream(filename, true));
			writer.println(line);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}   
    }

}

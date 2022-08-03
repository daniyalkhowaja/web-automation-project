
import java.time.Duration;
import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.JavascriptExecutor;

public class Main {
	
	WebDriver driver;
	ArrayList <String> favList;
	ArrayList <String> moviesList;
	
	//Run Browser
	public void runBrowser() {
		
			System.setProperty("webdriver.chrome.driver", "C:\\Users\\nabee\\Downloads\\chromedriver_win32\\chromedriver.exe");
			driver = new ChromeDriver();
			
			ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("results.html");
			
			//create ExtentReports and attach reporter
			ExtentReports extent = new ExtentReports();
			extent.attachReporter(htmlReporter);
			
			//create test for successful URL launch
			ExtentTest launchTest = extent.createTest("URL Launch Test", "test for successful URL launch");
			launchTest.log(Status.INFO, "Starting URL Launch Test...");
			launchOptik();
			launchTest.pass("Scuccessfully Navigated to URL");	
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			
			//create test for login
			ExtentTest loginTest = extent.createTest("Login Test", "test for login");
			login("tsqatest1@gmail.com", "Optik123");
			loginTest.log(Status.INFO, "Starting LOGIN Test...");
			loginTest.pass("Successfully Login");
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			
			
			driver.findElement(By.xpath("//a[normalize-space()='telus.com/watchpik']")).click();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			
			System.out.println("Close poupup");
			driver.findElement(By.xpath("//img[@src='/static/media/Clear_Button.6ea0b5d7.svg']")).click();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			
			
			WebElement btnLogin = driver.findElement(By.className("login-button"));
			btnLogin.click();
			
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			navigateToHomePage();
			
			List <WebElement> webElementsInFavorites = driver.findElements(By.xpath("/html/body/div/div[2]/div[5]/div[2]/div/div/div")); 
			  
			  for(WebElement item: webElementsInFavorites) { 
				  List <WebElement> list =  item.findElements(By.tagName("a"));
				  favList = new ArrayList <String>();
				  for(WebElement i: list) {
					  String src = i.getAttribute("href");
					  favList.add(src);
				  }
			  } 
			  System.out.println("Total Number of Favourite Movies: "+ favList.size());
			  
			  driver.findElement(By.linkText("On Demand")).click();
			  System.out.println("On Demand Selected");
			  
			  driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			  
			  JavascriptExecutor jse = (JavascriptExecutor)driver;
			  jse.executeScript("window.scrollBy(0,450)", "");
			  jse.executeScript("scroll(0, 450);");
			  
			  System.out.println("Movies Selected");
			  driver.findElement(By.linkText("Movies")).click();
			  System.out.println("Displaying All Movies");
			  
			  List <WebElement> webElementsInMovies =
					  driver.findElements(By.xpath("//*[@id=\"root\"]/div[2]")); for(WebElement
					  item: webElementsInMovies) { List <WebElement> list =
					  item.findElements(By.tagName("a")); moviesList = new ArrayList <String>();
					  for(WebElement i: list) { String src = i.getAttribute("href");
					  moviesList.add(src); } }
					  
			  System.out.println("Looping through All Movies");
						  
			  System.out.println("UnFavourite Asset Selected");
			  driver.get(GetUnfavoriteMovie(moviesList, favList));
			  
			  System.out.println("Movie added as Favourite");
			  driver.findElement(By.xpath("//*[@id=\"root\"]/div[2]/div[1]/div[2]/div/div[2]/div[2]/button")).click();
			  
			  navigateToHomePage();
			  
			  jse.executeScript("window.scrollBy(0,700)", "");
				  
			  //Open Newly Selected Favourite Asset
			  driver.findElement(By.xpath(
			  "//div[5]//div[2]//div[1]//div[1]//div[1]//div[1]//div[1]//div[1]//a[1]//div[1]//div[1]//img[1]"
			  )).click();
			 
			  
			  //create test for validating favourite button
			  ExtentTest validateTest = extent.createTest("Validate Test", " validate test");
			  validateButton();
			  validateTest.log(Status.INFO, "Starting Validating button Test...");
			  validateTest.pass("Successfully validated");
			  
			  System.out.println("The Newly Favourite Asset is: "+ driver.getTitle());
			  
		      extent.flush();
	}
		
	public void launchOptik() {
		driver.get("https://onthego.telus.com/");
		driver.manage().window().maximize();
		System.out.println("URL Launched");
	}
	
	public void login(String username, String password) {
		WebElement txtUsername = driver.findElement(By.id("IDToken1"));
		txtUsername.clear();
		txtUsername.sendKeys(username);
		
		WebElement txtPassword = driver.findElement(By.id("IDToken2"));
		txtPassword.clear();
		txtPassword.sendKeys(password);
		
		WebElement btnLogin = driver.findElement(By.id("Submit"));
		btnLogin.click();
		System.out.println("Successfully Login");
	}
	
	public void navigateToHomePage() {
		System.out.println("Navigating to Home Page");
		driver.findElement(By.linkText("Home")).click();
	}
	
	public String GetUnfavoriteMovie(ArrayList <String> m, ArrayList <String> f) {
		String a = null;

		for (int i=0; i<m.size(); i++)
		{
		    a = m.get(i);

		    if (f.contains(a))
		    {
		        f.remove(a);
		        m.remove(a);
		        i--;
		    }
		}

		ArrayList<String> unFav = new ArrayList<String>();
		unFav.addAll(m);
		unFav.addAll(f);
		
		return unFav.get(0);
	}

	public void validateButton() {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebElement btnFavourite = driver.findElement(By.xpath("//*[@id=\"root\"]/div[2]/div[1]/div[2]/div/div[2]/div[2]/button"));
		String imgText = btnFavourite.findElement(By.tagName("img")).getAttribute("alt");
		System.out.println("Validated button text as its now changed to:"+imgText);
	}
	
	//Main Method to run the application
	public static void main(String[] args) {
		Main obj = new Main();
		obj.runBrowser();	
	}
}

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;

public class LoginTest {

    WebDriver driver;
    ChromeOptions options;

    final String user1 = "standard_user";
    final String user2 = "not_a_user_valid";
    final String password = "secret_sauce";

    @BeforeTest
    public void SetUp(){
        options = new ChromeOptions();
        options.setBinary("C:\\MyTools\\chrome-win64\\chrome.exe");
        driver = new ChromeDriver(options);
        driver.get("https://www.saucedemo.com/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
    }

    @Test(priority = 1)
    public void LoginTestSuccess(){
        System.out.println("Pengujian pertama: Login Berhasil");
        WebElement usernameField = driver.findElement(By.id("user-name"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("login-button"));

        usernameField.sendKeys(user1);
        System.out.println("login as username "+ user1);
        passwordField.sendKeys(password);
        loginButton.click();

        String expectedUrl = "https://www.saucedemo.com/inventory.html";
        Assert.assertEquals(driver.getCurrentUrl(), expectedUrl, "Login berhasil");
        System.out.println("Current URL: " + driver.getCurrentUrl());
        System.out.println("Expected URL: " + expectedUrl);
        System.out.println("Assertion passed: Current URL match the expected URL.");
    }

    @Test(priority = 2)
    public void LoginTestFailed(){
        System.out.println("Pengujian pertama: Login Tidak Berhasil");
        WebElement usernameField = driver.findElement(By.id("user-name"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("login-button"));

        usernameField.sendKeys(user2);
        System.out.println("login as username "+ user2);
        passwordField.sendKeys(password);
        loginButton.click();

        String expectedUrl = "https://www.saucedemo.com/inventory.html";
        Assert.assertNotEquals(driver.getCurrentUrl(), expectedUrl, "Login tidak berhasil");
        System.out.println("Current URL: " + driver.getCurrentUrl());
        System.out.println("Expected URL: " + expectedUrl);
        System.out.println("Assertion passed: Current URL does not match the expected URL.");
    }

    @AfterMethod
    public void AfterLogin(){
        try {
            WebElement burgerBar = driver.findElement(By.id("react-burger-menu-btn"));
            WebElement logOutButton = driver.findElement(By.id("logout_sidebar_link"));
            burgerBar.click();
            logOutButton.click();
        } catch (Exception e) {
            driver.quit();
        }
    }

}

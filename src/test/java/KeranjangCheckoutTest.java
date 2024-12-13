import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;

public class KeranjangCheckoutTest {
    WebDriver driver;
    ChromeOptions options;

    final String user = "standard_user";
    final String password = "secret_sauce";

    @BeforeTest
    public void SetUpTest(){
        options = new ChromeOptions();
        options.setBinary("C:\\MyTools\\chrome-win64\\chrome.exe");
        driver = new ChromeDriver(options);
        driver.get("https://www.saucedemo.com/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
    }

    @Test(priority = 1)
    public void LoginDahulu() throws InterruptedException {
        System.out.println("Pengujian pertama: Login Berhasil");
        WebElement usernameField = driver.findElement(By.id("user-name"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("login-button"));

        usernameField.sendKeys(user);
        System.out.println("login as username "+ user);
        Thread.sleep(1000);
        passwordField.sendKeys(password);
        Thread.sleep(1000);
        loginButton.click();
        Thread.sleep(5000);

        String expectedUrl = "https://www.saucedemo.com/inventory.html";
        Assert.assertEquals(driver.getCurrentUrl(), expectedUrl, "Login berhasil");
        System.out.println("Current URL: " + driver.getCurrentUrl());
        System.out.println("Expected URL: " + expectedUrl);
        System.out.println("Assertion passed: Current URL match the expected URL.");

        System.out.println("=====================================");
    }

    @Test(priority = 2)
    public void masukkanBarangKeKeranjang() throws InterruptedException {
        System.out.println("Pengujian kedua: berhasil menambahkan ke keranjang");
        // Menambahkan barang ke keranjang
        WebElement addToCart1 = driver.findElement(By.id("add-to-cart-sauce-labs-backpack")); // Sauce Labs Backpack
        WebElement addToCart2 = driver.findElement(By.id("add-to-cart-sauce-labs-fleece-jacket")); // Sauce Labs Fleece Jacket

        addToCart1.click();
        System.out.println("Sauce Labs Backpack Added");
        Thread.sleep(1000);
        addToCart2.click();
        System.out.println("Sauce Labs Fleece Jacket Added");
        Thread.sleep(1000);

        WebElement cartBadge = driver.findElement(By.className("shopping_cart_badge"));
        String itemCount = cartBadge.getText(); // Mendapatkan jumlah barang di keranjang
        Assert.assertEquals(itemCount, "2", "Jumlah barang di keranjang tidak sesuai");

        WebElement cartLink = driver.findElement(By.className("shopping_cart_link"));
        cartLink.click();

        try {
            WebElement itemKeranjang1 = driver.findElement(By.xpath("//div[@class='inventory_item_name' and text()='Sauce Labs Backpack']"));
            WebElement itemKeranjang2 = driver.findElement(By.xpath("//div[@class='inventory_item_name' and text()='Sauce Labs Fleece Jacket']"));

            Assert.assertTrue(itemKeranjang1.isDisplayed(), "Sauce Labs Backpack tidak ada di keranjang");
            Assert.assertTrue(itemKeranjang2.isDisplayed(), "Sauce Labs Fleece Jacket tidak ada di keranjang");

            System.out.println("Barang yang dipilih sesuai dengan isi keranjang.");
        } catch (Exception e) {
            System.out.println("Barang tidak sesuai dengan yang dipilih. Error: " + e.getMessage());
            Assert.fail("Barang yang dipilih tidak sesuai dengan isi keranjang.");
        }
        Thread.sleep(1000);
        System.out.println("=====================================");
    }

    @Test(priority = 3)
    public void checkOutDongBarangnya() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement checkoutShopping = wait.until(ExpectedConditions.elementToBeClickable(By.id("checkout")));
        checkoutShopping.click();
        System.out.println("Open checkout");

        WebElement isiNamaAwal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("first-name")));
        WebElement isiNamaAkhir = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("last-name")));
        WebElement isiKodePos = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("postal-code")));
        WebElement continueButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("continue")));

        isiNamaAwal.click();
        isiNamaAwal.sendKeys("Taor");
        Thread.sleep(1000);

        isiNamaAkhir.click();
        isiNamaAkhir.sendKeys("Baga");
        Thread.sleep(1000);

        isiKodePos.click();
        isiKodePos.sendKeys("19");
        Thread.sleep(1000);

        continueButton.click();
        Thread.sleep(1000);

        wait.until(ExpectedConditions.urlContains("checkout-step-two.html"));
        System.out.println("Checkout berhasil diproses!");
        WebElement finishButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("finish")));
        finishButton.click();
        System.out.println("Checkout berhasil diproses!");
    }


    @AfterMethod
    public void AfterMethodTest(){
        System.out.println("TEST telah berhasil dijalankan");
    }

}

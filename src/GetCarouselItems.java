import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;
import java.util.List;

public class GetCarouselItems{
    static WebDriver driver;
    Actions act = new Actions(driver);
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Hello world!");
        driver = new ChromeDriver();
        driver.get("https://www.noon.com/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
        String recommendedForYou = "//h3[text()='Recommended for you']";
        String noonBrand = "//h3[text()='noon brands deals']";
        String electronicDeals = "//h3[text()='Trending deals in electronics']";
        String clearanceDeals = "//h3[text()='Clearance deals']";
        GetCarouselItems obj = new GetCarouselItems();
        System.out.println("======1st carousel======");
        obj.getCarouselElementsList(recommendedForYou);
        System.out.println("======2nd carousel======");
        obj.getCarouselElementsList(noonBrand);
        System.out.println("======3rd carousel======");
        obj.getCarouselElementsList(electronicDeals);
        System.out.println("======4th carousel======");
        obj.getCarouselElementsList(clearanceDeals);

        driver.quit();
    }


    public void getCarouselElementsList(String locator) throws InterruptedException {

        try {
            scrollToSection(locator);
            printElementSectionList(locator);

        } catch (Exception e) {
            act.sendKeys(Keys.PAGE_DOWN).perform();
            Thread.sleep(1000);
        }
    }

    public void scrollToSection(String locator) throws InterruptedException {
        do {
            try {
                WebElement carousel = driver.findElement(By.xpath(locator));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", carousel);
                break;
            } catch (NoSuchElementException e) {
                act.sendKeys(Keys.PAGE_DOWN).perform();
                Thread.sleep(1000);
            }
        }
        while (true);
    }

    public void printElementSectionList(String locator) {

        String currentCarousel = locator + "//parent::div//following-sibling::div//div[contains(@class,'swiper-button-next')]";
        String carouselListElements = locator + "//parent::div//following-sibling::div//div[@data-qa='product-name'] ";
        List<WebElement> listName = driver.findElements(By.xpath(carouselListElements));
        if (driver.findElement(By.xpath(currentCarousel)).isDisplayed()) {

            int i = 1;
            for (WebElement individualName : listName) {
                System.out.println(i + "." + individualName.getAttribute("title"));
                i++;
            }
            int arrowClick = listName.size();
            int z = 0;
            do {
                driver.findElement(By.xpath(currentCarousel)).click();
                z++;
            } while (z < arrowClick / 6);
        }


    }
}
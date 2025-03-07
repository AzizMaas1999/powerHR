package tn.esprit.powerHR.services.PaiePointage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;
import java.util.List;

public class ServiceScraping {

    public ServiceScraping() {
    }

        public List<String> automatePaieTunisie(String nbrenfants, String salaireNet) {

            System.setProperty("webdriver.chrome.driver", "src/main/resources/chrome/chromedriver.exe");

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");
            options.addArguments("--ignore-certificate-errors");
            options.addArguments("--silent");
            options.addArguments("--mute-audio");

            WebDriver driver = new ChromeDriver(options);

            try {

                driver.get("https://paie-tunisie.com/");


                WebElement checkbox = driver.findElement(By.xpath("/html/body/form/div[3]/div[1]/div/div[2]/div/span/div[1]/div/span/div/div[2]/span/div[2]/div/span/div[1]/table/tbody/tr[1]/td[3]/span/input"));
                if (!checkbox.isSelected()) {
                    checkbox.click();
                }
                WebElement body = driver.findElement(By.tagName("body"));

                WebElement text = driver.findElement(By.xpath("/html/body/form/div[3]/div[1]/div/div[2]/div/span/div[1]/div/span/div/div[2]/span/div[2]/div/span/div[1]/table/tbody/tr[2]/td[3]/input"));
                text.sendKeys(nbrenfants);
                body.click();
                Thread.sleep(1000);
                WebElement text2 = driver.findElement(By.xpath("/html/body/form/div[3]/div[1]/div/div[2]/div/span/div[1]/div/span/div/div[2]/span/div[2]/div/span/div[1]/table/tbody/tr[4]/td[3]/input"));
                text2.sendKeys(salaireNet);
                body.click();
                System.out.println("All fields set");
                Thread.sleep(1000);

//                WebElement button = driver.findElement(By.xpath("//*[@id=\"MainContent_ctl00_ctl02_ctl06_ctl04\"]/div[1]/table/tbody/tr[7]/td"));
//                button.click();
//                System.out.println("Button clicked");

//                Thread.sleep(3000);

                String ret = "";
                String salBrut = "";

                ret = driver.findElement(By.xpath("/html/body/form/div[3]/div[1]/div/div[2]/div/span/div[1]/div/span/div/div[2]/span/div[2]/div/span/div[2]/table/tbody/tr[2]/td[2]")).getText();
                salBrut = driver.findElement(By.xpath("/html/body/form/div[3]/div[1]/div/div[2]/div/span/div[1]/div/span/div/div[2]/span/div[2]/div/span/div[2]/table/tbody/tr[1]/td[2]")).getText();
                List<String> list = new ArrayList<>();
                list.add(ret);
                list.add(salBrut);
                System.out.println("Retenu: " + ret);
                System.out.println("Salaire Net: " + salBrut);

//                Thread.sleep(3000);
                return list;

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            finally {

                driver.quit();
            }
            return null;
        }
    }

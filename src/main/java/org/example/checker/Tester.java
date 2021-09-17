package org.example.checker;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Tester {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        Properties properties = new Properties();
        try{
            properties.load(new FileReader("src/main/java/org/example/checker/config.properties"));
            driver.get(properties.getProperty("url"));
            driver.findElement(By.id("details-button")).click();
            driver.findElement(By.id("proceed-link")).click();
            User user = new User(driver, properties.getProperty("password"));
            user.checkData();
        }catch (IOException ex){
            System.err.println("properties file faulty");
        }finally{
            driver.quit();
        }
    }
}

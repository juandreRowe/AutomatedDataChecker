package org.example.checker;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.time.Duration;
import java.util.Calendar;

public class User {
    private String password;
    private final WebDriver driver;

    public User(WebDriver driver, String password){
        this.driver = driver;
        driver.manage().window().setSize(new Dimension(1840, 1200));
        this.setPassword(password);
    }

    public void setPassword(String password){
        this.password = password;
    }



    private void login(){
        driver.findElement(By.id("login_password")).sendKeys(password + Keys.ENTER);
    }

    private void sendRequest(){
        new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions.elementToBeClickable(By.id("header_sms_info"))).click();
        new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions.elementToBeClickable(By.id("menu_ussd"))).click();
        //general_control span
        new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions.elementToBeClickable(By.cssSelector("#general_control span"))).click();
        driver.findElement(By.id("ussd_general_select")).sendKeys("*100#");
        //send sms
        //driver.findElement(By.id("general_button_0")).click();
    }

    private void retrieveData(){
        //menu_sms
        new WebDriverWait(driver, Duration.ofSeconds(120)).until(ExpectedConditions.elementToBeClickable(By.id("menu_smstool"))).click();
        //mCSB_1_container
        WebElement messages = new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions.elementToBeClickable(By.id("mCSB_1_container")));
        WebElement lastMessage = messages.findElement(By.cssSelector(".border_bottom.pull-left.pointer"));
        //select last one
        System.out.println(lastMessage != null);
        if(lastMessage != null){
            lastMessage.click();
        }

        this.sleepFive(3);
        this.takeScreenshot();
    }

    private void takeScreenshot(){
        WebElement container = new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions.elementToBeClickable(By.id("mCSB_3")));
        File file = container.getScreenshotAs(OutputType.FILE);
        OutputStream outStream = null;
        FileInputStream inputStream = null;
        try{
            outStream = new FileOutputStream("src/main/resources/images/" + Calendar.getInstance().getTime() + ".png");
            inputStream = new FileInputStream(file);
            int bytesRead = -1;
            byte[] array = new byte[1024];
            while((bytesRead = inputStream.read(array)) != -1){
                outStream.write(array, 0, bytesRead);
            }

            System.out.println("file saved");
        }catch(IOException ex){
            System.err.println("file faulty");
        }finally{
            this.closeCloseable(outStream);
            this.closeCloseable(inputStream);
        }
    }

    private void closeCloseable(AutoCloseable closeable){
        if(closeable != null){
            try{
                closeable.close();
            }catch (Exception ex){
                System.err.println("closeable faulty");
            }
        }
    }

    public void checkData(){
        login();
        this.sleepFive(5);
        sendRequest();
        this.sleepFive(20);
        retrieveData();
    }

    private void sleepFive(long seconds){
        try{
            Thread.sleep(seconds * 1000);
        }catch(InterruptedException ex){
            System.err.println("thread broken");
        }
    }
}

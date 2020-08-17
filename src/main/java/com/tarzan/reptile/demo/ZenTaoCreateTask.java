package com.tarzan.reptile.demo;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.util.ClassUtils;

import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * 禅达快速创建任务
 * Created by tarzan liu on 2018/2/2.
 */
public class ZenTaoCreateTask {
    private static String webDriver = "webdriver.chrome.driver";
    private static String webDriverPath = ClassUtils.getDefaultClassLoader().getResource("chromedriver/chromedriver.exe").getPath();
    private static String targetPath = "http://172.16.10.26:12345/zentao/user-login-L3plbnRhby9teS5odG1s.html";

    private static WebDriver driver = null;

    private static String username = null;
    private static String password = null;

    //项目名称（必须和禅道项目名称一致）
    private static String projectName = "CIM6D-智慧施工-2020";

    //任务名称
    private static String taskName = "精装修-质量目标分解添加、修改、详情接口修改";

    //内容描述
    private static String content = "";

    //任务类型 （必须和禅道任务类型一致）
    private static String taskType = "开发";

    //任务指派人（L首个汉字的拼音首字母大写）
    private static String  people = "L:刘亚鹏";

    //预估时长
    private static String  estimate = "6";


    static {
        ResourceBundle rb = ResourceBundle.getBundle("zenTao");
        username = rb.getString("zenTao.username");
        password = rb.getString("zenTao.password");
    }

    public static void main(String[] args) {
        start();
    }


    public static void start() {
        System.setProperty(webDriver, webDriverPath);
            try {
                if (Objects.isNull(driver)){
                    driver = new ChromeDriver();
                    userLogin(driver);
                }
                work(driver);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (Objects.nonNull(driver)) {
                  //  driver.close();
                }
            }

    }



    /**
     * 开始工作
     */
    private static void work(WebDriver driver) throws Exception {

        WebElement projectButtonWebElement = driver.findElement(By.xpath("//li[@data-id='project']/a"));
        projectButtonWebElement.click();

        WebElement taskButtonWebElement = driver.findElement(By.xpath("//li[@data-id='task']/a"));
        taskButtonWebElement.click();

        WebElement createWebElement = driver.findElement(By.xpath("//div[@id='mainMenu']/div[@class='btn-toolbar pull-right']/a[@class='btn btn-primary']"));
        createWebElement.click();

        WebElement projectWebElement = driver.findElement(By.xpath("//*[@id='project_chosen']/a"));
        projectWebElement.click();
        Thread.sleep(200);//等待0.2秒
        projectWebElement.findElement(By.xpath("//li[@title='"+projectName+"']")).click();

        WebElement typeWebElement = driver.findElement(By.xpath("//*[@id='type_chosen']/a"));
        typeWebElement.click();
        Thread.sleep(200);//等待0.2秒
        typeWebElement.findElement(By.xpath("//li[@title='"+taskType+"']")).click();

        WebElement peopleWebElement = driver.findElement(By.xpath("//*[@id='assignedTo_chosen']/a"));
        peopleWebElement.click();
        Thread.sleep(200);//等待0.2秒
        peopleWebElement.findElement(By.xpath("//li[@title='"+people+"']")).click();

        WebElement taskWebElement = driver.findElement(By.name("name"));
        taskWebElement.sendKeys(taskName);

        WebElement estimateWebElement = driver.findElement(By.name("estimate"));
        estimateWebElement.sendKeys(estimate);

        WebElement contentWebElement = driver.findElement(By.xpath("//div[@class='ke-edit']/iframe"));
        contentWebElement.sendKeys(content);

        WebElement submitWebElement = driver.findElement(By.id("submit"));
       submitWebElement.click();

        Thread.sleep(2000);//等待0.5秒
        List<WebElement>  taskList= driver.findElements(By.xpath("//table[@id='taskList']/tbody/tr"));
        String id=taskList.get(0).getAttribute("data-id");
        String name=taskList.get(0).findElements(By.tagName("td")).get(2).getAttribute("title");
        System.out.println("task#"+id+"  "+name);
    }




    /**
     * 获取token
     */
    private static String getToken(WebDriver driver) throws Exception {
        if (StringUtils.isBlank(driver.getCurrentUrl())) {
            throw new Exception("获取token链接有误");
        }
        String token = driver.getCurrentUrl().split("token=")[1];
        if (StringUtils.isBlank(token)) {
            throw new Exception("token错误");
        }
        return token;
    }

    /**
     * 登录模块
     */
    private static void userLogin(WebDriver driver) throws Exception {
        driver.get(targetPath);

        WebElement usernameWebElement = driver.findElement(By.name("account"));
        usernameWebElement.clear();
        usernameWebElement.sendKeys(username);

        WebElement passwordWebElement = driver.findElement(By.name("password"));
        passwordWebElement.clear();
        passwordWebElement.sendKeys(password);

        WebElement helpWebElement = driver.findElement(By.id("keepLoginon"));
        helpWebElement.click();

        WebElement btnWebElement = driver.findElement(By.id("submit"));
        btnWebElement.click();

        Thread.sleep(200);

    }
}

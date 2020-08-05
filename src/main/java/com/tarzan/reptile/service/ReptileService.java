package com.tarzan.reptile.service;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.tarzan.reptile.domain.other.InfoResult;
import com.tarzan.reptile.domain.other.PlatformResult;
import com.tarzan.reptile.domain.entity.PlatformEntity;
import com.tarzan.reptile.mapper.InfoDao;
import com.tarzan.reptile.mapper.PlatformDao;
import com.tarzan.reptile.utils.HttpUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;

import java.util.*;

/**
 * Created by tarzan liu on 2018/2/2.
 */
@Service
public class ReptileService {

    private static String webDriver = "webdriver.chrome.driver";
    private static String webDriverPath = ClassUtils.getDefaultClassLoader().getResource("chromedriver/chromedriver.exe").getPath();
    private static String targetPath = "https://mp.weixin.qq.com";
    private static String searchPath = "https://mp.weixin.qq.com/cgi-bin/searchbiz";
    private static String appmsgPath = "https://mp.weixin.qq.com/cgi-bin/appmsg";
    private static Random random = new Random(1);
    private static Gson gson = new Gson();

    private static WebDriver driver = null;


    private static List<String> nameList = new ArrayList<>();   // 要爬的公众号名称(准确名称)

    private static String username = null;
    private static String password = null;

    private static String token = null;
    private static List<PlatformEntity> platformList= null;

    @Autowired
    private PlatformDao platformDao;

    @Autowired
    private InfoDao infoDao;


    static {
        ResourceBundle rb = ResourceBundle.getBundle("reptile");
        username = rb.getString("reptile.username");
        password = rb.getString("reptile.password");
        nameList.add("洛阳旅游");
        nameList.add("洛阳交警");
        nameList.add("洛阳消防");
        nameList.add("FM927洛阳交通广播");
        nameList.add("考古洛阳");
        nameList.add("绿色洛阳");

    }


    public  void crawling() {
        System.setProperty(webDriver, webDriverPath);
        try {
            if (Objects.isNull(driver)){
                driver = new ChromeDriver();
                weixinLogin(driver);
                token=getToken(driver);
            }
            if (CollectionUtils.isEmpty(platformList)){
                platformList= getPlatform(driver, nameList);
            }
            if (CollectionUtils.isNotEmpty(platformList)) {
                platformDao.insertList(platformList);
                for (PlatformEntity platform : platformList) {
                    InfoResult infoResult = getInfoResult(driver, token, platform.getFakeId(), 0, 5);
                    if (CollectionUtils.isNotEmpty(infoResult.getAppMsgList())) {
                        infoResult.getAppMsgList().forEach(e -> {
                            System.out.println(e);
                        });
                        infoDao.insertList(infoResult.getAppMsgList());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (Objects.nonNull(driver)) {
                //    driver.close();
            }
        }
    }

    /**
     * 获取公众号某一页数据
     */
    private static InfoResult getInfoResult(WebDriver driver, String token, String fakeId,
                                            Integer begin, Integer count) throws Exception {
        Map<String, String> queryInfoParams = new HashMap<>();
        queryInfoParams.put("token", token);
        queryInfoParams.put("lang", "zh_CN");
        queryInfoParams.put("f", "json");
        queryInfoParams.put("ajax", "1");
        queryInfoParams.put("random", random.nextDouble() + "");
        queryInfoParams.put("action", "list_ex");
        queryInfoParams.put("query", "");
        queryInfoParams.put("type", "9");
        queryInfoParams.put("fakeid", fakeId);

        queryInfoParams.put("begin", begin + "");
        queryInfoParams.put("count", count + "");

        String appmsgParamsPath = HttpUtils.setParams(appmsgPath, queryInfoParams);
        driver.get(appmsgParamsPath);
        Document infoDocument = Jsoup.parse(driver.getPageSource());
        Elements infoList = infoDocument.select("pre");
        if (Objects.isNull(infoList)) {
            throw new Exception("获取公众号文章错误");
        }
        Thread.sleep(15000);
        return gson.fromJson(infoList.text(), InfoResult.class);
    }

    /**
     * 获取公众号信息
     */
    private static List<PlatformEntity> getPlatform(WebDriver driver, List<String> nameList) throws Exception {
        List<PlatformEntity> list = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(nameList)) {
            nameList.forEach(e -> {
                try {
                    PlatformEntity platform = getPlatform(driver,token, e);
                    list.add(platform);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });

        }
        return list;
    }

    /**
     * 获取公众号信息
     */
    private static PlatformEntity getPlatform(WebDriver driver,String token,String name) throws Exception {
        Map<String, String> searchNameParams = new HashMap<>();
        searchNameParams.put("action", "search_biz");
        searchNameParams.put("begin", "0");
        searchNameParams.put("count", "5");
        searchNameParams.put("query", name);
        searchNameParams.put("token",token);
        searchNameParams.put("lang", "zh_CN");
        searchNameParams.put("f", "json");
        searchNameParams.put("ajax", "1");
        //searchNameParams.put("random", random.nextDouble() + "");

       String searchParamsPath = HttpUtils.setParams(searchPath, searchNameParams);
        driver.get(searchParamsPath);

        Document preDocument = Jsoup.parse(driver.getPageSource());
        Elements preList = preDocument.select("pre");
        if (Objects.isNull(preList)) {
            throw new Exception("获取公众号错误");
        }
        PlatformResult result = gson.fromJson(preList.text(), PlatformResult.class);

        PlatformEntity platform = null;
        for (int index = 0; index < result.getList().size(); index++) {
            PlatformEntity item = result.getList().get(index);
            if (name.equals(item.getNickname())) {
                platform = item;
            }
        }
        return platform;
    }

    /**
     * 获取token
     */
    private static String getToken(WebDriver driver) throws Exception {
        String current = driver.getCurrentUrl();
        if (StringUtils.isBlank(current)) {
            throw new Exception("获取token链接有误");
        }
        String token = current.split("token=")[1];
        if (StringUtils.isBlank(token)) {
            throw new Exception("token错误");
        }
        return token;
    }

    /**
     * 登录模块
     */
    private static void weixinLogin(WebDriver driver) throws Exception {
        driver.get(targetPath);

        // WebDriverWait waiting = new WebDriverWait(driver, 15, 1000);
        // ExpectedCondition<WebElement> nameEC = ExpectedConditions.visibilityOfElementLocated(By.className("login__type__container__select-type"));
        // WebElement typeWebElement = waiting.until(nameEC);
        // WebElement typeWebElement = driver.findElement(By.className("login__type__container__select-type"));
        WebElement typeWebElement = driver.findElement(By.xpath("//*[@class='login__type__container login__type__container__scan']/a"));
        typeWebElement.click();

        WebElement usernameWebElement = driver.findElement(By.name("account"));
        usernameWebElement.clear();
        usernameWebElement.sendKeys(username);

        WebElement passwordWebElement = driver.findElement(By.name("password"));
        passwordWebElement.clear();
        passwordWebElement.sendKeys(password);

        WebElement helpWebElement = driver.findElement(By.className("icon_checkbox"));
        helpWebElement.click();

        WebElement btnWebElement = driver.findElement(By.className("btn_login"));
        btnWebElement.click();

        System.out.println("请用手机微信扫码二维码登录公众号");
        Thread.sleep(15000);
    }
}

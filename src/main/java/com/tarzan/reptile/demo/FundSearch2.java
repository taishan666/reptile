package com.tarzan.reptile.demo;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.util.List;

/**
 * @author Lenovo
 */
public class FundSearch2 {

    static String api = "https://fundapi.eastmoney.com/fundtradenew.aspx?sc=1n&st=desc&cp=&ct=&cd=&ms=&fr=&plevel=&fst=&ftype=&fr1=&fl=0&isab=1";
    private static String webDriver = "webdriver.chrome.driver";
    private static String webDriverPath ="E:\\work_space\\reptile\\src\\main\\resources\\chromedriver\\chromedriver.exe";

    private static final List<String> TYPE_LIST = Lists.newArrayList();

    static {
        TYPE_LIST.add("&ft=gp"); //股票型
        TYPE_LIST.add("&ft=hh");//混合型
        TYPE_LIST.add("&ft=zq"); //债券型
        TYPE_LIST.add("&ft=zs"); //指数型
        TYPE_LIST.add("&ft=qdii"); //QDII
        TYPE_LIST.add("&ft=fof"); //FOF
    }

    public static void main(String[] args) {
       // List<Fund> funds = getAllFunds();
      //  System.out.println("共扫描基金" + funds.size() + "只");
        getAverage("002190");

    }

    public static List<Fund> getAverage(String fundCode){
        String body = HttpUtil.get("http://api.fund.eastmoney.com/f10/lsjz?callback=jQuery18307452521207439875_1675222024483&fundCode=002190&pageIndex=1&pageSize=20&startDate=&endDate=&_="+System.currentTimeMillis());
        System.out.println(body);
        return null;
    }

    public static List<Fund> getAllFunds() {
        List<Fund> dataList = Lists.newArrayList();
        TYPE_LIST.forEach(type -> dataList.addAll(getTypeFunds(api, type)));
        return dataList;
    }

    public static List<Fund> getTypeFunds(String API, String type) {
        List<Fund> dataList = Lists.newArrayList();
        String body = HttpUtil.get(API + type + "&pi=1&pn=1000");
        Integer pages = getPages(body);
        dataList.addAll(parseFundData(body));
        for (int i = 0; i < pages; i++) {
            body = HttpUtil.get(API + type + "&pi=" + (i + 2) + "&pn=1000");
            dataList.addAll(parseFundData(body));
        }
        return dataList;
    }

    public static List<Fund> parseFundData(String text) {
        List<Fund> funds = Lists.newArrayList();
        text = text.replace("var rankData = ", "");
        text = text.replace(";", "");
        JSONObject json = JSONObject.parseObject(text);
        JSONArray array = json.getJSONArray("datas");
        for (int i = 0; i < array.size(); i++) {
            Fund fund = conversion(array.getString(i));
            funds.add(fund);
        }
        return funds;
    }

    public static Integer getPages(String text) {
        text = text.replace("var rankData = ", "");
        text = text.replace(";", "");
        JSONObject json = JSONObject.parseObject(text);
        return json.getInteger("allPages");
    }

    public static Fund conversion(String text) {
        String[] args = text.split("\\|");
        Fund fund = new Fund();
        fund.setRowData(text);
        fund.setCode(args[0]);
        fund.setName(args[1]);
        fund.setType(args[2]);
        fund.setDate(args[3]);
        fund.setNetValue(strToDouble(args[4]));
        fund.setDayRise(strToDouble(args[5]));
        fund.setWeekRise(strToDouble(args[6]));
        fund.setMonthRise(strToDouble(args[7]));
        fund.setThreeMonthsRise(strToDouble(args[8]));
        fund.setSixMonthsRise(strToDouble(args[9]));
        fund.setOneYearRise(strToDouble(args[10]));
        fund.setTwoYearsRise(strToDouble(args[11]));
        fund.setThreeYearsRise(strToDouble(args[12]));
        fund.setCurYearRise(strToDouble(args[13]));
        fund.setHistoryRise(strToDouble(args[14]));
        fund.setPoundage(strToDouble(args[18]));
        fund.setDtStatus(Integer.valueOf(args[22]));
        fund.setDealStatus(Integer.valueOf(args[23]));
        return fund;
    }

    public static Double strToDouble(String str) {
        str = str.replace(",", "");
        return StringUtils.isEmpty(str) ? 0 : Double.parseDouble(str);
    }

    @Data
    static
    class Fund {
        /**
         * 基金代码
         **/
        private String code;
        /**
         * 基金名称
         **/
        private String name;
        /**
         * 基金类型
         **/
        private String type;
        /**
         * 净值时间
         **/
        private String date;
        /**
         * 基金净值
         **/
        private Double netValue;
        /**
         * 日增长率
         **/
        private Double dayRise;
        /**
         * 近1周
         **/
        private Double weekRise;
        /**
         * 近1个月
         **/
        private Double monthRise;
        /**
         * 近3个月
         **/
        private Double threeMonthsRise;
        /**
         * 近6个月
         **/
        private Double sixMonthsRise;
        /**
         * 近1年
         **/
        private Double oneYearRise;
        /**
         * 近2年
         **/
        private Double twoYearsRise;
        /**
         * 近3年
         **/
        private Double threeYearsRise;
        /**
         * 今年以来
         **/
        private Double curYearRise;
        /**
         * 成立以来
         **/
        private Double historyRise;
        /**
         * 手续费
         **/
        private Double poundage;
        /**
         * 是否可以定投0no1ok
         **/
        private Integer dtStatus;
        /**
         * 交易状态
         **/
        private Integer dealStatus;
        /**
         * 行文本数据
         **/
        private String rowData;

    }


}

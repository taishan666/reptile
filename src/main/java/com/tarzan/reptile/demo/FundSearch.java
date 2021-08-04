package com.tarzan.reptile.demo;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FundSearch {

    static String api = "https://fundapi.eastmoney.com/fundtradenew.aspx?sc=1n&st=desc&cp=&ct=&cd=&ms=&fr=&plevel=&fst=&ftype=&fr1=&fl=0&isab=1";

    private static final List<String> typeList = Lists.newArrayList();

    static {
        typeList.add("&ft=gp"); //股票型
        typeList.add("&ft=hh");//混合型
        typeList.add("&ft=zq"); //债券型
        typeList.add("&ft=zs"); //指数型
        typeList.add("&ft=qdii"); //QDII
        typeList.add("&ft=fof"); //FOF
    }

    public static void main(String[] args) {
        System.out.println(Math.pow(1.242,17));
        List<Fund> funds = getAllFunds();
        System.out.println("共扫描基金" + funds.size() + "只");
        Fund param=new Fund();
        param.setWeekRise(1.0);
        param.setMonthRise(6.0);
        param.setThreeMonthsRise(20.0);
        param.setSixMonthsRise(42.0);
       param.setOneYearRise(100.0);
        param.setTwoYearsRise(200.0);
       // param.setThreeYearsRise(300.0);
        funds =filterResult(funds,param);
        funds.forEach(e -> System.out.println(e.getRowData()));
    }

    public static List<Fund> filterResult(List<Fund> funds,Fund param){
        StringBuffer buffer=new StringBuffer("搜索到");
        if(param!=null){
            if(Objects.nonNull(param.getDealStatus())){
                funds = funds.stream().filter(e -> e.getDealStatus()==param.getDealStatus()).collect(Collectors.toList());
            }
            if(Objects.nonNull(param.getWeekRise())){
                buffer.append("近1周涨幅大于"+param.getWeekRise()+"%,");
                funds = funds.stream().filter(e -> e.getWeekRise()>=param.getWeekRise()).collect(Collectors.toList());
            }
            if(Objects.nonNull(param.getMonthRise())){
                buffer.append("近1月涨幅大于"+param.getMonthRise()+"%,");
                funds = funds.stream().filter(e -> e.getMonthRise()>=param.getMonthRise()).collect(Collectors.toList());
            }
            if(Objects.nonNull(param.getThreeMonthsRise())){
                buffer.append("近3月涨幅大于"+param.getThreeMonthsRise()+"%,");
                funds = funds.stream().filter(e -> e.getThreeMonthsRise()>=param.getThreeMonthsRise()).collect(Collectors.toList());
            }
            if(Objects.nonNull(param.getSixMonthsRise())){
                buffer.append("近6月涨幅大于"+param.getSixMonthsRise()+"%,");
                funds = funds.stream().filter(e -> e.getSixMonthsRise()>=param.getSixMonthsRise()).collect(Collectors.toList());
            }
            if(Objects.nonNull(param.getOneYearRise())){
                buffer.append("近1年涨幅大于"+param.getOneYearRise()+"%,");
                funds = funds.stream().filter(e -> e.getOneYearRise()>=param.getOneYearRise()).collect(Collectors.toList());
            }
            if(Objects.nonNull(param.getTwoYearsRise())){
                buffer.append("近2年涨幅大于"+param.getTwoYearsRise()+"%,");
                funds = funds.stream().filter(e -> e.getTwoYearsRise()>=param.getTwoYearsRise()).collect(Collectors.toList());
            }
            if(Objects.nonNull(param.getThreeYearsRise())){
                buffer.append("近3年涨幅大于"+param.getThreeYearsRise()+"%,");
                funds = funds.stream().filter(e -> e.getThreeYearsRise()>=param.getThreeYearsRise()).collect(Collectors.toList());
            }
        }
        buffer.append("\n符合条件的基金共" + funds.size() + "只");
        System.out.println(buffer);
        return funds;
 }

    public static List<Fund> getAllFunds() {
        List<Fund> dataList = Lists.newArrayList();
        typeList.forEach(type -> {
            dataList.addAll(getTypeFunds(api, type));
        });
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
        Integer pages = json.getInteger("allPages");
        return pages;
    }

    public static Fund conversion(String text) {
        String[] args = text.split("\\|");
        Fund fund = new Fund();
        fund.setRowData(text);
        fund.setCode(args[0]);
        fund.setName(args[1]);
        fund.setType(args[2]);
        fund.setDate(args[3]);
        fund.setNetValue(StrToDouble(args[4]));
        fund.setDayRise(StrToDouble(args[5]));
        fund.setWeekRise(StrToDouble(args[6]));
        fund.setMonthRise(StrToDouble(args[7]));
        fund.setThreeMonthsRise(StrToDouble(args[8]));
        fund.setSixMonthsRise(StrToDouble(args[9]));
        fund.setOneYearRise(StrToDouble(args[10]));
        fund.setTwoYearsRise(StrToDouble(args[11]));
        fund.setThreeYearsRise(StrToDouble(args[12]));
        fund.setCurYearRise(StrToDouble(args[13]));
        fund.setHistoryRise(StrToDouble(args[14]));
        fund.setPoundage(StrToDouble(args[18]));
        fund.setDtStatus(Integer.valueOf(args[22]));
        fund.setDealStatus(Integer.valueOf(args[23]));
        return fund;
    }

    public static Double StrToDouble(String str) {
        str = str.replace(",", "");
        return StringUtils.isEmpty(str) ? 0 : Double.valueOf(str);
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

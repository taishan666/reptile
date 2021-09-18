package com.tarzan.reptile.demo;


import cn.hutool.core.util.RandomUtil;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * @Author tarzan
 * @Date 2021/4/1 14:43
 * @Description 模拟双色球，随机生成若干住号码
 */
public class DoubleColorBall {


    /**
     * 主方法
     * */
    public static void main(String[] args) {
        getDoubleColorBallNumber(5);
    }

    /**
     * 获取多注双色球号码
     * */
    public static void getDoubleColorBallNumber(int num){
        System.out.println("随机生成"+num+"注双色球号码为：");
        String resultNumber="";
        for (int i = 0; i < num; i++) {
            System.out.println("【"+(i+1)+"】 "+resultNumber+getDoubleColorBallNumber());
        }

    }

    /**
     * 获取单注双色球号码
     * */
    public static String getDoubleColorBallNumber(){
        String resultNumber="";
        for (int i = 0; i < 6; i++) {
            String ballNumber= RandomUtil.randomEle(getRedBalls())+"\t";
            resultNumber=resultNumber+ballNumber;
        }
        return resultNumber+RandomUtil.randomEle(getBlueBalls());
    }

    /**
     * 获取红球球号集合
     * */
    public static List<String> getRedBalls(){
        return getBalls(33);
    }

    /**
     * 获取蓝球球号集合
     * */
    public static List<String> getBlueBalls(){
        return getBalls(16);
    }

    /**
     * 获取球号集合
     * */
    public static List<String> getBalls(int num){
        List<String> redBalls= Lists.newArrayList();
        for (int i = 1; i <=num; i++) {
            int length=String.valueOf(num).length();
            String str = String.format("%0"+length+"d",i);
            redBalls.add(str);
        }
        return redBalls;
    }
}

package com.tarzan.reptile.demo;

import cn.hutool.core.util.RandomUtil;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MockDraw {

    public static void main(String[] args) {
        List<String> result=new ArrayList<>(10000);
        for (int i = 0; i < 10000; i++) {
            result.add(raffle());
        }
        Map<Object, Long> groupMap=result.stream().collect(Collectors.groupingBy(e->e,Collectors.counting()));
        System.out.println(groupMap);

    }

    private static String  raffle(){
        //三个门的编号1，2，3
        List<Integer> doors= Lists.newArrayList(1,2,3);
        //奖品选项
        List<String> prizes= Lists.newArrayList("山羊","汽车","山羊");
        //把奖品随机放到对应的门后面
        String option1=RandomUtil.randomEle(prizes);
        prizes.remove(option1);
        String option2=RandomUtil.randomEle(prizes);
        prizes.remove(option2);
        String option3=RandomUtil.randomEle(prizes);
        prizes.remove(option3);
        Map<Integer,String> map=new HashMap<>(5);
        map.put(1,option1);
        map.put(2,option2);
        map.put(3,option3);
        //第一次选择
        Integer firstSelect= RandomUtil.randomEle(doors);
        //主持人打开一扇有羊的门
        Integer open= openDoor(doors,map,firstSelect);
        //排除打开门的选项
        doors.remove(open);
        //第二次选择（随机）
        Integer second=  RandomUtil.randomEle(doors);
        return map.get(second);
    }

    private static Integer openDoor(List<Integer> doors, Map<Integer,String> map,Integer firstSelect){
        return doors.stream().filter(door->map.get(door).equals("山羊")&&!firstSelect.equals(door)).findFirst().get();
    }
}

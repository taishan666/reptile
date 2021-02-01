package com.tarzan.reptile.demo;

import java.awt.*;
import java.awt.event.*;

public class RobotPaint{

    //中心点x坐标
    static int centerPx = 360;
    //中心点y坐标
    static int centerPy = 540;
    //起始点x坐标
    static int beginPx = 360;
    //起始点y坐标
    static int beginPy = 340;
    //圆半径
    static int radio = 200;
    //上半圆
    static int RIGHT_LEFT_UP = 2;
    //下半圆
    static int LEFT_RIGHT_DOWN = 3;
    //标识
    static int flag = LEFT_RIGHT_DOWN;
   //java robot对象
    static Robot robot; 

    static {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }


    public static void main(String [] args) throws AWTException{
        //运行后等待3秒，切换到画图界面
        robot.delay(3000);
        //鼠标移动到起始点位
        robot.mouseMove(beginPx, beginPy);
        robot.delay(100);
        //鼠标点击左键
        robot.mousePress(InputEvent.BUTTON1_MASK);
        //鼠标画圈
        circle(robot);
        //释放鼠标左键
        robot.mouseRelease(InputEvent.BUTTON1_MASK);

    }

    //画圆圈
    public static void circle(Robot robot) {
        int px=beginPx;
        int py=beginPy;
        while (true){
            if (flag == LEFT_RIGHT_DOWN) {//上半圆的运动轨迹方程
                px += 1;
                //y = b + (int) Math.sqrt(r^2 - (x - a)^2);
                if (px <= centerPx+radio) {
                    py = centerPy - (int) Math.sqrt( Math.pow(radio, 2) - Math.pow( (px - centerPx),2 ) );
                }else {
                    px = centerPx + radio;
                    flag = RIGHT_LEFT_UP;
                }
            }else if (flag == RIGHT_LEFT_UP) {//下半圆的运动轨迹方程
                px -= 1;
                if (px >= centerPx - radio) {
                    py = centerPy + (int) Math.sqrt( Math.pow(radio, 2) - Math.pow( (px - centerPx),2 ) );
                }else{
                    px = centerPx - radio;
                    flag = LEFT_RIGHT_DOWN;
                }
            }
            robot.mouseMove(px, py);
            robot.delay(20);
            if(px==beginPx&&py==beginPy){
                break;
            }
        }
    }

}

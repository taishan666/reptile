package com.tarzan.reptile.demo;


import java.awt.*;
import java.awt.event.InputEvent;

/**
 * @Author Javee
 * @Date 2019/8/9 14:43
 * @Description 写一个脚本，打开电脑画板后，自动在画板上画一个奥运五环
 */
public class AutoPaint {
    public static void main(String[] args) throws AWTException {
        Robot robot = new Robot();
        robot.delay(3000); //运行代码后，暂停三秒，留够时间去打开电脑自带的画板，并点击形状里面的椭圆形

        //1111111111111111111111
        int i = 10;
        while (i-- > 0) {
            robot.mouseMove(400, 300);
        }
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.delay(100);       //这里延迟0.1s，可以看到动态画的过程
        i = 10;
        while (i-- > 0) {
            robot.mouseMove(650, 550);
        }
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
        robot.delay(1000);       //画完一个圆停止0.2s，否则因为计算机执行速度太快，看不到动态作图的过程

        //222222222222222222222
        i = 10;
        while (i-- > 0) {
            robot.mouseMove(0, 300);
        }
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK); //画完一个圆后在圆外面点一下鼠标，否则回拖动画的圆到下一个位置
        i = 10;
        while (i-- > 0) {
            robot.mouseMove(600, 300);
        }
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.delay(100);       //这里延迟0.1s，可以看到动态画的过程
        i = 10;
        while (i-- > 0) {
            robot.mouseMove(850, 550);
        }
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
        robot.delay(1000);       //画完一个圆停止0.2s，否则因为计算机执行速度太快，看不到动态作图的过程

        //3333333333333333333333333
        i = 10;
        while (i-- > 0) {
            robot.mouseMove(0, 300);
        }
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK); //画完一个圆后在圆外面点一下鼠标，否则回拖动画的圆到下一个位置
        i = 10;
        while (i-- > 0) {
            robot.mouseMove(800, 300);
        }
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.delay(100);       //这里延迟0.2s，可以看到动态画的过程
        i = 10;
        while (i-- > 0) {
            robot.mouseMove(1050, 550);
        }
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
        robot.delay(200);       //画完一个圆停止0.1s，否则因为计算机执行速度太快，看不到动态作图的过程

        //44444444444444444444444444
        i = 10;
        while (i-- > 0) {
            robot.mouseMove(0, 300);
        }
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK); //画完一个圆后在圆外面点一下鼠标，否则回拖动画的圆到下一个位置
        i = 10;
        while (i-- > 0) {
            robot.mouseMove(500, 425);
        }
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.delay(100);       //这里延迟0.1s，可以看到动态画的过程
        i = 10;
        while (i-- > 0) {
            robot.mouseMove(750, 675);
        }
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
        robot.delay(200);       //画完一个圆停止0.2s，否则因为计算机执行速度太快，看不到动态作图的过程

        //555555555555555555555555
        i = 10;
        while (i-- > 0) {
            robot.mouseMove(0, 300);
        }
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK); //画完一个圆后在圆外面点一下鼠标，否则回拖动画的圆到下一个位置
        i = 10;
        while (i-- > 0) {
            robot.mouseMove(700, 425);
        }
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.delay(100);       //这里延迟0.1s，可以看到动态画的过程
        i = 10;
        while (i-- > 0) {
            robot.mouseMove(950, 675);
        }
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
        robot.delay(200);       //画完一个圆停止0.2s，否则因为计算机执行速度太快，看不到动态作图的过程


    }
}

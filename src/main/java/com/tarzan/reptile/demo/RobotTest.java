package com.tarzan.reptile.demo;


import java.awt.*;
import java.awt.event.KeyEvent;

public class RobotTest {


    public static void main(String[] args) {
       //  getPointInfo();

       task();


    }


    private static void task(){
        try {
            Robot  robot = new Robot();
          //  robot.delay(5000);

           // robot.delay(1000);
            robot.mouseMove(1802,6);
         //   robot.delay(1000);
            robot.mousePress(KeyEvent.BUTTON1_MASK);
            robot.mouseRelease(KeyEvent.BUTTON1_MASK);
        } catch (AWTException e) {
            e.printStackTrace();
        }

    }




}

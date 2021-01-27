package com.tarzan.reptile.demo;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

public class JavaRobot {

    public static void main(String[] args) throws AWTException {
        JavaCMD.run("calc");
        Robot robot = new Robot();
        Random random = new Random();
        int a = 0;
        while (true) {

            robot.keyPress(KeyEvent.VK_Y);
            robot.keyRelease(KeyEvent.VK_Y);
            a = Math.abs(random.nextInt()) % 100 + 50;
            robot.delay(a);

            robot.keyPress(KeyEvent.VK_B);
            robot.keyRelease(KeyEvent.VK_B);
            a = Math.abs(random.nextInt()) % 100 + 50;
            robot.delay(a);

            robot.keyPress(KeyEvent.VK_Q);
            robot.keyRelease(KeyEvent.VK_Q);
            a = Math.abs(random.nextInt()) % 100 + 50;
            robot.delay(a);

            robot.keyPress(KeyEvent.VK_U);
            robot.keyRelease(KeyEvent.VK_U);

            robot.keyPress(KeyEvent.VK_SPACE);
            robot.keyRelease(KeyEvent.VK_SPACE);

            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);

            a = Math.abs(random.nextInt()) % 2000 + 1000;
            System.out.println(a);
            robot.delay(a);
        }
    }
}

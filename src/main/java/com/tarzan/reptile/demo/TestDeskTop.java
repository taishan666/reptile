package com.tarzan.reptile.demo;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
/**
 * Java实现在QQ上输入文字并发送
 * @author tarzan LIU
 *
 */
public class TestDeskTop {
    static Desktop deskApp = Desktop.getDesktop();

    static Robot robot;

    static {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }


    public static void main(String [] args) throws AWTException{
        openApp("C:\\Users\\liuya\\AppData\\Local\\Google\\Chrome\\Application\\chrome.exe");
        //浏览器最大化快捷键 alt+space+x
        keyPressWithAlt(robot,KeyEvent.VK_SPACE,KeyEvent.VK_X);
        //地址栏输入网址
        keyPressString(robot,"baidu.com");
        //回车
        keyPress(robot, KeyEvent.VK_ENTER);
    }

    //打开app
    public static void openApp(String appPath) {
        //判断当前系统释放支持Desktop提供的接口
        if (Desktop.isDesktopSupported()) {
            try {
                deskApp.open(new File(appPath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //延迟1秒
        robot.delay(1000);
    }


    //Alt组合键
    public static void keyPressWithAlt(Robot r, int key1,int key2) {
        r.keyPress(KeyEvent.VK_ALT);
        r.keyPress(key1);
        r.keyPress(key2);
        r.keyRelease(key2);
        r.keyRelease(key1);
        r.keyRelease(KeyEvent.VK_ALT);
        r.delay(100);
    }

    //输入字符串
    public static void keyPressString(Robot r, String str) {
        //获取剪切板
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
        //将传入字符串封装下
        Transferable tText = new StringSelection(str);
        //将字符串放入剪切板
        clip.setContents(tText, null);
        //按下Ctrl+V实现粘贴文本
        keyPressWithCtrl(r, KeyEvent.VK_V);
        r.delay(100);
    }

    //Ctrl组合键
    public static void keyPressWithCtrl(Robot r, int key) {
        r.keyPress(KeyEvent.VK_CONTROL);
        r.keyPress(key);
        r.keyRelease(key);
        r.keyRelease(KeyEvent.VK_CONTROL);
        r.delay(100);
    }


    //实现按一次某个按键
    public static void keyPress(Robot r, int key) {
        //按下键
        r.keyPress(key);
        //释放键
        r.keyRelease(key);
        r.delay(100);
    }
}

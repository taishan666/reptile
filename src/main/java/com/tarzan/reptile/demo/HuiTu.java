package com.tarzan.reptile.demo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HuiTu {

    JFrame mFrame=new JFrame("画板");
    JPanel mPanel=new JPanel();
    int LastX=0;
    int LastY=0;

    public HuiTu() {
        mFrame.setSize(800, 800);
        mFrame.setVisible(true);
        mFrame.setForeground(Color.BLUE);
        mFrame.add(mPanel);
        mPanel.setBackground(Color.WHITE);
        mFrame.addMouseListener(new MouseAdapter() {
            public void mousePress(MouseEvent e) {
                LastX = e.getX();
                LastY = e.getY();
            }
        });
        mFrame.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                int x=e.getX();
                int y=e.getY();
                Graphics g =mFrame.getGraphics();
                g.drawLine(LastX, LastY, x, y);
                LastX=e.getX();
                LastY=e.getY();
            }

        });
        mFrame.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                String string=String.valueOf(e.getKeyChar());
                mFrame.getGraphics().drawString(string, LastX, LastY);
                LastX += 30;
            }
        });

    }


    public static void main(String[] args) {
        JavaCMD.run("calc");
       // new HuiTu();
    }


}

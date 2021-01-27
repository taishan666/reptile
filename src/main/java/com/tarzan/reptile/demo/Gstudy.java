package com.tarzan.reptile.demo;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Gstudy extends JFrame {

    private int x1, y1, x2, y2;
    private newPanel panel = new newPanel();
    private JButton btn = new JButton("清除");

    public Gstudy() {
        setTitle("交互式绘图");
        setBounds(10, 10, 750, 700);
        btn.addActionListener(new ClearList());
        panel.add(btn, BorderLayout.SOUTH);
        add(panel);
    }

    public static void main(String[] args) {
        Gstudy frm = new Gstudy();
        frm.setVisible(true);
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private class ClearList implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            panel.paintComponent(panel.getGraphics());
        }
    }

    private class MyMouseList extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            x1 = e.getX();
            y1 = e.getY();
        }
    }

    private class MyMouseMotionList extends MouseMotionAdapter {
        public void mouseDragged(MouseEvent e) {
            x2 = e.getX();
            y2 = e.getY();
            Graphics g = panel.getGraphics();
            g.drawLine(x1, y1, x2, y2);
            x1 = x2;
            y1 = y2;
        }
    }

    private class newPanel extends JPanel {

        public newPanel() {
            this.addMouseMotionListener(new MyMouseMotionList());
            this.addMouseListener(new MyMouseList());
        }

        @Override
        protected void paintComponent(Graphics g) {
            // TODO Auto-generated method stub
            super.paintComponent(g);
        }

    }
}

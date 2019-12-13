package com.tk.algo_practice.fractal_basics;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("all")
public class AlgoFrame extends JFrame {

    private int canvasWidth;
    private int canvasHeight;

    public AlgoFrame (String title, int canvasWidth, int canvasHeight) {

        super(title);

        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;

        AlgoCanvas canvas = new AlgoCanvas();
        setContentPane(canvas);

        setResizable(false);  // 在setResizable(false)后进行pack()，防止在Windows下系统修改frame的尺寸
        pack();               // 具体参见：http://coding.imooc.com/learn/questiondetail/26420.html


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public AlgoFrame (String title) {
        this(title, 1024, 768);
    }

    public int getCanvasWidth() {
        return canvasWidth;
    }

    public int getCanvasHeight() {
        return canvasHeight;
    }

    private FractalData data;
    public void render(FractalData data) {
        this.data = data;
        repaint();
    }

    private class AlgoCanvas extends JPanel {

        public AlgoCanvas() {
            super(true);
        }

        @Override
        public void paintComponent(Graphics g) {

            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g;

            //抗锯齿
            RenderingHints hints = new RenderingHints(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.addRenderingHints(hints);

            //具体绘制
//            drawFractal1(g2d, 0, 0, canvasHeight, canvasWidth, 0);
//            drawFractal3(g2d, 0, canvasHeight, canvasWidth, 0);

            //雪花
//            drawFractal4(g2d, 0, canvasHeight/3-3, canvasWidth, 0, 0);
//            drawFractal4(g2d, canvasWidth, canvasHeight/3-3, canvasWidth, -120, 0);
//            drawFractal4(g2d, canvasWidth/2, canvasHeight/3-3 + canvasWidth*Math.sin(60.0 * Math.PI / 180.0),
//                    canvasWidth, 120, 0);

            //树
            drawFractal5(g2d, canvasWidth/2, canvasHeight, canvasHeight, 0, 0);
        }

        private void drawFractal1(Graphics2D g2d, int x, int y, int w, int h, int depth) {

            if (depth == data.depth) {
                AlgoVisHelper.setColor(g2d, AlgoVisHelper.Indigo);
                AlgoVisHelper.fillRectangle(g2d, x, y, w, h);
                return;
            }

            if (w <= 1 || h <= 1) {
                AlgoVisHelper.setColor(g2d, AlgoVisHelper.Indigo);
                AlgoVisHelper.fillRectangle(g2d, x, y, Math.max(w, 1), Math.max(h, 1));
                return;
            }

            int w_3 = w / 3;
            int h_3 = h / 3;
            drawFractal1(g2d, x, y, w_3, h_3, depth + 1);
            drawFractal1(g2d, x + 2*w_3, y, w_3, h_3, depth + 1);
            drawFractal1(g2d, x + w_3, y + h_3, w_3, h_3, depth + 1);
            drawFractal1(g2d, x, y + 2*h_3, w_3, h_3, depth + 1);
            drawFractal1(g2d, x + 2*w_3, y + 2*h_3, w_3, h_3, depth + 1);

//            drawFractal1(g2d, x + w_3, y, w_3, h_3, depth + 1);
//            drawFractal1(g2d, x, y + h_3, w_3, h_3, depth + 1);
//            drawFractal1(g2d, x + w_3, y + h_3, w_3, h_3, depth + 1);
//            drawFractal1(g2d, x + 2*w_3, y + h_3, w_3, h_3, depth + 1);
//            drawFractal1(g2d, x + w_3, y + 2*h_3, w_3, h_3, depth + 1);

            return;
        }

        private void drawFractal2(Graphics2D g2d, int x, int y, int w, int h, int depth) {

            int w_3 = w / 3;
            int h_3 = h / 3;

            if (depth == data.depth) {
                AlgoVisHelper.setColor(g2d, AlgoVisHelper.Indigo);
                AlgoVisHelper.fillRectangle(g2d, x + w_3, y + h_3, w_3, h_3);
                return;
            }

            if (w <= 1 || h <= 1) {
                return;
            }

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (i == 1 && j == 1) {
                        AlgoVisHelper.setColor(g2d, AlgoVisHelper.Indigo);
                        AlgoVisHelper.fillRectangle(g2d, x + w_3, y + h_3, w_3, h_3);
                    } else {
                        drawFractal2(g2d, x + i*w_3, y + j*h_3, w_3, h_3, depth+1);
                    }
                }
            }

            return;
        }

        private void drawFractal3(Graphics2D g2d, int Ax, int Ay, int side, int depth) {

            if (side <= 1) {
                AlgoVisHelper.setColor(g2d, AlgoVisHelper.Indigo);
                AlgoVisHelper.fillRectangle(g2d, Ax, Ay, 1, 1);
                return;
            }

            int Bx = Ax + side;
            int By = Ay;

            int h = (int) (Math.sin(60.0 * Math.PI / 180.0) * side);
            int Cx = Ax + side / 2;
            int Cy = Ay - h;

            if (depth == data.depth) {
                AlgoVisHelper.setColor(g2d, AlgoVisHelper.Indigo);
                AlgoVisHelper.fillTriangle(g2d, Ax, Ay, Bx, By, Cx, Cy);
                return;
            }

            int AB_centerx = (Ax + Bx) / 2;
            int AB_centery = (Ay + By) / 2;

            int AC_centerx = (Ax + Cx) / 2;
            int AC_centery = (Ay + Cy) / 2;

            drawFractal3(g2d, Ax, Ay, side / 2, depth + 1);
            drawFractal3(g2d, AC_centerx, AC_centery, side / 2, depth + 1);
            drawFractal3(g2d, AB_centerx, AB_centery, side / 2, depth + 1);
            return;
        }

        private void drawFractal4(Graphics2D g2d, double x1, double y1, double side, double angle, int depth) {

            if (side <= 0) {
                return;
            }

            if (depth == data.depth) {
                double x2 = x1 + side * Math.cos(angle * Math.PI / 180.0);
                double y2 = y1 - side * Math.sin(angle * Math.PI / 180.0);
                AlgoVisHelper.setColor(g2d, AlgoVisHelper.Indigo);
                AlgoVisHelper.drawLine(g2d, x1, y1, x2, y2);
                return;
            }

            double side_3 = side / 3;

            double x2 = x1 + side_3 * Math.cos(angle * Math.PI / 180.0);
            double y2 = y1 - side_3 * Math.sin(angle * Math.PI / 180.0);
            drawFractal4(g2d, x1, y1, side_3, angle, depth+1);

            double x3 = x2 + side_3 * Math.cos((angle+60.0) * Math.PI / 180.0);
            double y3 = y2 - side_3 * Math.sin((angle+60.0) * Math.PI / 180.0);
            drawFractal4(g2d, x2, y2, side_3, angle+60.0, depth+1);

            double x4 = x3 + side_3 * Math.cos((angle-60.0) * Math.PI / 180.0);
            double y4 = y3 - side_3 * Math.sin((angle-60.0) * Math.PI / 180.0);
            drawFractal4(g2d , x3, y3, side_3, angle-60.0, depth+1);

            drawFractal4(g2d, x4, y4, side_3, angle, depth+1);
            return;
        }

        private void drawFractal5(Graphics2D g2d, double x1, double y1, double side, double angle, int depth) {

            double side_2 = side / 2;

            if (side_2 <= 0) {
                return;
            }

            if (depth == data.depth) {
                double x2 = x1 - side * Math.sin(angle * Math.PI / 180.0);
                double y2 = y1 - side * Math.cos(angle * Math.PI / 180.0);
                AlgoVisHelper.setColor(g2d, AlgoVisHelper.Indigo);
                AlgoVisHelper.drawLine(g2d, x1, y1, x2, y2);
                return;
            }

            double x2 = x1 - side_2 * Math.sin(angle * Math.PI / 180.0);
            double y2 = y1 - side_2 * Math.cos(angle * Math.PI / 180.0);
            AlgoVisHelper.setColor(g2d, AlgoVisHelper.Indigo);
            AlgoVisHelper.drawLine(g2d, x1, y1, x2, y2);

            drawFractal5(g2d, x2, y2, side_2, angle + data.splitAngle/2, depth+1);
            drawFractal5(g2d, x2, y2, side_2, angle - data.splitAngle/2, depth+1);

            return;
        }


        @Override
        public Dimension getPreferredSize() {
            return new Dimension(canvasWidth, canvasHeight);
        }
    }
}

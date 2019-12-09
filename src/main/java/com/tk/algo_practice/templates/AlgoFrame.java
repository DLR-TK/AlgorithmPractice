package com.tk.algo_practice.templates;

import com.tk.algo_practice.circle_animation.Circle;

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

        setResizable(false);
        pack();

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

    private Object data;
    public void render(Object data) {
        this.data = data;
        repaint();
    }

    private class AlgoCanvas extends JPanel {

        public AlgoCanvas() {
            //双缓存
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
            hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.addRenderingHints(hints);

            //TODO:具体绘制

        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(canvasWidth, canvasHeight);
        }
    }
}

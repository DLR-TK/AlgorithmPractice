package com.tk.algo_practice.fractal_basics;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@SuppressWarnings("all")
public class AlgoVisualizer {

    private static final int DELAY = 40;

    private FractalData data;
    private AlgoFrame frame;

    //雪花
    public AlgoVisualizer(int maxDepth, int side) {

        data = new FractalData(maxDepth);

        int sceneWidth = side;
        int sceneHeight = side*3/2;

//        int sceneWidth = (int) Math.pow(2, maxDepth); //正方形 3
//        int sceneHeight = (int) Math.pow(2, maxDepth);//三角形2

//        int sceneWidth = 1024;
//        int sceneHeight = 768;

        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Fractal Visualizer", sceneWidth, sceneHeight);
            frame.addKeyListener(new AlgoKeyListener());
            new Thread(() -> {
               run();
            }).start();

        });
    }

    //树
    public AlgoVisualizer(int sceneWidth, int sceneHeight, int maxDepth, double splitAngle) {

        data = new FractalData(maxDepth, splitAngle);

        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Fractal Visualizer", sceneWidth, sceneHeight);
            frame.addKeyListener(new AlgoKeyListener());
            new Thread(() -> {
                run();
            }).start();

        });
    }

    private void run() {
        setData(data.depth);
    }

    private void setData(int depth) {

        if (depth >= 0) {
            data.depth = depth;
        }
        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }

    private class AlgoKeyListener extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent event) {

            if (event.getKeyChar() >= '0' && event.getKeyChar() <= '9') {
                int depth = event.getKeyChar() - '0';
                setData(depth);
            }
        }
    }

    public static void main(String[] args) {

//        int maxDepth = 9; //三角形
//        int maxDepth = 6; //正方形

        //雪花
//        int maxDepth = 6;
//        int side = 600;
//        AlgoVisualizer visualizer = new AlgoVisualizer(maxDepth, side);

        int maxDepth = 6;
        double splitAngle = 60.0;
        int sceneWitch = 800;
        int sceneHeight = 800;
        AlgoVisualizer visualizer = new AlgoVisualizer(sceneWitch, sceneHeight, maxDepth, splitAngle);
    }
}

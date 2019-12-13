package com.tk.algo_practice.recursive_circle;

import java.awt.*;

@SuppressWarnings("all")
public class AlgoVisualizer {

    private static final int DELAY = 40;

    private CircleData data;
    private AlgoFrame frame;

    public AlgoVisualizer(int sceneWidth, int sceneHeight) {

        int R = Math.min(sceneWidth, sceneHeight) / 2 - 2;
        data = new CircleData(sceneWidth/2, sceneHeight/2, R, R/4, 4);

        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Welcome", sceneWidth, sceneHeight);

            new Thread(() -> {
               run();
            }).start();

        });
    }

    private void run() {
        setData();
    }

    private void setData() {

        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }

    public static void main(String[] args) {

        int sceneWidth = 800;
        int sceneHeight = 800;

        AlgoVisualizer visualizer = new AlgoVisualizer(sceneWidth, sceneHeight);
    }
}

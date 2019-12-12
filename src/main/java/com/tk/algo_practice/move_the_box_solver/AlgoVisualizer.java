package com.tk.algo_practice.move_the_box_solver;

import java.awt.*;

@SuppressWarnings("all")
public class AlgoVisualizer {

    private static int blockSide = 80;
    private static int DELAY = 5;

    //创建数据
    private GameData data;      //数据
    private AlgoFrame frame;  //视图

    public AlgoVisualizer(String fileName) {

        //初始化数据
        data = new GameData(fileName);

        int sceneWidth = data.M() * blockSide;
        int sceneHeight = data.N() * blockSide;

        //初始化视图
        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Move the Box Solver", sceneWidth, sceneHeight);

            new Thread(() -> {
               run();
            }).start();

        });
    }

    private void run() {
        // 编写动画逻辑
        setData();

        if (data.solve()) {
            System.out.println("The game has a solution!");
        } else {
            System.out.println("The game does not have a solution!");
        }
    }

    private void setData() {

        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }

    public static void main(String[] args) {

        String fileName = "level/boston_09.txt";

        AlgoVisualizer visualizer = new AlgoVisualizer(fileName);
    }
}

package com.tk.algo_practice.mine_sweeper_core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("all")
public class AlgoVisualizer {

    private static int blockSide = 32;
    private static int DELAY = 5;

    //创建数据
    private MineSweeperData data;      //数据
    private AlgoFrame frame;  //视图

    private static final int[][] d = {{-1,0}, {0,1}, {1,0}, {0,-1}};

    public AlgoVisualizer(int N, int M, int mineNumber) {

        //初始化数据
        data = new MineSweeperData(N, M, mineNumber);

        int sceneWidth = data.M() * blockSide;
        int sceneHeight = data.N() * blockSide;

        //初始化视图
        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Mine Sweeper", sceneWidth, sceneHeight);
            frame.addMouseListener(new AlgoMouseListener());
            new Thread(() -> {
               run();
            }).start();
        });
    }

    private void run() {
        // 编写动画逻辑
        setData(false, -1, -1);
    }

    private void setData(boolean isLeftClicked, int x, int y) {

        if (data.inArea(x, y)) {

            if (isLeftClicked) {
                if (data.isMine(x, y)) {
                    //TODO GameOver
                    data.open[x][y] = true;
                } else {
                    data.open(x, y);
                }
                data.open[x][y] = true;
            } else {
                data.flags[x][y] = !data.flags[x][y];
            }
        }
        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }

    private class AlgoMouseListener extends MouseAdapter {

        @Override
        public void mouseReleased(MouseEvent event) {

            event.translatePoint(
                    -(int)(frame.getBounds().width - frame.getCanvasWidth()),
                    -(int)(frame.getBounds().height - frame.getCanvasHeight()));

            Point pos = event.getPoint();

            int w = frame.getCanvasWidth() / data.M();
            int h = frame.getCanvasHeight() / data.N();

            int x = pos.y / h;
            int y = pos.x / w;

            if (SwingUtilities.isLeftMouseButton(event)) {
                setData(true, x, y);
            } else {
                setData(false, x, y);
            }
        }
    }

    public static void main(String[] args) {

        int N = 20;
        int M = 20;
        int mineNumber = 50;

        AlgoVisualizer visualizer = new AlgoVisualizer(N, M, mineNumber);
    }
}

package com.tk.algo_practice.maze_solver;

import java.awt.*;

@SuppressWarnings("all")
public class AlgoVisualizer {

    private static int blockSide = 8;
    private static int DELAY = 5;

    //创建数据
    private MazeData data;      //数据
    private AlgoFrame frame;  //视图

    private static final int[][] d = {{-1,0}, {0,1}, {1,0}, {0,-1}};

    public AlgoVisualizer(String mazeFile) {

        //初始化数据
        data = new MazeData(mazeFile);

        int sceneWidth = data.M() * blockSide;
        int sceneHeight = data.N() * blockSide;

        //初始化视图
        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Maze Solver Visualization", sceneWidth, sceneHeight);

            new Thread(() -> {
               run();
            }).start();

        });
    }

    private void run() {
        // 编写动画逻辑
        setData(-1, -1, false);

        if (!go(data.getEntranceX(), data.getEntranceY())) {
            System.out.println("The maze has no solution!");
        }

        setData(-1, -1, false);
    }

    /**
     * 从(x,y)位置开始求解迷宫，如果成功返回true否则返回false
     */
    private boolean go(int x, int y) {

        if (!data.inArea(x, y)) {
            throw new IllegalArgumentException("x,y are out of index in go function!");
        }

        data.visited[x][y] = true;

        setData(x, y, true);

        if (x == data.getExitX() && y == data.getExitY()) {
            return true;
        }

        for (int i = 0; i < 4; i++) {
            int newX = x + d[i][0];
            int newY = y + d[i][1];
            if (data.inArea(newX, newY) &&
                    data.getMaze(newX, newY) == MazeData.ROAD &&
                    !data.visited[newX][newY]) {
                if (go(newX, newY)){
                    return true;
                }
            }
        }

        setData(x, y, false);
        return false;
    }

    private void setData(int x, int y, boolean isPath) {

        if (data.inArea(x, y)) {
            data.path[x][y] = isPath;
        }

        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }

    public static void main(String[] args) {

        String mazeFile = "maze_101_101.txt";

        AlgoVisualizer visualizer = new AlgoVisualizer(mazeFile);
    }
}

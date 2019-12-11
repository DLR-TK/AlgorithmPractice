package com.tk.algo_practice.maze_generation;

import java.awt.*;
import java.util.LinkedList;

@SuppressWarnings("all")
public class AlgoVisualizer3 {

    private static int blockSide = 8;
    private static int DELAY = 5;

    //创建数据
    private MazeData data;      //数据
    private AlgoFrame frame;  //视图

    private static final int[][] d = {{-1,0}, {0,1}, {1,0}, {0,-1}};

    public AlgoVisualizer3(int N, int M) {

        //初始化数据
        data = new MazeData(N, M);

        int sceneWidth = data.M() * blockSide;
        int sceneHeight = data.N() * blockSide;

        //初始化视图
        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Random Maze Generation Visualization", sceneWidth, sceneHeight);

            new Thread(() -> {
               run();
            }).start();
        });
    }

    private void run() {
        // 编写动画逻辑
        setData(-1, -1);

        LinkedList<Position> queue = new LinkedList<>();
        Position first = new Position(data.getEntranceX(), data.getEntranceY() + 1);
        queue.addLast(first);
        data.visited[first.getX()][first.getY()] = true;

        while (queue.size() != 0) {
            Position curPos = queue.removeFirst();

            for (int i = 0; i < 4; i++) {
                int newX = curPos.getX() + d[i][0] * 2;
                int newY = curPos.getY() + d[i][1] * 2;

                if (data.inArea(newX, newY) && !data.visited[newX][newY]) {
                    queue.addLast(new Position(newX, newY));
                    data.visited[newX][newY] = true;
                    setData(curPos.getX() + d[i][0], curPos.getY() + d[i][1]);
                }
            }
        }

        setData(-1, -1);
    }

    private void setData(int x, int y) {

        if (data.inArea(x, y)) {
            data.maze[x][y] = MazeData.ROAD;
        }

        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }

    public static void main(String[] args) {

        int N = 101;
        int M = 101;

        AlgoVisualizer3 visualizer = new AlgoVisualizer3(N, M);
    }
}

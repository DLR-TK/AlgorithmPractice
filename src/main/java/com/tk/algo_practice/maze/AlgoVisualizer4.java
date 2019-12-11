package com.tk.algo_practice.maze;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@SuppressWarnings("all")
public class AlgoVisualizer4 {

    private static int blockSide = 8;
    private static int DELAY = 5;

    //创建数据
    private MazeData data;      //数据
    private AlgoFrame frame;  //视图

    private static final int[][] d = {{-1,0}, {0,1}, {1,0}, {0,-1}};

    public AlgoVisualizer4(int N, int M) {

        //初始化数据
        data = new MazeData(N, M);

        int sceneWidth = data.M() * blockSide;
        int sceneHeight = data.N() * blockSide;

        //初始化视图
        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Random Maze Generation Visualization", sceneWidth, sceneHeight);
            frame.addKeyListener(new AlgoKeyListener());

            new Thread(() -> {
               run();
            }).start();
        });
    }

    private void run() {
        // 编写动画逻辑
        setRoadData(-1, -1);

        RandomQueue<Position> queue = new RandomQueue<>();
        Position first = new Position(data.getEntranceX(), data.getEntranceY() + 1);
        queue.add(first);
        data.visited[first.getX()][first.getY()] = true;
        data.openMist(first.getX(), first.getY());
        
        while (queue.size() != 0) {
            Position curPos = queue.remove();

            for (int i = 0; i < 4; i++) {
                int newX = curPos.getX() + d[i][0] * 2;
                int newY = curPos.getY() + d[i][1] * 2;

                if (data.inArea(newX, newY) && !data.visited[newX][newY]) {
                    queue.add(new Position(newX, newY));
                    data.visited[newX][newY] = true;
                    data.openMist(newX, newY);
                    setRoadData(curPos.getX() + d[i][0], curPos.getY() + d[i][1]);
                }
            }
        }
        setRoadData(-1, -1);
    }

    /**
     * 从(x,y)位置开始求解迷宫，如果成功返回true否则返回false
     */
    private boolean go(int x, int y) {

        if (!data.inArea(x, y)) {
            throw new IllegalArgumentException("x,y are out of index in go function!");
        }

        data.visited[x][y] = true;

        setPathData(x, y, true);

        if (x == data.getExitX() && y == data.getExitY()) {
            return true;
        }

        for (int i = 0; i < 4; i++) {
            int newX = x + d[i][0];
            int newY = y + d[i][1];
            if (data.inArea(newX, newY) &&
                    data.maze[newX][newY] == MazeData.ROAD &&
                    !data.visited[newX][newY]) {
                if (go(newX, newY)){
                    return true;
                }
            }
        }

        setPathData(x, y, false);
        return false;
    }

    private void setRoadData(int x, int y) {

        if (data.inArea(x, y)) {
            data.maze[x][y] = MazeData.ROAD;
        }


        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }

    private void setPathData(int x, int y, boolean isPath) {

        if (data.inArea(x, y)) {
            data.path[x][y] = isPath;
        }

        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }

    private class AlgoKeyListener extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent event){
            if(event.getKeyChar() == ' '){
                for(int i = 0 ; i < data.N() ; i ++)
                    for(int j = 0 ; j < data.M() ; j ++)
                        data.visited[i][j] = false;

                new Thread(() -> {
                    go(data.getEntranceX(), data.getEntranceY());
                }).start();
            }
        }
    }

    public static void main(String[] args) {

        int N = 101;
        int M = 101;

        AlgoVisualizer4 visualizer = new AlgoVisualizer4(N, M);
    }
}

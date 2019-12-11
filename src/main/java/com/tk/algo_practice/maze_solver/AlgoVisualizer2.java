package com.tk.algo_practice.maze_solver;

import java.awt.*;
import java.util.Stack;

@SuppressWarnings("all")
public class AlgoVisualizer2 {

    private static int blockSide = 8;
    private static int DELAY = 5;

    //创建数据
    private MazeData data;      //数据
    private AlgoFrame frame;  //视图

    private static final int[][] d = {{-1,0}, {0,1}, {1,0}, {0,-1}};

    public AlgoVisualizer2(String mazeFile) {

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

        Stack<Position> stack = new Stack<>();
        Position entrance = new Position(data.getEntranceX(), data.getEntranceY());
        stack.push(entrance);
        data.visited[entrance.getX()][entrance.getY()] = true;

        boolean isSolved = false;
        while (!stack.isEmpty()) {
            Position curPos = stack.pop();
            setData(curPos.getX(), curPos.getY(), true);

            if (curPos.getX() == data.getExitX() && curPos.getY() == data.getExitY()) {
                isSolved = true;
                findPath(curPos);
                break;
            }

            for (int i = 0; i < 4; i++) {
                int newX = curPos.getX() + d[i][0];
                int newY = curPos.getY() + d[i][1];

                if (data.isArea(newX, newY) &&
                        !data.visited[newX][newY] &&
                        data.getMaze(newX, newY) == MazeData.ROAD) {
                    stack.push(new Position(newX, newY, curPos));
                    data.visited[newX][newY] = true;
                }
            }
        }

        if (!isSolved) {
            throw new IllegalArgumentException("The maze has no Solution!");
        }
        setData(-1, -1, false);
    }

    private void findPath(Position des) {

        Position cur = des;
        while (cur != null) {
            data.result[cur.getX()][cur.getY()] = true;
            cur = cur.getPrev();
        }
    }

    private void setData(int x, int y, boolean isPath) {

        if (data.isArea(x, y)) {
            data.path[x][y] = isPath;
        }

        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }

    public static void main(String[] args) {

        String mazeFile = "maze_101_101.txt";

        AlgoVisualizer2 visualizer = new AlgoVisualizer2(mazeFile);
    }
}

package com.tk.algo_practice.move_the_box_solver;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class GameData {

    private int maxTurn;
    private Board startBoard;
    private int N, M;

    private Board showBoard;

    public GameData(String filename) {

        if (filename == null) {
            throw new IllegalArgumentException("filename can not be null!!!");
        }

        Scanner scanner = null;
        try {

            File file = new File(filename);
            if (!file.exists()) {
                throw new IllegalArgumentException("File :" + filename + "doesn't exists!");
            }

            FileInputStream fis = new FileInputStream(file);
            scanner = new Scanner(new BufferedInputStream(fis), "UTF-8");

            String turnLine = scanner.nextLine();
            this.maxTurn = Integer.parseInt(turnLine);

            ArrayList<String> lines = new ArrayList<>();
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                lines.add(line);
            }

            startBoard = new Board(lines.toArray(new String[lines.size()]));

            this.N = startBoard.N();
            this.M = startBoard.M();

            startBoard.print();

            showBoard = new Board(startBoard);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    public int N() {
        return N;
    }

    public int M() {
        return M;
    }

    public Board getShowBoard() {
        return showBoard;
    }

    public boolean inArea(int x, int y) {
        return x >=0 && x < N && y >= 0 && y < M;
    }

    public boolean solve() {

        if (maxTurn < 0) {
            return false;
        }
        return solve(startBoard, maxTurn);
    }

    private static final int[][] d = {{1,0}, {0,1}, {0,-1}};
    /**
     * 通过盘面board，使用turn次move，解决move the box问题
     * 成功解决返回true，否则返回false
     */
    private boolean solve(Board board, int turn) {

        if (board == null || turn < 0) {
            throw new IllegalArgumentException("Illegal arguments in solve function!");
        }

        if (turn == 0) {
            return board.isWin();
        }

        if (board.isWin()) {
            return true;
        }

        for (int x = 0; x < N; x++) {
            for (int y = 0; y < M; y++) {
                if (board.getData(x, y) != Board.EMPTY) {
                    for (int i = 0; i < 3; i++) {
                        int newX = x + d[i][0];
                        int newY = y + d[i][1];

                        if (inArea(newX, newY)) {
                            String swapString = String.format("swap (%d, %d) and (%d, %d)",x ,y, newX, newY);
                            Board nextBoard = new Board(board, board, swapString);
                            nextBoard.swap(x, y, newX, newY);
                            nextBoard.run();
                            if (solve(nextBoard, turn - 1)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}

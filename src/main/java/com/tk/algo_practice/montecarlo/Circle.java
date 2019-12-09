package com.tk.algo_practice.montecarlo;

import java.awt.*;

@SuppressWarnings("all")
public class Circle {

    private int x;
    private int y;
    private int r;

    public Circle(int x, int y, int r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getR() {
        return r;
    }

    public boolean contain(Point point) {
        return Math.pow(x - point.getX(), 2) + Math.pow(y - point.getY(), 2) <= r * r;
    }
}

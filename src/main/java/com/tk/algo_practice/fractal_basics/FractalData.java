package com.tk.algo_practice.fractal_basics;

@SuppressWarnings("all")
public class FractalData {

    public int depth;
    public double splitAngle;

    public FractalData(int depth, double splitAngle) {
        this.depth = depth;
        this.splitAngle = splitAngle;
    }

    public FractalData(int depth) {
        this(depth, 0.0);
    }
}
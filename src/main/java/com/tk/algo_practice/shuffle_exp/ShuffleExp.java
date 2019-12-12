package com.tk.algo_practice.shuffle_exp;

public class ShuffleExp {

    private int N;
    private int n, m;

    public ShuffleExp(int N, int n, int m) {

        if (N < 0) {
            throw new IllegalArgumentException("");
        }

        if (n < m) {
            throw new IllegalArgumentException();
        }

        this.N = N;
        this.n = n;
        this.m = m;
    }

    private void run() {

        int[] arr = new int[n];
        int[] freq = new int[n];

        for (int i = 0; i < N; i++) {
            reset(arr);
            shuffle(arr);
            for (int j = 0; j < n; j++) {
                freq[j] += arr[j];
            }
        }

        for (int i = 0; i < n; i++) {
            System.out.println(i + " : " + (double) freq[i] / N);
        }
    }

    private void shuffle(int[] arr) {

        for (int i = n-1; i > 0; i--) {
            //从[0,i+1)里随机选择元素
            int x = (int) (Math.random()*(i+1));
            swap(arr, i, x);
        }
    }

    private void swap(int[] arr, int i, int x) {

        int t = arr[i];
        arr[i] = arr[x];
        arr[x] = t;
    }

    private void reset(int[] arr) {

        for (int i = 0; i < m; i++) {
            arr[i] = 1;
        }
        for (int i = m; i < n; i++) {
            arr[i] = 0;
        }
    }

    public static void main(String[] args) {

        int N = 10000000;
        int n = 10;
        int m = 5;
        ShuffleExp exp = new ShuffleExp(N, n, m);
        exp.run();
    }
}

package com.tk.algo_practice.merge_sort;

import java.awt.*;
import java.util.Arrays;

@SuppressWarnings("all")
public class AlgoVisualizer {

    //创建数据
    private static int DELAY = 40;
    private MergeSortData data;      //数据
    private AlgoFrame frame;  //视图

    public AlgoVisualizer(int sceneWidth, int sceneHeight, int N, MergeSortData.Type dataType) {

        //初始化数据
        data = new MergeSortData(N, sceneHeight, dataType);

        //初始化视图
        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("MergeSort", sceneWidth, sceneHeight);
            new Thread(() -> {
               run();
            }).start();

        });
    }

    public AlgoVisualizer(int sceneWidth, int sceneHeight, int N) {
        this(sceneWidth, sceneHeight, N, MergeSortData.Type.Default);
    }

    private void run() {
        // 编写动画逻辑
        setData(-1, -1, -1);

//        mergeSort(0, data.N() - 1);
        for (int sz = 1; sz < data.N(); sz *= 2) {
            for (int i = 0; i < data.N() - sz; i += sz + sz) {
                merge(i, i + sz -1, Math.min(i+sz+sz-1, data.N()-1));
            }
        }

        setData(0, data.N() - 1, data.N() - 1);
    }

    private void mergeSort(int l, int r) {

        if (l >= r)
            return;

        setData(l , r, -1);

        int mid = (l + r) / 2;
        mergeSort(l, mid);
        mergeSort(mid + 1, r);
        merge(l, mid, r);
    }

    private void merge(int l, int mid, int r) {

        int[] aux = Arrays.copyOfRange(data.numbers, l, r+1);

        int i = l, j = mid + 1;
        for (int k = l; k <= r; k++) {

            if (i > mid) {
                data.numbers[k] = aux[j - l];
                j++;
            } else if (j > r) {
                data.numbers[k] = aux[i - l];
                i++;
            } else if (aux[i - l] < aux[j - l]) {
                data.numbers[k] = aux[i - l];
                i++;
            } else {
                data.numbers[k] = aux[j - l];
                j++;
            }

            setData(l, r, k);
        }
    }

    private void setData(int l, int r, int mergeIndex) {

        data.l = l;
        data.r = r;
        data.mergeIndex = mergeIndex;

        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }

    public static void main(String[] args) {

        int sceneWidth = 800;
        int sceneHeight = 800;
        int N = 100;

        AlgoVisualizer visualizer = new AlgoVisualizer(sceneWidth, sceneHeight, N);
    }
}

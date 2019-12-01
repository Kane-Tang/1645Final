package rk4;

import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class Rk4WithCountDownLatch {

    public int problemSize = 8192;
    double h = 0.3154;
    public double[] y, yt, k1, k2, k3, k4, pow, yout;
    public double[][] c;
    public double totalSum = 0.0;

    public Rk4WithCountDownLatch() {
        y = new double[problemSize];
        yt = new double[problemSize];
        k1 = new double[problemSize];
        k2 = new double[problemSize];
        k3 = new double[problemSize];
        k4 = new double[problemSize];
        pow = new double[problemSize];
        yout = new double[problemSize];
        c = new double[problemSize][problemSize];

        for (int i = 0; i < problemSize; i++) {
            y[i] = i * i;
            pow[i] = i + i;
            for (int j = 0; j < problemSize; j++) {
                c[i][j] = i * i + j;
            }
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        final CountDownLatch latch1 = new CountDownLatch(n);
        final CountDownLatch latch2 = new CountDownLatch(n);
        final CountDownLatch latch3 = new CountDownLatch(n);
        final CountDownLatch latch4 = new CountDownLatch(n);
        Rk4WithCountDownLatch testRk4 = new Rk4WithCountDownLatch();
        int len = testRk4.problemSize;
        int size = len / n;
        double startTime, endTime;
        startTime=System.nanoTime()/1000000.0;
        Thread thread[] = new Thread[n];
        try {
            for (int i = 0; i < n; i++) {
                int start = i * size;
                int end = (i + 1) * size - 1;
                thread[i] = new ThreadsWorker1("thread1-" + String.valueOf(i), len, latch1, testRk4, start, end);
                thread[i].start();
            }
            latch1.await();
            for (int i = 0; i < n; i++) {
                int start = i * size;
                int end = (i + 1) * size - 1;
                thread[i] = new ThreadsWorker2("thread2-" + String.valueOf(i), len, latch2, testRk4, start, end);
                thread[i].start();
            }
            latch2.await();
            for (int i = 0; i < n; i++) {
                int start = i * size;
                int end = (i + 1) * size - 1;
                thread[i] = new ThreadsWorker3("thread3-" + String.valueOf(i), len, latch3, testRk4, start, end);
                thread[i].start();
            }
            latch3.await();
            for (int i = 0; i < n; i++) {
                int start = i * size;
                int end = (i + 1) * size - 1;
                thread[i] = new ThreadsWorker4("thread4-" + String.valueOf(i), len, latch4, testRk4, start, end);
                thread[i].start();
            }
            latch4.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < testRk4.problemSize; i++) {
            testRk4.totalSum += testRk4.yout[i];
        }
        endTime=System.nanoTime()/1000000.0;
        System.out.println("rk4 Total Sum ="+testRk4.totalSum);
        System.out.println("Interval length: "+(endTime-startTime));
    }
}


class ThreadsWorker1 extends Thread {

    private int problemSize;
    private CountDownLatch latch;
    private double h;
    private double[] y, yt, k1, k2, k3, k4, pow, yout;
    private double[][] c;
    private int start;
    private int end;

    public ThreadsWorker1(String name, int problemSize, CountDownLatch latch,
                          Rk4WithCountDownLatch testRk4, int start, int end) {
        super(name);
        this.problemSize = problemSize;
        this.latch = latch;
        this.h = testRk4.h;
        this.y = testRk4.y;
        this.yt = testRk4.yt;
        this.k1 = testRk4.k1;
        this.k2 = testRk4.k2;
        this.k3 = testRk4.k3;
        this.k4 = testRk4.k4;
        this.pow = testRk4.pow;
        this.yout = testRk4.yout;
        this.c = testRk4.c;
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        try {
            for (int i = start; i <= end; i++) {
                yt[i] = 0.0;
                for (int j = 0; j < problemSize; j++) {

                    yt[i] += c[i][j] * y[j];
                }
                k1[i] = h * (pow[i] - yt[i]);
            }
            latch.countDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


class ThreadsWorker2 extends Thread {

    private int problemSize;
    private CountDownLatch latch;
    private double h;
    private double[] y, yt, k1, k2, k3, k4, pow, yout;
    private double[][] c;
    private int start;
    private int end;

    public ThreadsWorker2(String name, int problemSize, CountDownLatch latch,
                          Rk4WithCountDownLatch testRk4, int start, int end) {
        super(name);
        this.problemSize = problemSize;
        this.latch = latch;
        this.h = testRk4.h;
        this.y = testRk4.y;
        this.yt = testRk4.yt;
        this.k1 = testRk4.k1;
        this.k2 = testRk4.k2;
        this.k3 = testRk4.k3;
        this.k4 = testRk4.k4;
        this.pow = testRk4.pow;
        this.yout = testRk4.yout;
        this.c = testRk4.c;
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        try {
            for (int i = start; i <= end; i++) {
                yt[i] = 0.0;
                for (int j = 0; j < problemSize; j++) {
                    yt[i] += c[i][j] * (y[j] + 0.5 * k1[j]);
                }
                k2[i] = h * (pow[i] - yt[i]);
            }
            latch.countDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


class ThreadsWorker3 extends Thread {

    private int problemSize;
    private CountDownLatch latch;
    private double h;
    private double[] y, yt, k1, k2, k3, k4, pow, yout;
    private double[][] c;
    private int start;
    private int end;

    public ThreadsWorker3(String name, int problemSize, CountDownLatch latch,
                          Rk4WithCountDownLatch testRk4, int start, int end) {
        super(name);
        this.problemSize = problemSize;
        this.latch = latch;
        this.h = testRk4.h;
        this.y = testRk4.y;
        this.yt = testRk4.yt;
        this.k1 = testRk4.k1;
        this.k2 = testRk4.k2;
        this.k3 = testRk4.k3;
        this.k4 = testRk4.k4;
        this.pow = testRk4.pow;
        this.yout = testRk4.yout;
        this.c = testRk4.c;
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        try {
            for (int i = start; i <= end; i++) {
                yt[i] = 0.0;
                for (int j = 0; j < problemSize; j++) {
                    yt[i] += c[i][j] * (y[j] + 0.5 * k2[j]);
                }
                k3[i] = h * (pow[i] - yt[i]);
            }
            latch.countDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


class ThreadsWorker4 extends Thread {

    private int problemSize;
    private CountDownLatch latch;
    private double h;
    private double[] y, yt, k1, k2, k3, k4, pow, yout;
    private double[][] c;
    private int start;
    private int end;

    public ThreadsWorker4(String name, int problemSize, CountDownLatch latch,
                          Rk4WithCountDownLatch testRk4, int start, int end) {
        super(name);
        this.problemSize = problemSize;
        this.latch = latch;
        this.h = testRk4.h;
        this.y = testRk4.y;
        this.yt = testRk4.yt;
        this.k1 = testRk4.k1;
        this.k2 = testRk4.k2;
        this.k3 = testRk4.k3;
        this.k4 = testRk4.k4;
        this.pow = testRk4.pow;
        this.yout = testRk4.yout;
        this.c = testRk4.c;
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        try {
            for (int i = start; i <= end; i++) {
                yt[i] = 0.0;
                for (int j = 0; j < problemSize; j++) {
                    yt[i] += c[i][j] * (y[j] + k3[j]);
                }
                k4[i] = h * (pow[i] - yt[i]);
                yout[i] = y[i] + (k1[i] + 2 * k2[i] + 2 * k3[i] + k4[i]) / 6.0;
            }
            latch.countDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
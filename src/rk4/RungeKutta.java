package rk4;

import java.util.concurrent.CyclicBarrier;

public class RungeKutta implements Runnable {
    private int start ;
    private int end ;
    private int problemSize;
    private CyclicBarrier barrier;
    private double h;
    private double[] y, yt, k1, k2, k3, k4, pow, yout;
    private double[][] c;
    private double mysum;

    
    public RungeKutta(int start, int end, int problemSize, CyclicBarrier barrier, double h, double[] y, double[] yt, double[] k1, double[] k2, double[] k3, double[] k4, double[] pow, double[] yout, double[][] c) {
        this.start = start;
        this.end = end;
        this.problemSize = problemSize;
        this.barrier = barrier;
        this.h = h;
        this.y = y;
        this.yt = yt;
        this.k1 = k1;
        this.k2 = k2;
        this.k3 = k3;
        this.k4 = k4;
        this.pow = pow;
        this.yout = yout;
        this.c = c;
        mysum=0;
    }

    public double getMysum() {
        return mysum;
    }

    @Override
    public void run() {
        try {
            for (int i = start; i <=end; i++) {
                yt[i] = 0.0;
                for (int j = 0; j < problemSize; j++) {
                    yt[i] += c[i][j]*y[j];
                }
                k1[i] = h*(pow[i]-yt[i]);
            }
            barrier.await();
//            System.out.println("*****"+start+"first loop finished");
            for (int i = start; i <=end; i++) {
                yt[i] = 0.0;
                for (int j = 0; j < problemSize; j++) {
                    yt[i] += c[i][j]*(y[j]+0.5*k1[j]);
                }
                k2[i] = h*(pow[i]-yt[i]);
            }
            barrier.await();
//            System.out.println("*****"+start+"second loop finished");
            for (int i = start; i <=end; i++) {
                yt[i] = 0.0;
                for (int j = 0; j < problemSize; j++) {
                    yt[i] += c[i][j]*(y[j]+0.5*k2[j]);
                }
                k3[i] = h*(pow[i]-yt[i]);
            }
            barrier.await();
//            System.out.println("*****"+start+"third loop finished");
            for (int i =start; i <=end; i++) {
                yt[i]=0.0;
                for (int j = 0; j < problemSize; j++) {
                    yt[i] += c[i][j]*(y[j]+k3[j]);
                }
                k4[i] = h*(pow[i]-yt[i]);

                yout[i] = y[i] + (k1[i] + 2*k2[i] + 2*k3[i] + k4[i])/6.0;
                mysum+=yout[i];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

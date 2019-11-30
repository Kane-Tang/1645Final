package rk4;

import java.util.Scanner;
import java.util.concurrent.CyclicBarrier;

public class rk4{
	public void serial(int problemSize, double h, double[] y, double[] yt, double[] k1, double[] k2, double[] k3, double[] k4, double[] pow, double[] yout, double[][] c, double totalSum) {
		for (int i = 0; i < problemSize; i++) { 
			yt[i] = 0.0;
			for (int j = 0; j < problemSize; j++) {
				yt[i] += c[i][j]*y[j];
			}
			k1[i] = h*(pow[i]-yt[i]);
		}

		for (int i = 0; i < problemSize; i++) {
			yt[i] = 0.0;
			for (int j = 0; j < problemSize; j++) {
				yt[i] += c[i][j]*(y[j]+0.5*k1[j]);
			}
			k2[i] = h*(pow[i]-yt[i]);
		}

		for (int i = 0; i < problemSize; i++) {
			yt[i] = 0.0;
			for (int j = 0; j < problemSize; j++) {
				yt[i] += c[i][j]*(y[j]+0.5*k2[j]);
			}
			k3[i] = h*(pow[i]-yt[i]);
		}

		for (int i =0; i < problemSize; i++) {
			yt[i]=0.0;
			for (int j = 0; j < problemSize; j++) {
				yt[i] += c[i][j]*(y[j]+k3[j]);
			}
			k4[i] = h*(pow[i]-yt[i]);

			yout[i] = y[i] + (k1[i] + 2*k2[i] + 2*k3[i] + k4[i])/6.0;
			totalSum+=yout[i];
		}
		System.out.println("Total Sum ="+totalSum);
	}
	
	public static void main(String[] args) throws Exception {
		int problemSize = 8192;
		double h=0.3154;
		double[] y, yt, k1, k2, k3, k4, pow, yout;
		double[][] c;
		double totalSum = 0.0;
		double startTime, endTime;
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
			y[i]=i*i;
			pow[i]=i+i;
			for (int j = 0; j < problemSize; j++){
				c[i][j]=i*i+j;
			}
		}
		Scanner in = new Scanner(System.in);
		int threadCount = in.nextInt();
		int localSize=problemSize/threadCount;
		startTime=System.nanoTime()/1000000.0;
		Thread thread[]=new Thread[threadCount];
		CyclicBarrier barrier=new CyclicBarrier(threadCount);
		RungeKutta[] rk4=new RungeKutta[threadCount];
		for(int i=0; i<threadCount; i++) {
			rk4[i]= new RungeKutta(i*localSize, (i+1)*localSize-1, problemSize, barrier, h, y, yt, k1, k2, k3, k4, pow, yout, c);
			thread[i]= new Thread(rk4[i]);
			thread[i].start();
		}
		for(int i=0; i<threadCount; i++) {
			thread[i].join();
		}
		endTime=System.nanoTime()/1000000.0;
		for(int i=0; i<threadCount; i++) {
			totalSum+=rk4[i].getMysum();
		}
		System.out.println("Total Sum ="+totalSum);
		System.out.println("Interval length: "+(endTime-startTime));
		//rk4 testRk4 = new rk4();
		//testRk4.serial(problemSize, h, y, yt, k1, k2, k3, k4, pow, yout, c, totalSum);
		
	}
	
}


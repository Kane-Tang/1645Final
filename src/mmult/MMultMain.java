package mmult;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MMultMain {
	public void test(int n) throws InterruptedException {

		int NROW = 1024;
		int a[][] = new int[NROW][NROW];
		int b[][] = new int[NROW][NROW];
		int c[][] = new int[NROW][NROW];
		double startTime, endTime;
		double totalSum = 0;
		for (int i = 0; i < NROW; i++) {
			for (int j = 0; j < NROW; j++) {
				a[i][j] = i * NROW + j;
				b[i][j] = j * NROW + i;
				c[i][j] = 0;
			}
		}

		// implements by implements Runnable
		int localSize = NROW / n;
		startTime = System.nanoTime() / 1000000.0;
		ExecutorService threadPool = Executors.newFixedThreadPool(n);
		for (int i = 0; i < n; i++) {
			threadPool.submit(new MMult(a, b, c, i * localSize, (i + 1) * localSize - 1));
		}
		threadPool.shutdown();
		threadPool.awaitTermination(1, TimeUnit.DAYS);
		endTime = System.nanoTime() / 1000000.0;
		for (int i = 0; i < NROW; i++) {
			for (int j = 0; j < NROW; j++) {
				totalSum += (double) c[i][j];
			}
		}
		System.out.println("Matrix Multiplication totalSum = " + totalSum);
		System.out.println("Interval length for Runnable idea with threadPool: " + (endTime - startTime));
		
		for (int i = 0; i < NROW; i++) {
			for (int j = 0; j < NROW; j++) {
				a[i][j] = i * NROW + j;
				b[i][j] = j * NROW + i;
				c[i][j] = 0;
			}
		}

		// implements by extends thread
		//int localSize = NROW / n;
		startTime = System.nanoTime() / 1000000.0;
		ExecutorService threadPool2 = Executors.newFixedThreadPool(n);
		for (int i = 0; i < n; i++) {
			threadPool2.submit(new Matrix(a, b, c, i * localSize, (i + 1) * localSize - 1, NROW));
		}
		threadPool2.shutdown();
		threadPool2.awaitTermination(1, TimeUnit.DAYS);
		endTime = System.nanoTime() / 1000000.0;
		totalSum = 0.0;
		for (int i = 0; i < NROW; i++) {
			for (int j = 0; j < NROW; j++) {
				totalSum += (double) c[i][j];
			}
		}
		System.out.println("Matirx Multiplication totalSum = " + totalSum);
		System.out.println("Interval length for Thread idea with threadPool: " + (endTime - startTime));
	}

	public static void main(String[] args) throws InterruptedException {
		while (true) {
			Scanner in = new Scanner(System.in);
			int n = in.nextInt();
			if (n == 0) {
				break;
			}
			MMultMain test = new MMultMain();
			test.test(n);
		}
	}
}

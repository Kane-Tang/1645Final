package multiThread;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MMult implements Runnable{

	//private int test;
	private int[][] a;
	private int[][] b;
	private int[][] c;
	private int row;
	private int col;
	public MMult(int[][] c, int row, int col) {
		// TODO Auto-generated constructor stub
		this.c = c;
		this.row = row;
		this.col = col;
		a = new int[100][100];
		b = new int[100][100];
		for(int i=0;i<100;i++) {
			for(int j=0;j<100;j++) {
				a[i][j] = i+j;
				b[i][j] = i*j;
			}
		}
	}

	@Override
	public void run() {
		for(int i=0;i<100;i++) {
			c[row][col] += a[row][i]*b[i][col];
		}
	}
}

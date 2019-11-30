package mmult;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MMult implements Runnable{

	
	private int[][] a;
	private int[][] b;
	private int[][] c;
	private int row;
	private int col;
	public MMult(int[][] a, int[][] b, int[][] c, int row, int col) {
		// TODO Auto-generated constructor stub
		this.a = a;
		this.b = b;
		this.c = c;
		this.row = row;
		this.col = col;
	}

	@Override
	public void run() {
		int NROW = 1024;
		for(int i=0;i<NROW;i++) {
			c[row][col] += a[row][i]*b[i][col];
		}
	}
}

package mmult;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MMult implements Runnable{

	
	private int[][] a;
	private int[][] b;
	private int[][] c;
	private int start;
	private int end;
	public MMult(int[][] a, int[][] b, int[][] c, int start, int end) {
		// TODO Auto-generated constructor stub
		this.a = a;
		this.b = b;
		this.c = c;
		this.start = start;
		this.end = end;
	}

	@Override
	public void run() {
		int NROW = 1024;
		for(int i=start; i<=end; i++) {
            for(int j=0; j<NROW; j++) {
                for(int k=0; k<NROW; k++) {
                    c[i][j]+=a[i][k]*b[k][j];
                }
            }
        }
	}
}

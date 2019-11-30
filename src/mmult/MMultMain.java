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
		for(int i=0;i<NROW;i++)
        {
            for(int j=0;j<NROW;j++)
            {
                a[i][j]= i*NROW+j;
                b[i][j]= j*NROW+i;
                c[i][j]= 0;
            }
        }
		startTime=System.nanoTime()/1000000.0;
		ExecutorService threadPool = Executors.newFixedThreadPool(n);
		for (int i = 0; i < NROW; i++) {
			for (int j = 0; j < NROW; j++) {
				threadPool.submit(new MMult(a, b, c, i, j));
			}
		}
		threadPool.shutdown();
		threadPool.awaitTermination(1, TimeUnit.DAYS);
		endTime=System.nanoTime()/1000000.0;
        for(int i=0;i<NROW;i++)
        {
            for(int j=0;j<NROW;j++)
            {
                totalSum+=(double)c[i][j];
            }
        }
        System.out.println("totalSum = " + totalSum);
        System.out.println("Interval length: "+(endTime-startTime));
	}

	public static void main(String[] args) throws InterruptedException {
		while(true) {
			Scanner in = new Scanner(System.in);
			int n = in.nextInt();
			if(n == 0) {
				break;
			}
			MMultMain test = new MMultMain();
			test.test(n);
		}
	}
}

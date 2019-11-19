package multiThread;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {

	private int[][] c;
	public Test() {
		// TODO Auto-generated constructor stub
		c = new int[100][100];
	}
	public void test(int n) {
		ExecutorService threadPool = Executors.newFixedThreadPool(n);
		for(int i=0;i<100;i++) {
			for(int j=0;j<100;j++) {
				threadPool.submit(new MMult(c, i, j));
			}
		}
	}
	
	public void print() {
		for(int i=0;i<100;i++) {
			for(int j=0;j<100;j++) {
				System.out.print(c[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		Test t = new Test();
		t.test(n);
		t.print();
	}
}

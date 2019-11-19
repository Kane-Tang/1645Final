package multiThread;

public class MMult implements Runnable{

	//private int test;
	private int[][] a;
	private int[][] b;
	private int[][] c;
	private int i;
	private int j;
	public MMult() {
		// TODO Auto-generated constructor stub
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
			
		}
	}
}

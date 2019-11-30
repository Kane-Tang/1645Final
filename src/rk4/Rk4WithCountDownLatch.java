package rk4;

public class rk4Thread {
	
	public static void main(String[] args) {
		
	}
}


class initRk4Array implements Runnable{
	
	private int problemSize = 100;
	private double h=0.3154;
	private double[] y, yt, k1, k2, k3, k4, pow, yout;
	private double[][] c;
	private double totalSum = 0.0;
	
	@Override
	public void run() {
		for (int i = 0; i < problemSize; i++) {
			y[i] = i*i;
			pow[i] = i+i;
			for (int j = 0; j < problemSize; j++) {
				c[i][j] = i*i+j;
			}
		}
	}
}

class k1 implements Runnable{
	
	private int problemSize = 100;

	@Override
	public void run() {
		
		
	}
}
package rk4;

public class rk4{
	
	public int problemSize = 8192;
	double h=0.3154;
	public double[] y, yt, k1, k2, k3, k4, pow, yout;
	public double[][] c;
	public double totalSum = 0.0;
	
	public rk4() {
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
		
	}
	
	public static void main(String[] args) {
		rk4 testRk4 = new rk4();
		System.out.println(testRk4.totalSum);
		
	}
	
}


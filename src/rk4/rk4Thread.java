package rk4;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import mmult.MMult;

public class rk4Thread {
	
	public static void main(String args[]) { 
		rk4Thread d2 = new rk4Thread(); 
		double x0 = 0, y = 1, x = 2, h = 0.2; 
		
		System.out.println("\nThe value of y at x is : " + d2.rungeKutta(x0, y, x, h)); 
	} 

	
	double dydx(double x, double y) 
	{ 
		return ((x - y) / 2); 
	} 
	
	double rungeKutta(double x0, double y0, double x, double h) 
	{ 
		rk4Thread d1 = new rk4Thread(); 
		int n = (int)((x - x0) / h); 

		double k1, k2, k3, k4, k5; 

		double y = y0; 
		for (int i = 1; i <= n; i++) 
		{ 
			k1 = h * (d1.dydx(x0, y)); 
			k2 = h * (d1.dydx(x0 + 0.5 * h, y + 0.5 * k1)); 
			k3 = h * (d1.dydx(x0 + 0.5 * h, y + 0.5 * k2)); 
			k4 = h * (d1.dydx(x0 + h, y + k3)); 

			y = y + (1.0 / 6.0) * (k1 + 2 * k2 + 2 * k3 + k4); 
			
			x0 = x0 + h; 
		} 
		return y; 
	} 

	
	public void test(int n) throws InterruptedException {
		ExecutorService threadPool = Executors.newFixedThreadPool(n);
		for(int i=0;i<100;i++) {
			for(int j=0;j<100;j++) {
				threadPool.submit(new MMult(c, i, j));
			}
		}
		threadPool.shutdown();
		threadPool.awaitTermination(1, TimeUnit.DAYS);
	}
	
}

package mmult;

import java.util.Scanner;

public class Matrixmul {
    public static void main(String[] args) throws InterruptedException {
        int NROW=1024;
        int a[][] = new int[NROW][NROW];
        int b[][] = new int[NROW][NROW];
        int c[][] = new int[NROW][NROW];
        double startTime, endTime;
        double totalSum=0;
        for(int i=0;i<NROW;i++)
        {
            for(int j=0;j<NROW;j++)
            {
                a[i][j]= i*NROW+j;
                b[i][j]= j*NROW+i;
                c[i][j]= 0;
            }
        }
        Scanner in = new Scanner(System.in);
        int threadCount = in.nextInt();
        int localSize=NROW/threadCount;
        startTime=System.nanoTime()/1000000.0;
        Thread thread[]=new Thread[threadCount];
        Matrix matrix[]=new Matrix[threadCount];
        for(int i=0; i<threadCount; i++) {
            matrix[i]= new Matrix(a, b, c, i*localSize, (i+1)*localSize-1, NROW);
            thread[i]= new Thread(matrix[i]);
            thread[i].start();
        }
        for(int i=0; i<threadCount; i++) {
            thread[i].join();
        }
        endTime=System.nanoTime()/1000000.0;
        for(int i=0;i<NROW;i++)
        {
            for(int j=0;j<NROW;j++)
            {
                totalSum+=(double)c[i][j];
            }
        }
        System.out.println("Matrix Multiplication Total Sum ="+totalSum);
        System.out.println("Interval length for Thread idea: "+(endTime-startTime));
//        Matrixmul m=new Matrixmul();
//        m.serial(a, b, c, NROW);
    }
    public void serial(int a[][], int b[][], int c[][], int NROW) {
        for(int i=0;i<NROW;i++)
        {
            for(int j=0; j<NROW; j++)
            {
                for(int k=0;k<NROW;k++)
                {
                    c[i][j]+=a[i][k]*b[k][j];
                }
            }
        }
        double totalSum=0.0;
        for(int i=0;i<NROW;i++)
        {
            for(int j=0;j<NROW;j++)
            {
                totalSum+=(double)c[i][j];
            }
        }
        System.out.println("Total Sum ="+totalSum);
    }
}

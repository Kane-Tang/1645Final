package mmult;

public class Matrix extends Thread {

    private int a[][];
    private int b[][];
    private int c[][];
    private int start ;
    private int end ;
    private int NROW;

    public Matrix(int[][] a, int[][] b, int[][] c, int start, int end, int NROW) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.start = start;
        this.end = end;
        this.NROW=NROW;
    }

    @Override
    public void run() {
        for(int i=start; i<=end; i++) {
            for(int j=0; j<NROW; j++) {
                for(int k=0; k<NROW; k++) {
                    c[i][j]+=a[i][k]*b[k][j];
                }
            }
        }
    }
}

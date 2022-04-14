public class Ex2 {
    public static int[][] summation(int[][] A, int[][] B) {
        int[][] C = new int[A.length][A.length];
        for (int i=0;i<=A.length-1;i++) {
            for (int j=0;j<=A.length-1;j++) {
                C[i][j] = A[i][j]+B[i][j];
            }
        }
        return(C);
    }
    public static int[][] product(int[][] A, int[][] B) {
        int[][] C = new int[A.length][A.length];
        for (int i=0;i<=A.length-1;i++) {
            for (int j=0;j<=A.length-1;j++) {
                for (int k=0;k<=A.length-1;k++) {
                    C[i][j] = C[i][j]+A[i][k]*B[k][j];
                }
            }
        }
        return(C);
    }
    public static void printer(int[][] M) {
        for (int i=0;i<=M.length-1;i++) {
            for (int j=0;j<=M.length-1;j++) {
                System.out.print(Integer.toString(M[i][j])+' ');
            }
            System.out.print('\n');
        }
    }
    public static void main(String[] args) {
        int[][] A = {{2,3,1},{7,1,6},{9,2,4}};
        int[][] B = {{8,5,3},{3,9,2},{2,7,3}};
        System.out.println("Matrix A is:");
        printer(A);
        System.out.println("Matrix B is:");
        printer(B);
        int[][] sum = summation(A,B);
        System.out.println("The summation is:");
        printer(sum);
        int[][] prod = product(A,B);
        System.out.println("The product is:");
        printer(prod);
    }
}
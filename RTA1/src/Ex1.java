public class Ex1 {

    public static int[] summation(int a[], int b[]) {
        int c[] = {a[0]+b[0],a[1]+b[1]};
        return(c);
    }
    public static int[] product(int a[], int b[]) {
        int c[] = {a[0]*a[1]-b[0]*b[1],a[0]*b[1]+a[1]*b[0]};
        return(c);
    }
    public static void main(String[] args) {
        int a[] = {2,5};
        int b[] = {4,-1};
        System.out.println("The numbers are: "+a[0]+'+'+a[1]+"i and "+b[0]+'+'+b[1]+'i');
        int sum[] = summation(a,b);
        System.out.println("The sum is: "+sum[0]+'+'+sum[1]+'i');
        int prod[] = product(a,b);
        System.out.println("The product is: "+prod[0]+'+'+prod[1]+'i');
    }
}
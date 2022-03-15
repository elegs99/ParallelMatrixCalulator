import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class matrixmult{
    public static int rowA;
    public static int colA;
    public static int rowB;
    public static int colB;
    public static int[][] matA;
    public static int[][] matB;
    public static int[][] matC;
    public static AtomicInteger row= new AtomicInteger(0);

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Dimensions of matrix A:");
        System.out.print("Row = ");
        rowA = sc.nextInt();
        System.out.print("Column = ");
        colA = sc.nextInt();

        System.out.println("Dimensions of matrix B:");
        System.out.print("Row = ");
        rowB = sc.nextInt();
        System.out.print("Column = ");
        colB = sc.nextInt();

        matA = new int[rowA][colA];
        matB = new int[rowB][colB];
        matC = new int[rowA][colB];

        System.out.println("Enter matrix A:");

        for (int i = 0; i < rowA; i++) 
        {
            for (int j = 0; j < colA; j++) 
            {
                matA[i][j] = sc.nextInt();
            }
        }

        System.out.println("Enter matrix B:");

        for (int i = 0; i < rowB; i++) 
        {
            for (int j = 0; j < colB; j++) 
            {
                matB[i][j] = sc.nextInt();
            }
        }

        System.out.println("\nEnter number for operation\n3: Multiply");

        int choice = sc.nextInt();

        if(choice==3)
        {
            if(colA!=rowB)
            {
                System.out.println("Matrix multiplication not possible");
                sc.close();
                return;
            }

            multiply(matA,matB,matC);
        }

        sc.close();
    }

    public static void multiply(int[][] matA, int[][] matB, int[][] matC)
    {
        //implementation 1 as discussed in meeting
        Runnable runnable = ()->{
            int i = row.get(); //i denotes row number of resultant matC
    
            for (int j = 0; j < rowA; j++)
                for (int k = 0; k < colB; k++)
                    matC[i][j] += matA[i][k] * matB[k][j];

            row.incrementAndGet();
        };

        ArrayList<Thread> threadList = new ArrayList<Thread>();

        long start = System.nanoTime();

        for(int i=0;i<rowA;i++)
        {
            threadList.add(new Thread(runnable));
            threadList.get(i).start();
        }

        for(Thread t : threadList)
        {
            try
            {
                t.join();
            }
            catch(InterruptedException e)
            {
                return;
            }
        }

        long end = System.nanoTime();

        System.out.println("\nResultant matrix:");
        for (int r = 0; r < rowA; r++) 
        {
            for (int c = 0; c < colB; c++) 
            {
                System.out.print(matC[r][c]);
                System.out.print(" ");
            }
            System.out.println();
        }

        System.out.println("Executed in "+String.valueOf((end-start)/1000000)+" milliseconds");
    }
}
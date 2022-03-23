import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class matrixmult {
    public static int rowA;
    public static int colA;
    public static int rowB;
    public static int colB;
    public static int[][] matA;
    public static int[][] matB;
    public static int[][] matC; // used for storing results
    public static AtomicInteger row = new AtomicInteger(0);

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\nEnter number for operation\n1: Transposition\n3: Multiply\n5: Exit");

            int choice = sc.nextInt();

            if (choice != 5) {
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

                for (int i = 0; i < rowA; i++) {
                    for (int j = 0; j < colA; j++) {
                        matA[i][j] = sc.nextInt();
                    }
                }

                System.out.println("Enter matrix B:");

                for (int i = 0; i < rowB; i++) {
                    for (int j = 0; j < colB; j++) {
                        matB[i][j] = sc.nextInt();
                    }
                }

                if (choice == 1) { // transposition
                    // declares runnable and threadpool
                    matrixtranspose transposition = new matrixtranspose();
                    ThreadPool threadPool = new ThreadPool(rowA);
                    matC = new int[rowA][colA];

                    threadPool.run(transposition);

                    colB = colA;

                    // printMatrix();
                }

                else if (choice == 3) { // multiplication
                    if (colA != rowB) {
                        System.out.println("Matrix multiplication not possible");
                    } else {
                        multiply(matA, matB, matC);
                    }
                }
            } else {
                break;
            }
        }

        sc.close();
    }

    public static void multiply(int[][] matA, int[][] matB, int[][] matC) {
        // implementation 1 as discussed in meeting
        Runnable runnable = () -> {
            int i = row.get(); // i denotes row number of resultant matC

            for (int j = 0; j < colB; j++)
                for (int k = 0; k < rowB; k++)
                    matC[i][j] += matA[i][k] * matB[k][j];

            row.incrementAndGet();
        };

        ArrayList<Thread> threadList = new ArrayList<Thread>();

        long start = System.nanoTime();

        for (int i = 0; i < rowA; i++) {
            threadList.add(new Thread(runnable));
            threadList.get(i).start();
        }

        for (Thread t : threadList) {
            try {
                t.join();
            } catch (InterruptedException e) {
                return;
            }
        }

        long end = System.nanoTime();
        printMatrix();
        System.out.println("Executed in " + String.valueOf((end - start) / 1000000) + " milliseconds");
    }

    public static void printMatrix() {
        // it's written assuming multiplication, just make sure to set your rows and
        // columns as needed before calling
        System.out.println("\nResultant matrix:");
        for (int r = 0; r < rowA; r++) {
            for (int c = 0; c < colB; c++) {
                System.out.print(matC[r][c]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}
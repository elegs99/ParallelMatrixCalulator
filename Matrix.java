import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

class Matrix {
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
        Random rand = new Random();
        while (true) {
            System.out.println("\nEnter number for operation\n1: Transposition\n3: Multiply\n5: Exit");

            int choice = sc.nextInt();

            if (choice < 5 && choice > 0) {
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

                // System.out.println("Enter matrix A:");

                for (int i = 0; i < rowA; i++) {
                    for (int j = 0; j < colA; j++) {
                        // matA[i][j] = sc.nextInt();
                        matA[i][j] = rand.nextInt(100);
                    }
                }

                // System.out.println("Enter matrix B:");

                for (int i = 0; i < rowB; i++) {
                    for (int j = 0; j < colB; j++) {
                        // matB[i][j] = sc.nextInt();
                        matB[i][j] = rand.nextInt(100);
                    }
                }

                if (choice == 1) { // transposition
                    // declares runnable and threadpool
                    MatrixTranspose transposition = new MatrixTranspose();
                    ThreadPool threadPool = new ThreadPool(rowA);
                    matC = new int[rowA][colA];

                    threadPool.run(transposition);
                    colB = colA;

                    SeqMatrixTranspose seqT = new SeqMatrixTranspose();
                    Thread t = new Thread(seqT);
                    t.start();

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
        ThreadPool threadPool = new ThreadPool(rowA);
        MatrixMultiply runnable = new MatrixMultiply();
        threadPool.run(runnable);

        SeqMatrixMultiply seqMM = new SeqMatrixMultiply();
        Thread t = new Thread(seqMM);
        t.start();
        // printMatrix();
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
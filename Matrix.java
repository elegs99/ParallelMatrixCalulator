import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

class Matrix {
    public static int rowA;
    public static int colA;
    public static int rowB;
    public static int colB;
    public static int blockSize;
    public static int[][] matA;
    public static int[][] matB;
    public static int[][] matC; // used for storing results
    public static int[][] matS; // used for storing results of seqeuntial operations
    public static AtomicInteger row = new AtomicInteger(0);

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random rand = new Random();
        while (true) {
            System.out.println(
                    "\nEnter number for operation\n1: Transposition\n2: Multiply\n3: Add\n4: Subtract\n5: Exit");

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
                    int nThreads = 1; // variable # of threads
                    blockSize = colA / nThreads; // declares size of submatrix

                    MatrixTranspose transposition = new MatrixTranspose();
                    ThreadPool threadPool = new ThreadPool(nThreads);
                    matC = new int[rowA][colA];
                    matS = new int[rowA][colA];

                    threadPool.run(transposition);
                    colB = colA;

                    SeqMatrixTranspose seqT = new SeqMatrixTranspose();
                    Thread t = new Thread(seqT);
                    t.start();

                }

                else if (choice == 2) { // multiplication
                    if (colA != rowB) {
                        System.out.println("Matrix multiplication not possible");
                    } else {
                        multiply(matA, matB, matC);
                    }
                }

                else if (choice == 3) { // add
                    if (rowA != rowB || colA != colB) {
                        System.out.println("Matrix addition not possible. Dimensions do not match");
                    } else {
                        MatrixAdd add = new MatrixAdd();
                        ThreadPool threadPool = new ThreadPool(rowA);
                        matC = new int[rowA][colA];
                        threadPool.run(add);
                        printMatrix();
                        SeqMatrixAdd seqMA = new SeqMatrixAdd();
                        Thread t = new Thread(seqMA);
                        t.start();
                    }
                }

                else if (choice == 4) { // subtract
                    if (rowA != rowB || colA != colB) {
                        System.out.println("Matrix subtraction not possible. Dimensions do not match");
                    } else {
                        MatrixSubtract sub = new MatrixSubtract();
                        ThreadPool threadPool = new ThreadPool(rowA);
                        matC = new int[rowA][colA];
                        threadPool.run(sub);
                        printMatrix();
                        SeqMatrixSubtract seqMS = new SeqMatrixSubtract();
                        Thread t = new Thread(seqMS);
                        t.start();
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

        System.out.println("\noriginal matrix:");
        for (int r = 0; r < rowA; r++) {
            for (int c = 0; c < colB; c++) {
                System.out.print(matA[r][c]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    public static int[][] parseMat(String input) {
        char c;
        int row = 0, col = 0, j = 0;
        int countRow = 0, countCol = 0; // used to count rows and columns
        int[] arr = new int[input.length()];

        for (int i = 0; i < input.length(); i++) // let's parse
        {
            c = input.charAt(i);
            if (c == '[' && countRow == 0) { // 1st character
                countRow++;

            } else if (c == '[' && countRow == 1) { // 2nd and other start row matrix character
                countRow++;
                row++;

            } else if (c == ' ' || c == ',') { // we don't want these
                ;

            } else if (((int) c) >= 48 && ((int) c) <= 57) {
                arr[j] = ((int) c) - 48; // ASCII, everyone's favorite
                countCol++; // each number is a new length of columns
                j++;

            } else if (c == ']' && countRow == 2) {
                countRow--;
                col = countCol;
                countCol = 0;

            }
        }

        int[][] retval = new int[row][col];
        int z = 0;
        for (int i = 0; i < row; i++) {
            for (int k = 0; k < col; k++) {
                retval[i][k] = arr[z];
                z++; // used to go thru original array.
            }
        }

        return retval;

    }
}

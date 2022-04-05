public class SeqMatrixSubtract implements Runnable {

    @Override
    public void run() {
        long start = System.nanoTime();

        for (int i = 0; i < Matrix.rowA; i++) {
            for (int j = 0; j < Matrix.colA; j++) {
                    Matrix.matC[i][j] = Matrix.matA[i][j] - Matrix.matB[i][j];
            }
        }
        Matrix.printMatrix();
        long end = System.nanoTime();
        System.out.println("Executed in sequential in: " + String.valueOf((end - start) / 1000000) + " milliseconds");
    }
}

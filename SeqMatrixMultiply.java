public class SeqMatrixMultiply implements Runnable {

    @Override
    public void run() {
        long start = System.nanoTime();

        for (int i = 0; i < Matrix.rowA; i++) {
            for (int j = 0; j < Matrix.colB; j++) {
                for (int z = 0; z < Matrix.colB; z++) {
                    Matrix.matC[i][j] += Matrix.matA[i][z] * Matrix.matB[z][j];
                }
            }
        }

        long end = System.nanoTime();
        System.out.println("Executed in sequential in: " + String.valueOf((end - start) / 1000000) + " milliseconds");
    }
}

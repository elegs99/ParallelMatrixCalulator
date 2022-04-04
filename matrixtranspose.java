public class MatrixTranspose implements Runnable {

    // ThreadLocal<Integer> tRow = new ThreadLocal<Integer>();

    @Override
    public void run() {
        int j = Matrix.row.getAndAdd(Matrix.blockSize); // each thread will get a submatrix
        int rBound = j + Matrix.blockSize;

        for (int i = 0; i < rBound; i++) {
            for (j = 0; j < Matrix.rowA; j++) {
                Matrix.matC[i][j] = Matrix.matA[j][i];
            }
        }
    }

}

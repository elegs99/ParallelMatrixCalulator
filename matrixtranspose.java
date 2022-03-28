public class MatrixTranspose implements Runnable {

    // ThreadLocal<Integer> tRow = new ThreadLocal<Integer>();

    @Override
    public void run() {
        int j = (Matrix.row.getAndIncrement()); // sets the threads row

        for (int i = 0; i < Matrix.rowA; i++) {
            Matrix.matC[i][j] = Matrix.matA[j][i];
        }
    }

}

public class MatrixTranspose implements Runnable {

    ThreadLocal<Integer> tRow = new ThreadLocal<Integer>();

    @Override
    public void run() {
        tRow.set(matrixmult.row.getAndIncrement()); // sets the threads row

        for (int i = 0; i < matrixmult.rowA; i++) {
            matrixmult.matC[i][tRow.get()] = matrixmult.matA[tRow.get()][i];
        }
    }

}

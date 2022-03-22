public class matrixtranspose implements Runnable {

    ThreadLocal<Integer> row = new ThreadLocal<Integer>();

    @Override
    public void run() {
        row.set(matrixmult.row.getAndIncrement()); // sets the threads row

        for (int j = 0; j < matrixmult.colB; j++) {
            matrixmult.matC[j][row.get()] = matrixmult.matA[row.get()][j];
        }
    }

}

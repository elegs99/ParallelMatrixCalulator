public class MatrixTranspose implements Runnable {

    // ThreadLocal<Integer> tRow = new ThreadLocal<Integer>();

    @Override
    public void run() {
        int j = calcGUI.row.getAndAdd(calcGUI.blockSize); // each thread will get a submatrix
        int rBound = j + calcGUI.blockSize;

        for (int i = 0; i < rBound; i++) {
            for (j = 0; j < calcGUI.rowA; j++) {
            	calcGUI.answer[i][j] = calcGUI.matA[j][i];
            }
        }
    }

}

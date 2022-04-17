public class MatrixMultiply implements Runnable {

    @Override
    public void run() {
        int i = calcGUI.row.getAndIncrement(); // i denotes row number of resultant matC

        for (int j = 0; j < calcGUI.colB; j++)
            for (int k = 0; k < calcGUI.rowB; k++)
                calcGUI.answer[i][j] += calcGUI.matA[i][k] * calcGUI.matB[k][j];
    }
}

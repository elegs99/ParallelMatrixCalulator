public class MatrixSubtract implements Runnable {

        @Override
        public void run() {
            int i = Matrix.row.getAndIncrement(); // i denotes row number of resultant matC

            for (int j = 0; j < Matrix.colA; j++)
                    Matrix.matC[i][j] = Matrix.matA[i][j] - Matrix.matB[i][j];

        }

    }

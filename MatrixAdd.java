public class MatrixAdd implements Runnable {

      @Override
      public void run() {
          int i = Matrix.row.getAndIncrement(); // i denotes row number of resultant matC

          for (int j = 0; j < Matrix.colB; j++)
              for (int k = 0; k < Matrix.rowB; k++)
                  Matrix.matC[i][j] += Matrix.matA[i][k] + Matrix.matB[k][j];

      }

  }

public class MatrixAdd implements Runnable {

      @Override
      public void run() {
          int i = calcGUI.row.getAndIncrement(); // i denotes row number of resultant matC

          for (int j = 0; j < calcGUI.colA; j++)
                  calcGUI.answer[i][j] = calcGUI.matA[i][j] + calcGUI.matB[i][j];
      }
  }

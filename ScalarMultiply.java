public class ScalarMultiply implements Runnable
{
   int scalar;
   ScalarMultiply(int num)
   {
       this.scalar = num;
   }
   @Override
   public void run()
   {
       int j = calcGUI.row.getAndAdd(calcGUI.blockSize); // each thread will get a submatrix
       int rBound = j + calcGUI.blockSize;

       for (int i = 0; i < rBound; i++) {
           for (j = 0; j < calcGUI.rowA; j++) {
             calcGUI.answer[j][i] = scalar * calcGUI.matA[j][i];
           }
       }
   }
}

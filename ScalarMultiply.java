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
        int j = Matrix.row.getAndAdd(Matrix.blockSize); // each thread will get a submatrix
        int rBound = j + Matrix.blockSize;

        for (int i = 0; i < rBound; i++) {
            for (j = 0; j < Matrix.rowA; j++) {
                Matrix.matC[j][i] = scalar * Matrix.matA[j][i];
            }
        }
    }
}
public class SeqScalarMultiply implements Runnable
{
    int scalar;
    SeqScalarMultiply(int num)
    {
        this.scalar = num;
    }
    @Override
    public void run()
    {
        long start = System.nanoTime();

        for (int i = 0; i < Matrix.rowA; i++) {
            for (int j = 0; j < Matrix.colA; j++) {
                    Matrix.matS[i][j] *= scalar;
                }
            }
        

        long end = System.nanoTime();
        System.out.println("Executed in sequential in: " + String.valueOf((end - start) / 1000000) + " milliseconds");
    }
}
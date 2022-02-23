import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class matrixmult{
    public static int maxSize = 4;
    public static int maxThreads = 4;
    public static AtomicInteger row= new AtomicInteger(0);

    public static void main(String[] args) {
        Runnable runnable = ()->{
            int i = row.get(); //i denotes row number of resultant matC
   
            for (int j = 0; j < MAX; j++)
                for (int k = 0; k < MAX; k++)
                    matC[i][j] += matA[i][k] * matB[k][j];

            row.incrementAndGet();
        };

        long start = System.nanoTime();
        ArrayList<Thread> threadList = new ArrayList<Thread>();
        int matA[MAX][MAX];
        int matB[MAX][MAX];
        int matC[MAX][MAX];

        for (int i = 0; i < MAX; i++) {
            for (int j = 0; j < MAX; j++) {
                matA[i][j] = rand();
                matB[i][j] = rand();
            }
        }

        for(int i=0;i<maxThreads;i++)
        {
            threadList.add(new Thread(runnable));
            threadList.get(i).start();
        }

        for(Thread t : threadList)
        {
            try
            {
                t.join();
            }
            catch(InterruptedException e)
            {
                return;
            }
        }

        long end = System.nanoTime();

        try {
            File primes = new File("primes.txt");
            FileWriter writer = new FileWriter(primes);
            writer.write(String.valueOf(end-start)+" "+String.valueOf(count)+" "+String.valueOf(primeSum)+"\n");

            //write ans

            writer.close();
          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
    }
}
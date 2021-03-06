import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// HOW TO USE???
// ThreadPool <var_name> = new ThreadPool(rowA/colB) (just use the value that correlates to number of threads)
// Create your runnable: matrixtranspose transp = new matrixtranspose();
// submit the runnable:
// ThreadPool.run(transp);

public class ThreadPool {
    int nThreads;

    public ThreadPool(int nThreads) {
        this.nThreads = nThreads;
    }

    public void run(Runnable runnable) {
        ExecutorService threadPool = Executors.newFixedThreadPool(this.nThreads);
        long start = System.nanoTime();

        for (int i = 0; i < nThreads; i++) { // submitting each thread to the pool
            threadPool.submit(runnable);
        }
        for (int i = 0; i < nThreads; i++) { // shutdown threadpool
            threadPool.shutdown();
        }

        try {
            threadPool.awaitTermination(2, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        long end = System.nanoTime();
        System.out.println("Executed in " + String.valueOf((end - start) / 1000000) + " milliseconds");
    }
}

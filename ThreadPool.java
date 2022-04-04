import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

        while (true) {
            if (threadPool.isShutdown()) {
                break;
            }
        }
        long end = System.nanoTime();
        System.out.println("Executed in " + String.valueOf((end - start) / 1000000) + " milliseconds");
    }
}

class Counter {
    private int count = 0;
    // Without synchronization
    public void increment() {
        count++;
    }
    public int getCount() {
        return count;
    }
}
class MyThread extends Thread {
    private Counter counter;
    private int times;
    MyThread(Counter counter, int times) {
        this.counter = counter;
        this.times = times;
    }
    public void run() {
        for (int i = 0; i < times; i++) {
            counter.increment();
        }
    }
}
public class CounterRace {
    public static void main(String[] args) throws Exception {
        int numberOfThreads = 10;
        int incrementsPerThread = 100000;
        Counter counter = new Counter();
        MyThread[] threads = new MyThread[numberOfThreads];
        for (int i = 0; i < numberOfThreads; i++) {
            threads[i] = new MyThread(counter, incrementsPerThread);
            threads[i].start();
        }
        for (int i = 0; i < numberOfThreads; i++) {
            threads[i].join();
        }
        int expected = numberOfThreads * incrementsPerThread;
        System.out.println("Expected count: " + expected);
        System.out.println("Actual count: " + counter.getCount());
    }
}
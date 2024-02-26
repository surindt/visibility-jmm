package mx.unam.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * References
    https://github.com/miguelpinia/concurrent-course
    https://www.kovalenko.link/blog/race-condition-vs-data-race
    https://stackoverflow.com/questions/16615140/is-volatile-read-happens-before-volatile-write
    https://gee.cs.oswego.edu/dl/jmm/cookbook.html
    https://gee.cs.oswego.edu/dl/cpj/jmm.html
    http://www.cs.umd.edu/users/pugh/java/memoryModel/
    http://www.cs.umd.edu/~pugh/java/memoryModel/jsr-133-faq.html
    https://www.open-std.org/jtc1/sc22/wg21/docs/papers/2007/n2429.htm
    https://www.kernel.org/doc/html/latest/process/volatile-considered-harmful.html
 */
public class App 
{
    public static void main(String[] args) {
        Counter c = new Counter();
        c.test();
    }
    }
    class Counter {
        volatile int count = 0; //Volatile is not enough
        void inc() {
            count = count + 1;
        }
        void print(){
            System.out.println(count);
        }
    public void test() {
        int numProcessors = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(numProcessors);
        IntStream.range(0, numProcessors).forEach(i -> executor.submit(this::inc)); // The :: is a reference to a method turned into a "callback"
        executor.shutdown();
        print();
    }
}


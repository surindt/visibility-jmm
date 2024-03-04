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
public class App {
    public static void main(String[] args) {
            FlagConcu f = new FlagConcu();
            f.test();
        }
        }
        class FlagConcu {
            volatile boolean flag = false;
            int i;
            void raised() {
            System.out.println("here: "+flag); //write operation
            this.flag = true;
            }
            void readF(){
                while (!flag) {
                    this.i = i+1;
                }
            }
        public void test() {
            int numProcessors = Runtime.getRuntime().availableProcessors();
            ExecutorService executor = Executors.newFixedThreadPool(numProcessors);
            executor.submit(this::raised); // The :: is a reference to a method turned into a "callback"
            executor.submit(this::readF);

            //while(!flag);
            System.out.println(flag +" "+i); //read operation
            executor.shutdown();
        }
    }
    


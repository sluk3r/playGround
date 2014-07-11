package cn.sluk3r.play.concurrent;

/**
 * Created by baiing on 2014/7/10.
 */
public class ReorderExample {
    int a = 0;
    boolean flag = false;

    public void writer() {
        a = 1;                   //1
        flag = true;             //2
    }

    public void reader() {
        if (flag) {                //3
            int i =  a * a;        //4
            System.out.println("value of i: " + i);
        }
    }


    public static void main(String[] args) throws InterruptedException {
        for (int i=0;i<10;i++) {
            runDemo();
        }
    }

    private static void runDemo() throws InterruptedException {
        final ReorderExample reordering = new ReorderExample();

        Thread r = new Thread() {
            public void run() {
                reordering.reader();
            }
        };

        Thread w = new Thread() {
            public void run() {
                reordering.writer();
            }
        };

        r.start();w.start();
        r.join();w.join();
    }

}

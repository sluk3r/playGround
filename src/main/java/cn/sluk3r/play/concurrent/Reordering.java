package cn.sluk3r.play.concurrent;

/**
 * Created by baiing on 2014/7/10.
 */
public class Reordering {
    static  int a = 0, b = 0;
    static  int x = 0, y =0;

    static void reset() {
        a = b = x = y = 0;
        String status = String.format("(a=%s, b=%s, x=%s,y=%s)", a, b, x, y);
//        System.out.println(status);
    }

    public String reorderResult() throws InterruptedException {
        Thread t1 = new Thread() {
            public void run() {
                a = 1;
                x = b;
            }
        };

        Thread t2 = new Thread() {
            public void run() {
                b = 1;
                y = a;
            }
        };

        t1.start();t2.start();
        t1.join();t2.join();

        return String.format("(x:%s, y:%s)", x, y);
    }


    public static void main(String[] args) throws InterruptedException {
        /*
            (x:0, y:1)
            (x:1, y:0)
            (x:1, y:0)
            (x:0, y:1)
            (x:0, y:1)
            (x:1, y:0)
            (x:1, y:0)
            (x:1, y:0)
            (x:0, y:1)
            (x:1, y:0)
         */
        for (int i=0;i<10; i++) {
            System.out.println(new Reordering().reorderResult());
            Reordering.reset();
        }
    }
}

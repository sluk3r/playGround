package cn.sluk3r.test.collection.concurrent.synchronizer;

import org.junit.Test;

/**
 * Created by baiing on 2014/11/19.
 */
public class SemaphoreDemo {

    @Test
    public void demo() throws InterruptedException {

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {

                Person p1 = new Person(1L, "Test1", "XYZ");
                try {
                    PersonLock.getInstance().getWriteLock();
                    PersonStorage.getInstance().putPerson(p1);
                } catch (InterruptedException e) {
                    // Exception handling need to be done
                    e.printStackTrace();
                }
                finally{
                    PersonLock.getInstance().releaseWriteLock();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {

            @Override
            public void run() {

                Person p2 = new Person(2L, "Test123", "ABC");

                try {
                    PersonLock.getInstance().getWriteLock();

                    PersonStorage.getInstance().putPerson(p2);
                } catch (InterruptedException e) {
                    // Exception handling need to be done
                }
                finally{
                    PersonLock.getInstance().releaseWriteLock();
                }

            }
        });

        t1.start();
        t2.start();

        System.out.println(PersonStorage.getInstance().retrievePerson(2));
    }
}

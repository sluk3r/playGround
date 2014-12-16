package cn.sluk3r.test.collection.concurrent.synchronizer;

/**
 * Created by baiing on 2014/11/19.
 */
public class Person {
    long l;
    String test;
    String xyz;

    public Person(long l, String test1, String xyz) {
        this.l = l;
        this.test = test1;
        this.xyz = xyz;
    }

    public Person copyPerson() {
        return new Person(l, test, xyz);
    }
}

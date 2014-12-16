package cn.sluk3r.test.lang;

/**
 * Created by baiing on 2014/12/16.
 */
public class Child extends Parent {

    public static void main(String[] args) {

        /*
        Parent p = new Parent(): cn.sluk3r.test.lang.Parent
        Child c = new Child(): cn.sluk3r.test.lang.Child
        Parent p1 = new Child(): cn.sluk3r.test.lang.Child
         */

        Parent p = new Parent();
        System.out.println("Parent p = new Parent(): " + p.getCurrentClassNameWithReflection());

        Child c = new Child();
        System.out.println("Child c = new Child(): " + c.getCurrentClassNameWithReflection());

        Parent p1 = new Child();
        System.out.println("Parent p1 = new Child(): " + p1.getCurrentClassNameWithReflection());
    }

}

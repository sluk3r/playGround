package cn.sluk3r.test.collection.proxy;

import org.junit.Test;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Calendar;

/**
 * Created by baiing on 2014/7/15.
 */
public class ProxyDemo {
    @Test
    public void demo() {
        Service service = new ServiceImpl();
        System.out.println(service.getClass().getSimpleName());
        Service poxyService = (Service) JDKProxy.getPoxyObject(service);
        System.out.println(poxyService.getClass().getSuperclass());

        poxyService.saySomething("hello,My QQ code is 107966750.");
        poxyService.saySomething("what 's your name?");
        poxyService.saySomething("only for test,hehe.");
    }
}

interface Service {
    void saySomething(String content);
}

class ServiceImpl implements Service {

    @Override
    public void saySomething(String content) {
        System.out.println("ServiceImpl invocation: " + content);
    }
}

class JDKProxy {

    public static Object getPoxyObject(final Object c) {

        return Proxy.newProxyInstance(c.getClass().getClassLoader(), c.getClass().getInterfaces(),// JDK实现动态代理，但JDK实现必须需要接口
                new InvocationHandler() {
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        Object reObj = null;
                        System.out.print("you say: ");
                        reObj = method.invoke(c, args);
                        System.out.println(" [" + Calendar.getInstance().get(Calendar.HOUR) + ":"
                                + Calendar.getInstance().get(Calendar.MINUTE) + " "
                                + Calendar.getInstance().get(Calendar.SECOND) + "]");
                        return reObj;
                    }
                }
        );
    }
}

//package cn.sluk3r.play.concurrent.forkjoin;
//
///**
// * Created by baiing on 2014/6/14.
// */
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.ForkJoinPool;
//import java.util.concurrent.RecursiveTask;
//public class FibonacciSeries_ForkJoin {
//    public static void main(String[] arg) {
//        int size = 25;
//        Long startTime = System.currentTimeMillis();
//        final ForkJoinPool pool = new ForkJoinPool();
//        List fibonacciSeries = new ArrayList();
//        for (int index = 0; index < size; index++) {
//            Fibonacci task = new Fibonacci(index);
//            fibonacciSeries.add(pool.invoke(task));
//        }
//        Long endTime = System.currentTimeMillis();
//        System.out.println(endTime - startTime);
////        dumpList(fibonacciSeries);
//    }
//    public static void dumpList(List list) {
//        int index = 0;
//        for (Object object : list) {
//            System.out.printf("%d\t%d\n", index++, object);
//        }
//    }
//}
//
//class Fibonacci extends RecursiveTask<Integer> {
//    final int n;
//    Fibonacci(int n) { this.n = n; }
//
//    @Override
//    protected Integer compute() {
//        if (n <= 1)
//            return n;
//        Fibonacci f1 = new Fibonacci(n - 1);
//        f1.fork();
//        Fibonacci f2 = new Fibonacci(n - 2);
//        return f2.compute() + f1.join();
//    }
//}
//
////class FibonacciSeriesGeneratorTask extends RecursiveTask {
////    private static final long serialVersionUID = 1L;
////    private int index = 0;
////    public FibonacciSeriesGeneratorTask(int index) {
////        super();
////        this.index = index;
////    }
////    @Override
////    protected Integer compute() {
////        if (index == 0) {
////            return 0;
////        }
////
////        if (index < 2) {
////            return 1;
////        }
////        final FibonacciSeriesGeneratorTask worker1 = new FibonacciSeriesGeneratorTask(index - 1);
////        worker1.fork();
////        final FibonacciSeriesGeneratorTask worker2 = new FibonacciSeriesGeneratorTask(index - 2);
////        return worker2.compute() + worker1.join();
////    }
////}
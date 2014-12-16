package cn.sluk3r.test.collection.sort;

import cn.sluk3r.play.sort.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by baiing on 2014/7/2.
 */
public class SortDemo {
    //冒择路(入)兮(希)快归堆
    Sort impl;
    int[] numbers = new int[] {12,34, 2,9,10, 293,31};

    @Before
    public void setUp() {
        numbers = new int[] {12,34, 2,9,10, 293,31};
    }

    @Test
    public void bubble() {
        doSort(new BubbleSort());
    }

    @Test
    public void select() {
        doSort(new SelectSort());
    }

    @Test
    public void heap() {
        doSort(new HeapSort());
    }

    @Test
    public void insert() {
        doSort(new InsertSort());
    }

    @Test
    public void shell() {
        doSort(new ShellSort());
    }

    @Test
    public void merge() {
        doSort(new MergeSort());
    }

    @Test
    public void quick() {
        doSort(new QuickSort());
    }

    private void doSort(Sort sort) {
        System.out.printf("\n\n\n %s sort==========================", sort.getClass().getSimpleName());
        display(numbers);
        sort.sort(numbers);
        display(numbers);
    }

    private void display(int[] numbers) {
//        System.out.println(StringUtils.join(numbers, ","));
        System.out.println();
        for (int i: numbers) {
            System.out.printf("%s,", i);
        }
    }

}

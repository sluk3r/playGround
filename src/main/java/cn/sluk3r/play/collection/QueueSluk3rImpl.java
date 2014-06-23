package cn.sluk3r.play.collection;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * Created by baiing on 2014/6/21.
 */
public class QueueSluk3rImpl<T> implements Queue<T> {
    private int size;
    private T[] items;
    private int putIndex;
    private int takeIndex;

    public QueueSluk3rImpl(int capacity) {
        this.items = (T[]) new Object[capacity];
    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private int inc(int index) {
        index++;
        if (index == items.length) {
            return 0;
        } else {
            return index;
        }
    }

    @Override
    public boolean contains(Object o) {
        int i = takeIndex;
        int k = 0;
        while (k++ < size) {
            if (o.equals(items[i]))
                return true;
            i = inc(i);
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return null;
    }

    @Override
    public boolean add(T t) {
        if (size == items.length) {
            throw new IllegalStateException("Queue full");
        }
        insert(t);
        return true;
    }

    private void insert(T t) {
        items[putIndex] = t;
        putIndex = inc(putIndex);
        ++size;
    }

    @Override
    //Collection interface defined method
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {
        int i = takeIndex;
        int k = 0;
        while (k++ < size) {
            items[i] = null;
            i = inc(i);
        }
    }

    @Override
    public boolean offer(T t) {
        if (size == items.length) {
            return false;
        }
        insert(t);
        return true;
    }

    @Override
    // remove, exception
    public T remove() {
        return null;
    }

    @Override
    // remove, no exception
    public T poll() {
        return null;
    }

    @Override
    // not remove, exception
    public T element() {
        T x = peek();
        if (x != null)
            return x;
        else
            throw new NoSuchElementException();
    }

    @Override
    // not remove, no exception
    public T peek() {
        return (size == 0) ? null : items[takeIndex];
    }
}

/*
1, 两个维度： 是否删除，是否要抛异常。
 */
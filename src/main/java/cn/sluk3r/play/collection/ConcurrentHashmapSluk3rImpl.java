package cn.sluk3r.play.collection;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by baiing on 2014/6/27.
 */
public class ConcurrentHashmapSluk3rImpl implements ConcurrentMap {

    /**
     * The maximum capacity, used if a higher value is implicitly
     * specified by either of the constructors with arguments.  MUST
     * be a power of two <= 1<<30 to ensure that entries are indexable
     * using ints.
     */
    //wangxc, 这个数据有什么数学上的原理？  using ints？
    static final int MAXIMUM_CAPACITY = 1 << 30;
    /**
     * The maximum number of segments to allow; used to bound
     * constructor arguments.
     */
    //wangxc  一般来说得有这样的限制， 但这里怎么要用16？
    static final int MAX_SEGMENTS = 1 << 16; // slightly conservative,

    final int segmentMask;
    final int segmentShift;


    final Segment[] segments;

    public ConcurrentHashmapSluk3rImpl(int initialCapacity,
                                       float loadFactor, int concurrencyLevel) {
        //wangxc, 这个有什么影响？ 对后续的操作方面？ 也是第一次见concurrencyLevel
        if (concurrencyLevel > MAX_SEGMENTS)
            concurrencyLevel = MAX_SEGMENTS;

        // Find power-of-two sizes best matching arguments //wangxc， 这里更多的是找一个合适的值（2的n次米的幂）
        int sshift = 0;
        int ssize = 1;
        while (ssize < concurrencyLevel) {
            ++sshift;
            ssize <<= 1;
        }
        segmentShift = 32 - sshift;
        segmentMask = ssize - 1; //wangxc segmentShift和segmentMask两值都是跟concurrencyLevel有关， 为什么？
        this.segments = Segment.newArray(ssize);

        if (initialCapacity > MAXIMUM_CAPACITY)
            initialCapacity = MAXIMUM_CAPACITY;
        int c = initialCapacity / ssize;
        if (c * ssize < initialCapacity)
            ++c;
        int cap = 1;
        while (cap < c)
            cap <<= 1;

        for (int i = 0; i < this.segments.length; ++i)
            this.segments[i] = new Segment(cap, loadFactor);
    }

    @Override
    public Object putIfAbsent(Object key, Object value) {
        if (value == null)
            throw new NullPointerException();
        int hash = hash(key.hashCode());
        return segmentFor(hash).put(key, hash, value, true);
    }

    @Override
    public boolean remove(Object key, Object value) {
        return false;
    }

    @Override
    public boolean replace(Object key, Object oldValue, Object newValue) {
        return false;
    }

    @Override
    public Object replace(Object key, Object value) {
        return null;
    }
    /***************************
     * The above are methods defind in ConcurrentHashMap
     ***************************/

    /***************************
     * Below are methods defind in HashMap
    ***************************/

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey(Object key) {
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public Object get(Object key) {
        return null;
    }

    @Override
    public Object put(Object key, Object value) {
        if (value == null)
            throw new NullPointerException();
        int hash = hash(key.hashCode());
        return segmentFor(hash).put(key, hash, value, false);
    }

    @Override
    public Object remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set keySet() {
        return null;
    }

    @Override
    public Collection values() {
        return null;
    }

    @Override
    public Set<Entry> entrySet() {
        return null;
    }

    static class Segment extends ReentrantLock {
        Segment(int initialCapacity, float lf) {

        }

        boolean containsKey(Object key, int hash) {
            return  false;
        }

        boolean containsValue(Object value) {
            return  false;
        }

        boolean replace(Object key, int hash, Object oldValue, Object newValue) {
            return  false;
        }

        Object replace(Object key, int hash, Object newValue) {
            return null;
        }

        Object put(Object key, int hash, Object value, boolean onlyIfAbsent) {
            return null;
        }

        void rehash() {

        }

        Object remove(Object key, int hash, Object value) {
            return null;
        }

        static final <K,V> Segment [] newArray(int i) {
            return new Segment[i];
        }
    }


    private static int hash(int h) {
        // Spread bits to regularize both segment and index locations,
        // using variant of single-word Wang/Jenkins hash.
        h += (h <<  15) ^ 0xffffcd7d;
        h ^= (h >>> 10);
        h += (h <<   3);
        h ^= (h >>>  6);
        h += (h <<   2) + (h << 14);
        return h ^ (h >>> 16);
    }

    /**
     * Returns the segment that should be used for key with given hash
     * @param hash the hash code for the key
     * @return the segment
     */
    final Segment segmentFor(int hash) {
        return segments[(hash >>> segmentShift) & segmentMask];
    }


    public static void main(String[] args) {
        int c = 1;
        System.out.println("c<<1: " + String.valueOf(c<<1));
    }
}

package cn.sluk3r.play.collection;

import java.util.*;

/**
 * Created by baiing on 2014/6/20.
 */
public class HashMapSluk3rImpl implements Map {
    private Entry[] table;
    private int size;

    public HashMapSluk3rImpl(int size) {
        this.size = size;
        table = new Entry[size];
    }

    @Override
    public int size() {
        int totalCnt = 0;
        for (Entry e: table) {
            if (e == null) continue;
            totalCnt ++;
            while (e.next != null) {
                totalCnt ++;
            }
        }
        return totalCnt;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        int hashForKey = key.hashCode();
        int indexForTable = index(hashForKey);

        for(Entry e = table[indexForTable];e != null;e = e.getNext()) {
            Object k = e.getKey();
            if (key == k || key.equals(k)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return values().contains(value);
    }

    @Override
    public Object get(Object key) {
        int hashForKey = key.hashCode();
        int indexForTable = index(hashForKey);

        for(Entry e = table[indexForTable];e != null;e = e.getNext()) {
            Object k = e.getKey();
            if (key == k || key.equals(k)) {
                return e.getValue();
            }
        }

        return null;
    }

    @Override
    public Object put(Object key, Object value) {
        if (key == null) {
            throw new NullPointerException("key should not be null");
        }
        if (value == null) {
            throw new NullPointerException("value should not be null");
        }

        int hashForKey = key.hashCode();
        int indexForTable = index(hashForKey);

        //如果存在，就替换，否则新加。 怎么判断存在？

        //先处理不存在的情况， 这个存在不存在的判断可以合二为一， 先遍历地找， 如果能找到， 就说明存在， 这里一个注意：index只能说明key的hash再index后的值是一样， 并不代表key值相同。
        Entry lastEntry = null;
        for(Entry e = table[indexForTable];e != null;e = e.getNext()) {
            Object k = e.getKey();
            if (key == k || key.equals(k)) {
                Object oldValue = e.getValue();
                e.setValue(value);
//                added = true;
                return oldValue;
            }
            lastEntry = e;
        }
        //如果没有找到，也就是还没添加上。
        //在全空时，lastEntry肯定是空。
        Entry newEntry = new Entry(key, value);
        if (lastEntry != null) {
            lastEntry.setNext(newEntry);
        } else {
            table[indexForTable] = newEntry;
        }

        return null;
    }

    private int index(int hashForKey) {
        return hashForKey % table.length;
    }

    @Override
    public Object remove(Object key) {
        int hashForKey = key.hashCode();
        int indexForTable = index(hashForKey);

        for(Entry e = table[indexForTable];e != null;e = e.getNext()) {
            Object k = e.getKey();
            if (key == k || key.equals(k)) {
                Object removeValue = e.value;
                if (e.next != null) {
                    e.pre.next = e.next;
                } else if (e.pre == null) {
                    table[indexForTable] = null;
                }
                return removeValue;
            }
        }

        return null;
    }

    @Override
    public void putAll(Map m) {
        for(Object k: m.keySet()) {
            put(k, m.get(k));
        }
    }

    @Override
    public void clear() {
        table = new Entry[size];
    }

    @Override
    public Set keySet() {
        Set result = new HashSet(); //这种交叉使用应该不行。 应该实现下个只属性HashMap自己的List。
        for(Entry e: table) {
            if (e != null) {
                result.add(e.key);

                e = e.next;
                while(e != null) {
                    result.add(e.key);
                    e = e.next;
                }
            }
        }
        return  result;

    }

    @Override
    public Collection values() {
        List result = new ArrayList(); //这种交叉使用应该不行。 应该实现下个只属性HashMap自己的List。
        for(Entry e: table) {
            if (e != null) {
                result.add(e.value);

                e = e.next;
                while(e != null) {
                    result.add(e.value);
                    e = e.next;
                }
            }
        }
        return  result;
    }

    @Override
    public Set<Entry> entrySet() {
        Set result = new HashSet(); //这种交叉使用应该不行。 应该实现下个只属性HashMap自己的List。
        for(Entry e: table) {
            if (e != null) {
                result.add(e);

                e = e.next;
                while(e != null) {
                    result.add(e);
                    e = e.next;
                }
            }
        }
        return  result;
    }


    static class Entry<K,V> implements Map.Entry<K,V> {
        private K key;
        private V value;
        private Entry next;
        private Entry pre;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public Entry getNext() {
            return next;
        }

        public void setNext(Entry next) {
            this.next = next;
            next.pre = this;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            return this.value = value;
        }
    }
}


/*
跟JDK实现对比
1, 维护了一个size， 当有CRUD时，适时修改此值。 而不是在需要时， 再遍历地算。
2，key的hash计算时， 又加了一层的hash方法，这个的数学原理？
3，index计算： h & (length-1)， 这种计算跟取模的性能对比？
4，Entry还维护了一个hash值， 有什么好处？ 细想下， 应该有这个好处：Entry链表比较时， 可先通过hash值是否相等而分段比较，而不必逐个比较。相当于tag分类。
5，Entry里没有维护pre， remove里怎么实现？ 顺便看到了一个类LinkedHashMap， 它相对于HashMap的好处是？
6，Entry里加了recordAccess， 这个有什么用？
7，addEntry时，把新加入的放到头上， 而不尾部。 有什么好处？remove时方便？应该是。
8，得加上resize的功能。
9，threshold的作用， 新加时这样判断：size++ >= threshold， 并不是用threshold跟table的长度比。 这个是新发现。
10. putAll比想像的要复杂些：会先考虑resize的事。 当然最核心的，还是遍历传入map再逐个放进来。
11，remove操作， 在Entry里有个相应的recordRemoval。 这个方法跟上面的recordAccess一样，在HashMap自身的Entry是空实现。 而是在LinkedHashMap里有实现。  自己先改用新Entry时放头部的方式实现下。
12，clear方法， 只是清空table，并没有把table也扔掉。
13，keySet方法实现上， 有自己定义的类， 这样也好， 用一个类在CRUD时收集，而不必再遍历地取。
14，和上面的keySet有类似的收集操作。
15，多了newEntryIterator，newKeyIterator和newValueIterator三个实现。
16, modCount != expectedModCount时，ConcurrentModificationException异常机制。
 */
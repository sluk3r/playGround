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

package cn.sluk3r.test.consistentHash.self;

/**
 * Created by baiing on 2014/11/19.
 */
public class Node {
    String name;

    public Node(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Node{" +
                "name='" + name + '\'' +
                '}';
    }
}

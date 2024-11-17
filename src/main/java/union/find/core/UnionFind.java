package union.find.core;

import java.util.HashMap;
import java.util.Map;

public class UnionFind<T> {

    private Map<T, UnionFindNode<T>> valToNode = new HashMap<>();
    public UnionFind( T[] arr ){
        for (int i = 0; i < arr.length; i++) {
            valToNode.put(arr[i], new UnionFindNode<>(arr[i]));
        }
    }

    public UnionFind() {
    }

    public void union(T v1, T v2) {
        var node1 = valToNode.get(v1);
        var node2 = valToNode.get(v2);
        var root1 = find(node1);
        var root2 = find(node2);
        if(root2.getSize() > root1.getSize()){
            root1.setParent(root2);
        } else if(root1.getSize() > root2.getSize()){
            root2.setParent(root1);
        } else {
            root1.setParent(root2);
            root2.setSize(root2.getSize() + 1);
        }
    }

    private UnionFindNode<T> find(UnionFindNode<T> node) {
        if(node.getParent() == null){
            return node;
        } else {
            return find(node.getParent());
        }
    }

    public void add(T val) {
        valToNode.putIfAbsent(val, new UnionFindNode<>(val));
    }

    public boolean connected(T v1, T v2) {
        var node1 = valToNode.get(v1);
        var node2 = valToNode.get(v2);
        var root1 = find(node1);
        var root2 = find(node2);
        return root1==root2;
    }

    public Map<T, UnionFindNode<T>> getNodes(){
        return valToNode;
    }
}


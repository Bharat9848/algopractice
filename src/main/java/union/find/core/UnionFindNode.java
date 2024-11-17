package union.find.core;

public class UnionFindNode<T>{
    private T val;
    private UnionFindNode<T> parent;
    private int size;

    public UnionFindNode(T val){
        this.val = val;
        this.parent = null;
        this.size = 1;
    }

    public UnionFindNode<T> getParent() {
        return parent;
    }

    public void setParent(UnionFindNode<T> parent) {
        this.parent = parent;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }


}

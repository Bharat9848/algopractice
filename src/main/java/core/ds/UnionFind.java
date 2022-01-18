package core.ds;

public class UnionFind {
    private int[] parent;
    private int[] rank;
    private int noOfRoots;
    public UnionFind(int size) {
        this.parent = new int[size];
        this.rank = new int[size];
        for (int i = 0; i < size; i++) {
            parent[i] = i;
            rank[i] = 1;
        }
        noOfRoots = size;
    }

    public int find(int node) {
        if (parent[node] == node) {
            return node;
        }
        int root = find(parent[node]);
        parent[node] = root;
        return root;
    }

    public boolean connected(int i, int j) {
        return find(i) == find(j);
    }

    public void union(int i, int j) {
        int rooti = find(i);
        int rootj = find(j);
        if (rooti != rootj) {
            int rankI = rank[i];
            int rankJ = rank[j];
            if (rankI > rankJ) {
                parent[rootj] = rooti;
            } else if (rankI < rankJ) {
                parent[rooti] = rootj;
            } else {
                parent[rooti] = rootj;
                rank[j] = rank[j] + 1;
            }
            noOfRoots--;
        }
    }

    public int noOfComponents(){
        return noOfRoots;
    }
}


#Techniques:

1. Most problem can be solved by recursion-DFS.
2. Global variable is a way to pass on values during recursion. Alternatively some kind of resultHolder in return or in the argument type.
3. level order-BFS requires iterative approach can be solved using queue.
4. Implementation difficulty: Base conditions in cases where you want to distinguish between single leaves absent and both leaves absent. Pay extra attention to special cases of trees e.g. tree looks like a linear list.
5. Trie benefits: Handle the prefix usecase well.

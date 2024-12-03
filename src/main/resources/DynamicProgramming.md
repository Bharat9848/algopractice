Think of solution in terms of sub solution. 

Following are the ideas where DP are applicable
1. The problem can be broken down into "overlapping sub-problems" - smaller versions of the original problem that are reused multiple times.

2.The problem has an "optimal substructure" - an optimal solution can be formed from optimal solutions to the overlapping sub-problems of the original problem.

It is sometimes difficult to recognize the optimal substructure in the problem statement. Following are some of the characteristic or hint that you have to recognize to classify problem that can be solved by Dynamic programming.
- problem ask for some optimal solution like what is the maximum or minimum or how many ways etc.
- Local decision to distinguish between greedy algorithm from DP approach. Greedy algorithms do not need DP approach for the optimal solution like minimum spanning tree etc. On the other hand DP is valid for algorithm where greedy approach will lead to non-optimal global solution e.g. knapsack etc.

-Space and time complexity is equal to the number of the states and its calculation.
Note: Check if problems is not straight forward DP check assume it can be solved by greedy approach and then think of any counter example. If counter example is there that means solution need be exhaustive and DP can help in that.
 
Problems:
Somewhat difficult to figure out recursive formula: 
coin change - `cc(coins, total) =  min^coin(i)(i=0...n-1)(cc(coins, total-coini)+1` 
P1335 https://leetcode.com/problems/minimum-difficulty-of-a-job-schedule/
P1770 https://leetcode.com/problems/maximum-score-from-performing-multiplication-operations/
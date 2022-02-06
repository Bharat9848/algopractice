1. Find all subsets and then do something: Two nested loops - 1.one loop representing length 2.second loop to find subset boundaries.  

2. Find all pairs of array that satisfy some criteria : use a hashmap-> process arr so that you store partial criteria as key. And value is the list of elements satisfying that partial criteria. Then use another loop to process the arr check if current element partial criteria and if map from above step have the remaining criteria that matches that criteria. If yes then pairs will include curr element with each one of elements from map's value. 

3. Process array to transform it into some different form : 
 2.1 operation over range of index: Cumulative sum(???)
 2.2 group element based on some criteria.
 
4. Rearrange array element problems: 1. sorting 2. group similar item 3. disperse item so that no same element are together. 

5. Sometime problems requires to sort (or not) and then process the number when numbers switch from one to another.
6. Two pointer technique: one pointer can be loop index and other index can be invariant index. check problem 26 remove duplicate.
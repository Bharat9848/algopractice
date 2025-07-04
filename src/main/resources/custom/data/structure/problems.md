1.Design a data structure that takes in a stream of numbers and can check if any two numbers add up to a specific value. Implement the TwoSum class with the following constructor and methods: Constructor: Sets up the TwoSum object with an empty list at the start. void add(int number): Adds a new number to the list. boolean find(int value): Returns TRUE if any two numbers in the list add up to the given value. If not, it returns FALSE.
  - Intuition: 
    1. If `add` operation frequency is very high then append the number to a arrayList and return. In this case `find` will do sorting then finding.
    2. If balanced approach I will keep a sorted list. `add` will search the correct position which will be O(nlogn) and then insertion that will be O(n). `Find` will loop through first element till number is less than `value`/2. In each loop it will look for `value-elem` through binary search.
    3. If `find` operation frequency is very high then In `add` I will maintain a Hashmap of all the possibles sum from the underlying element. `find` will be just lookup in a hashmap.
  - Conclusion: 
  - wrong solution: 2nd approach looks bad. I will use 3 in case of balanced number of operations. work correctly but very bad for space.
  - Actual solution: Use Hashmap for number vs frequency. add would be O(1) and find would be O(n) 

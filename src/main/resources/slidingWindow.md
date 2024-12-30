## Conditions
 - Questions can be of static window length or dynamic window length.
 - We need to be very careful about questions on windows with dynamic window length. We need to maintain state of what's in the window and whats required. Which guide us when to expand or shrink the window.

## Past mistake
 - some question have result calculation when we encounter a negative case which cannot be added to window. This strategy may leave a bug in case the last index element is part of the sliding window but we did not recalculate result calculation as there is no negative case after it.


## Good question
 - P3325
 - 
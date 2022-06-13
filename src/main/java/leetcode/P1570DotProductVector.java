package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Given two sparse vectors, compute their dot product.
 * Implement class SparseVector:
 *
 *     SparseVector(nums) Initializes the object with the vector nums
 *     dotProduct(vec) Compute the dot product between the instance of SparseVector and vec
 *
 * A sparse vector is a vector that has mostly zero values, you should store the sparse vector efficiently and compute the dot product between two SparseVector.
 *
 * Follow up: What if only one of the vectors is sparse?
 *
 * Input: nums1 = [1,0,0,2,3], nums2 = [0,3,0,4,0]
 * Output: 8
 * Explanation: v1 = SparseVector(nums1) , v2 = SparseVector(nums2)
 * v1.dotProduct(v2) = 1*0 + 0*3 + 0*0 + 2*4 + 3*0 = 8
 *
 * Input: nums1 = [0,1,0,0,0], nums2 = [0,0,0,0,2]
 * Output: 0
 * Explanation: v1 = SparseVector(nums1) , v2 = SparseVector(nums2)
 * v1.dotProduct(v2) = 0*0 + 1*0 + 0*0 + 0*0 + 0*2 = 0
 * Input: nums1 = [0,1,0,0,2,0,0], nums2 = [1,0,0,0,3,0,4]
 * Output: 6
 * Constraints:
 *
 *     n == nums1.length == nums2.length
 *     1 <= n <= 10^5
 *     0 <= nums1[i], nums2[i] <= 100
 */
public class P1570DotProductVector {

    class SparseVector {
        private Map<Integer, Integer> indexToVal = new HashMap<>();
        SparseVector(int[] nums) {
            for (int i = 0; i < nums.length; i++) {
                if(nums[i] != 0){
                    indexToVal.put(i, nums[i]);
                }
            }
        }

        // Return the dotProduct of two sparse vectors
        public int dotProduct(SparseVector vec) {
            if(indexToVal.size() < vec.indexToVal.size()){
                return doDotProd(indexToVal, vec.indexToVal);
            } else {
                return doDotProd(vec.indexToVal, indexToVal);
            }
        }

        private int doDotProd(Map<Integer, Integer> smallerSet, Map<Integer, Integer> biggerSet) {
            int productSum = 0;
            for (Map.Entry<Integer, Integer> entry : smallerSet.entrySet()){
                Integer index = entry.getKey();
                Integer val = entry.getValue();
                productSum += (biggerSet.getOrDefault(index, 0) * val);
            }
            return productSum;
        }
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "1,0,0,2,3|0,3,0,4,0|8",
            "0,1,0,0,0|0,0,0,0,2|0",
            "0,1,0,0,2,0,0|1,0,0,0,3,0,4|6"}
    )
    void test(String vec1, String vec2, int expected){
        int[] vec1Val = Arrays.stream(vec1.split(",")).mapToInt(Integer::parseInt).toArray();
        int[] vec2Val = Arrays.stream(vec2.split(",")).mapToInt(Integer::parseInt).toArray();
        SparseVector sparseVector1 = new SparseVector(vec1Val);
        SparseVector sparseVector2 = new SparseVector(vec2Val);
        Assertions.assertEquals(expected, sparseVector1.dotProduct(sparseVector2));
    }
}

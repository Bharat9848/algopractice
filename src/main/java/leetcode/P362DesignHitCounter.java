package leetcode;

import com.sun.source.tree.Tree;
import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

/**
 * Design a hit counter which counts the number of hits received in the past 5 minutes (i.e., the past 300 seconds).
 *
 * Your system should accept a timestamp parameter (in seconds granularity), and you may assume that calls are being made to the system in chronological order (i.e., timestamp is monotonically increasing). Several hits may arrive roughly at the same time.
 *
 * Implement the HitCounter class:
 *
 *     HitCounter() Initializes the object of the hit counter system.
 *     void hit(int timestamp) Records a hit that happened at timestamp (in seconds). Several hits may happen at the same timestamp.
 *     int getHits(int timestamp) Returns the number of hits in the past 5 minutes from timestamp (i.e., the past 300 seconds).
 *
 *
 *
 * Example 1:
 *
 * Input
 * ["HitCounter", "hit", "hit", "hit", "getHits", "hit", "getHits", "getHits"]
 * [[], [1], [2], [3], [4], [300], [300], [301]]
 * Output
 * [null, null, null, null, 3, null, 4, 3]
 *
 * Explanation
 * HitCounter hitCounter = new HitCounter();
 * hitCounter.hit(1);       // hit at timestamp 1.
 * hitCounter.hit(2);       // hit at timestamp 2.
 * hitCounter.hit(3);       // hit at timestamp 3.
 * hitCounter.getHits(4);   // get hits at timestamp 4, return 3.
 * hitCounter.hit(300);     // hit at timestamp 300.
 * hitCounter.getHits(300); // get hits at timestamp 300, return 4.
 * hitCounter.getHits(301); // get hits at timestamp 301, return 3.
 *
 *
 *
 * Constraints:
 *
 *     1 <= timestamp <= 2 * 109
 *     All the calls are being made to the system in chronological order (i.e., timestamp is monotonically increasing).
 *     At most 300 calls will be made to hit and getHits.
 *
 *
 *
 * Follow up: What if the number of hits per second could be huge? Does your design scale?
 */
 class P362DesignHitCounter {
     TreeMap<Integer, Integer> hitFreq= new TreeMap<>();
     Integer oldestHitTimestamp = null;
     int totalCount = 0;
     P362DesignHitCounter() {

    }

    public void hit(int timestamp) {
        if(oldestHitTimestamp == null){
            oldestHitTimestamp= timestamp;
        }
        totalCount+=1;
        hitFreq.putIfAbsent(timestamp,0);
        hitFreq.put(timestamp, hitFreq.get(timestamp) +1);
    }

    public int getHits(int timestamp) {
        int lowerKey = timestamp - Math.min(300, timestamp) + 1;
        if(oldestHitTimestamp == null){
            return 0;
        }
        if(lowerKey > oldestHitTimestamp){
            while(oldestHitTimestamp != null && oldestHitTimestamp < lowerKey){
                totalCount -= hitFreq.remove(oldestHitTimestamp);
                Integer nextOldKey = hitFreq.ceilingKey(oldestHitTimestamp);
                oldestHitTimestamp = nextOldKey;
            }
        }
        return totalCount;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "HitCounter,hit,hit,hit,getHits,hit,getHits,getHits|null,1,2,3,4,300,300,301|null,null,null,null,3,null,4,3",
            "HitCounter,hit,hit,hit,getHits,hit,getHits,getHits,getHits|null,1,2,3,4,300,300,301,600|null,null,null,null,3,null,4,3,0",
            "HitCounter,hit,hit,hit,getHits,getHits,getHits,getHits,getHits,getHits|null,2,3,4,300,301,302,303,304,600|null,null,null,null,3,3,2,1,0,0"
            })
    void test(String callStr, String argStr, String expectedStr){
        String[] calls = callStr.split(",");
        String[] args = argStr.split(",");
        String[] expected = expectedStr.split(",");

        P362DesignHitCounter hitCounter=null;
        for (int i = 0; i < calls.length ; i++) {
            switch(calls[i]){
                case "HitCounter" : hitCounter = new P362DesignHitCounter();
                break;
                case "hit": hitCounter.hit(Integer.parseInt(args[i]));
                break;
                case "getHits": Assert.assertEquals(Integer.parseInt(expected[i]), hitCounter.getHits(Integer.parseInt(args[i])));
                break;
                default: throw new RuntimeException("Invalid input");
            }
        }
    }
}

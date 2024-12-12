package data.structure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * In this challenge, you have to implement a Snapshot Array with the following properties:
 *
 *     Constructor (length): This is the constructor and it initializes the data structure to hold the specified number of indexes.
 *
 *     Set Value (idx, val): This property sets the value at a given index idx to value val.
 *
 *     Snapshot(): This method takes no parameters and returns the Snap ID. Snap ID is the number of times that the snapshot function was called, less 1, as we start the count at 0. The first time this function is called, it saves a snapshot and returns 0. The nth time it is called, after saving the snapshot, it returns n−1.
 *
 *     Get Value (idx, Snap ID) method returns the value at the index in the snapshot with the given Snap ID.
 *
 * Suppose that we have three nodes whose values we wish to track in the snapshot array. Initially, the value of all the nodes will be 0. After calling the Set Value (1, 4) function, the value of node 1 will change to 4. If we take a snapshot at this point, the current values of all the nodes will be saved with Snap ID 0. Now, if we call Set Value (1, 7), the current value for node 1 will change to 7. Now, if we call the Get Value (1, 0) function, we will get the value of node 1 from snapshot 0, that is, 4.
 *
 * Constraints:
 *
 *     1≤ length ≤1000
 *     0≤ idx << length
 *     0≤ val ≤10^9
 *     0≤ snapid << (the total number of times we call Snapshot)
 *     At most 5×103 calls will be made to Set Value, Snapshot, and Get Value.
 */
public class SnapshotArrayNotDone {
    private List<List<Integer>> versions;
    private Map<Integer, Map<Integer, Integer>> versionedSnapShot;
    private int currentSnapshot = 0;
    private int length=0;
    public SnapshotArrayNotDone(int length) {
        this.length = length;
        versions = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            versions.add(new ArrayList<>());
        }
        versionedSnapShot = new HashMap<>();
    }

    // Function setValue sets the value at a given index idx to val.
    public void setValue(int idx, int state) {
        if(idx < length){
            var versionList =  versions.get(idx);
            if(versionList.isEmpty()){
                versionList.add(currentSnapshot);
            } else {
                var lastVersion  = versionList.get(versionList.size()-1);
                if(lastVersion != currentSnapshot){
                    versionList.add(currentSnapshot);
                }
            }
            versionedSnapShot.putIfAbsent(currentSnapshot, new HashMap<>());
            versionedSnapShot.get(currentSnapshot).put(idx, state);
        }
    }

    // This function takes no parameters and returns the snapid.
    // snapid is the number of times that the snapshot() function was called minus 1.
    public int snapshot() {
        var last = currentSnapshot;
        currentSnapshot++;
        return last;
    }

    // Function getValue returns the value at the index idx with the given snapid.
    public int getValue(int idx, int snapshotId1) {
        if(snapshotId1 < currentSnapshot && snapshotId1 >= 0){
            List<Integer> versionsIds = versions.get(idx);
            if(versionsIds.isEmpty()){
                return 0;
            }
            int version;
            if(versionsIds.size() == 1 && versionsIds.get(0) <= snapshotId1){
                version = 0;
            } else {
                version = findMinOrEqual(versionsIds, snapshotId1);
            }
            if(version == -1){
//                System.out.printf("idx=%d, snapshot=%d, versionArr=%s", idx, snapshotId1, versionsIds);
                return 0;
            }
            return versionedSnapShot.get(versionsIds.get(version)).get(idx);
        } else {
            return -1;
        }
    }

    private int findMinOrEqual(List<Integer> versions, int snapshotId1) {

        int beg = 0, end = versions.size() - 1;
        while (beg < end){
            int mid = beg + (end-beg) / 2;
            if(versions.get(mid) ==  snapshotId1){
                return mid;
            }else if (versions.get(mid) < snapshotId1) {
                beg = mid + 1;
            }else {
                end = mid;
            }
        }
        return versions.get(beg) ==  snapshotId1 ? beg: beg-1;
    }

}

package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;
import java.util.stream.Collectors;

/**
 * You are given several logs, where each log contains a unique ID and timestamp. Timestamp is a string that has the following format: Year:Month:Day:Hour:Minute:Second, for example, 2017:01:01:23:59:59. All domains are zero-padded decimal numbers.
 *
 * Implement the LogSystem class:
 *
 *     LogSystem() Initializes the LogSystem object.
 *     void put(int id, string timestamp) Stores the given log (id, timestamp) in your storage system.
 *     int[] retrieve(string start, string end, string granularity) Returns the IDs of the logs whose timestamps are within the range from start to end inclusive. start and end all have the same format as timestamp, and granularity means how precise the range should be (i.e. to the exact Day, Minute, etc.). For example, start = "2017:01:01:23:59:59", end = "2017:01:02:23:59:59", and granularity = "Day" means that we need to find the logs within the inclusive range from Jan. 1st 2017 to Jan. 2nd 2017, and the Hour, Minute, and Second for each log entry can be ignored.
 *
 * Input
 * ["LogSystem", "put", "put", "put", "retrieve", "retrieve"]
 * [[], [1, "2017:01:01:23:59:59"], [2, "2017:01:01:22:59:59"], [3, "2016:01:01:00:00:00"], ["2016:01:01:01:01:01", "2017:01:01:23:00:00", "Year"], ["2016:01:01:01:01:01", "2017:01:01:23:00:00", "Hour"]]
 * Output
 * [null, null, null, null, [3, 2, 1], [2, 1]]
 *
 * Explanation
 * LogSystem logSystem = new LogSystem();
 * logSystem.put(1, "2017:01:01:23:59:59");
 * logSystem.put(2, "2017:01:01:22:59:59");
 * logSystem.put(3, "2016:01:01:00:00:00");
 *
 * // return [3,2,1], because you need to return all logs between 2016 and 2017.
 * logSystem.retrieve("2016:01:01:01:01:01", "2017:01:01:23:00:00", "Year");
 *
 * // return [2,1], because you need to return all logs between Jan. 1, 2016 01:XX:XX and Jan. 1, 2017 23:XX:XX.
 * // Log 3 is not returned because Jan. 1, 2016 00:00:00 comes before the start of the range.
 * logSystem.retrieve("2016:01:01:01:01:01", "2017:01:01:23:00:00", "Hour");
 *
 *     1 <= id <= 500
 *     2000 <= Year <= 2017
 *     1 <= Month <= 12
 *     1 <= Day <= 31
 *     0 <= Hour <= 23
 *     0 <= Minute, Second <= 59
 *     granularity is one of the values ["Year", "Month", "Day", "Hour", "Minute", "Second"].
 *     At most 500 calls will be made to put and retrieve.
 */
public class P635DesignLogStorageSystemNotDone {
    Map<Integer, String> indexToType = new HashMap<>();
    Map<String, Integer> typeToLevel = new HashMap<>();
    private class TimeStamp{
        int year;
        int month;
        int day;
        int hour;
        int minute;
        int second;
        Map<String, Integer> typeToVal = new HashMap<>();
        public TimeStamp(String timestamp){
            String[] tsComponent = timestamp.split(":");
            this.year = Integer.parseInt(tsComponent[0]);
            this.month = Integer.parseInt(tsComponent[1]);
            this.day = Integer.parseInt(tsComponent[2]);
            this.hour = Integer.parseInt(tsComponent[3]);
            this.minute = Integer.parseInt(tsComponent[4]);
            this.second = Integer.parseInt(tsComponent[5]);
            typeToVal.put("year", year);
            typeToVal.put("month", month);
            typeToVal.put("day", day);
            typeToVal.put("hour", hour);
            typeToVal.put("minute", minute);
            typeToVal.put("second", second);
        }
        public int getTimeComponent(String type){
            return this.typeToVal.get(type);
        }
    }
    private class LogNode{
        String keyType;
        Map<Integer, LogNode> nodes;
        boolean isLeaf;
        int val;
        int id;
        public LogNode(String keyType, int val){
            if(indexToType.isEmpty()){
                indexToType.put(0, "year");
                typeToLevel.put("year", 0);
                indexToType.put(1, "month");
                typeToLevel.put("month", 1);
                indexToType.put(2, "day");
                typeToLevel.put("day", 2);
                indexToType.put(3, "hour");
                typeToLevel.put("hour", 3);
                typeToLevel.put("minute", 4);
                indexToType.put(4, "minute");
                typeToLevel.put("second", 5);
                indexToType.put(5, "second");
            }
            this.isLeaf = false;
            this.keyType = keyType;
            this.val = val;
            this.nodes = new HashMap<>();
        }

        public LogNode(int val, int id){
            this.isLeaf = true;
            this.id = id;
            this.val = val;
            this.keyType = "second";
        }
    }
    private LogNode root;
    public P635DesignLogStorageSystemNotDone() {
        root = new LogNode("", -1);
    }

    public void put(int id, String timestamp) {
        TimeStamp timeStamp = new TimeStamp(timestamp);
        LogNode temp = root;
        for (int i = 0; i < 5; i++) {
            int component = timeStamp.getTimeComponent(indexToType.get(i));
            LogNode tempChild = temp.nodes.get(component);
            if(tempChild == null){
                tempChild = new LogNode(indexToType.get(i), component);
                temp.nodes.put(component, tempChild);
            }
            temp = tempChild;
        }
        int sec = timeStamp.getTimeComponent(indexToType.get(5));
        temp.nodes.put(sec, new LogNode(sec, id));
    }

    public List<Integer> retrieve(String start, String end, String granularity) {
        granularity = granularity.toLowerCase();
        TimeStamp startTime = new TimeStamp(start);
        TimeStamp endTime = new TimeStamp(end);
        List<Integer> result = new ArrayList<>();
        LogNode tempStart = root;
        LogNode tempEnd = root;
        int i=0;
        for (; i <= typeToLevel.get(granularity.toLowerCase()); i++) {
            int startComp = startTime.getTimeComponent(indexToType.get(i));
            int endComp = endTime.getTimeComponent(indexToType.get(i));
            tempStart = tempStart.nodes.get(startComp);
            tempEnd = tempEnd.nodes.get(endComp);
            if (startComp != endComp) {
                break;
            }
        }
        if(tempStart == tempEnd){
            result.addAll(getAllLeafIds(tempStart));
        }else{
            Set<Integer> result1 = new HashSet<>();
            result1.addAll(getAllLeafAfterGranualityReached(startTime, granularity, root, true));
            result1.addAll(getAllLeafAfterGranualityReached(endTime, granularity, root, false));
            int startYear = startTime.getTimeComponent("year");
            int endYear = endTime.getTimeComponent("year");
            for (int j = startYear+1; j < endYear; j++) {
                result1.addAll(getAllLeafIds(root.nodes.get(j)));
            }
            result.addAll(result1);
        }
        return result;
    }

    private Collection<Integer> getAllLeafAfterGranualityReached(TimeStamp time, String granularity, LogNode root, boolean isleft) {
        LogNode temp = root;
        for (int i = 0; i < typeToLevel.get(granularity.toLowerCase()) && temp !=null; i++) {
            temp = temp.nodes.get(time.getTimeComponent(indexToType.get(i)));
        }
        if(temp == null){
            return Collections.emptyList();
        }
        int val = time.getTimeComponent(granularity.toLowerCase());
        Collection<Integer> allLeafIds = new ArrayList<>();
        if(isleft){
            for(LogNode node: temp.nodes.values()){
                if(node.val >= val){
                    allLeafIds.addAll(getAllLeafIds(node));
                }
            }
        }else {
            for(LogNode node: temp.nodes.values()){
                if(node.val <= val){
                    allLeafIds.addAll(getAllLeafIds(node));
                }
            }
        }
        return allLeafIds;
    }

    private Collection<Integer> getAllLeafIds(LogNode logNode) {
        if(logNode == null){
            return new ArrayList<>();
        }
        if(logNode.isLeaf){
            return Collections.singleton(logNode.id);
        }
        List<Integer> allIds = new ArrayList<>();
        for (LogNode child : logNode.nodes.values()) {
            allIds.addAll(getAllLeafIds(child));
        }
        return allIds;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "LogSystem,put,retrieve|,1;2017:01:01:23:59:59,2017:01:01:23:59:58;2017:01:01:23:59:58;Second|null,null,|non existent nodes",
            "LogSystem,put,retrieve|,1;2017:01:01:23:59:59,2017:01:01:23:59:58;2017:01:02:23:59:59;Minute|null,null,1|non existent lower index",
            "LogSystem,put,put,retrieve|,1;2017:01:01:23:59:59,2;2017:01:02:23:59:59,2017:01:01:23:59:59;2017:01:02:23:59:59;Day|null,null,null,1;2|range query lower indices",
            "LogSystem,put,put,retrieve|,1;2017:01:01:23:59:59,2;2017:01:02:23:59:59,2017:01:01:23:59:59;2017:01:02:23:59:59;Month|null,null,null,2;1|zero length query for lower indices",
            "LogSystem,put,put,put,put,retrieve|,1;2017:01:01:23:59:59,2;2017:01:01:22:59:59,3;2016:01:01:00:00:00,4;2015:01:01:00:00:00,2016:01:01:01:01:01;2017:01:01:23:00:00;Hour|null,null,null,null,null,2;1|range querying smaller indices",
            "LogSystem,put,put,put,put,retrieve|,1;2022:05:07:01:00:00,2;2022:05:07:02:00:00,3;2022:05:07:03:00:00,4;2022:05:07:04:00:00,2022:05:07:01:00:00;2022:05:07:04:00:00;hour|null,null,null,null,null,1;2;3;4| single node querying for lower index",
            "LogSystem,put,put,put,put,retrieve|,1;2017:01:01:23:59:59,2;2017:01:01:22:59:59,3;2016:01:01:00:00:00,4;2015:01:01:00:00:00,2015:01:01:01:01:01;2017:01:01:23:00:00;Year|null,null,null,null,null,4;3;2;1|range querying top indices",
    })
    void test(String operationStr, String argStr, String expectedStr, String message){
        System.out.println(message);
        P635DesignLogStorageSystemNotDone logger = null;
        String[] operations = operationStr.split(",");
        String[] arguments = argStr.split(",");
        String[] expection = expectedStr.split(",");
        for (int i = 0; i < operations.length; i++) {
            String operation = operations[i];
            switch (operation) {
                case "LogSystem": {
                    logger = new P635DesignLogStorageSystemNotDone();
                    break;
                }
                case "put": {
                    String[] args = arguments[i].split(";");
                    logger.put(Integer.parseInt(args[0]), args[1]);
                    break;
                }
                case "retrieve" : {
                    String[] args = arguments[i].split(";");
                    List<Integer> expected = expection.length <= i || expection[i].isEmpty()? Collections.emptyList():Arrays.stream(expection[i].split(";")).map(Integer::parseInt).collect(Collectors.toList());
                    List<Integer> actual = logger.retrieve(args[0], args[1], args[2]);
                    Assertions.assertEquals(new HashSet<>(expected), new HashSet<>(actual));
                    Assertions.assertEquals(expected.size(), actual.size());
                }
            }

        }
    }
}

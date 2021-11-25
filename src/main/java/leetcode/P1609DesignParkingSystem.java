package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Design a parking system for a parking lot. The parking lot has three kinds of parking spaces: big, medium, and small, with a fixed number of slots for each size.
 *
 * Implement the ParkingSystem class:
 *
 *     ParkingSystem(int big, int medium, int small) Initializes object of the ParkingSystem class. The number of slots for each parking space are given as part of the constructor.
 *     bool addCar(int carType) Checks whether there is a parking space of carType for the car that wants to get into the parking lot. carType can be of three kinds: big, medium, or small, which are represented by 1, 2, and 3 respectively. A car can only park in a parking space of its carType. If there is no space available, return false, else park the car in that size space and return true.
 *
 *
 *
 * Example 1:
 *
 * Input
 * ["ParkingSystem", "addCar", "addCar", "addCar", "addCar"]
 * [[1, 1, 0], [1], [2], [3], [1]]
 * Output
 * [null, true, true, false, false]
 *
 * Explanation
 * ParkingSystem parkingSystem = new ParkingSystem(1, 1, 0);
 * parkingSystem.addCar(1); // return true because there is 1 available slot for a big car
 * parkingSystem.addCar(2); // return true because there is 1 available slot for a medium car
 * parkingSystem.addCar(3); // return false because there is no available slot for a small car
 * parkingSystem.addCar(1); // return false because there is no available slot for a big car. It is already occupied.
 *
 *
 *
 * Constraints:
 *
 *     0 <= big, medium, small <= 1000
 *     carType is 1, 2, or 3
 *     At most 1000 calls will be made to addCar
 */
public class P1609DesignParkingSystem {
    private int MAX_SMALL;
    private int MAX_MED;
    private int MAX_BIG;
    private int bigSize = 0;
    private int medSize = 0;
    private int smallSize = 0;
    public void parkingSystem(int big, int medium, int small) {
        this.MAX_BIG = big;
        this.MAX_MED = medium;
        this.MAX_SMALL = small;
    }

    public boolean addCar(int carType) {
        switch (carType){
            case 1:
                if (checkAvailbiity(bigSize, MAX_BIG)) {
                    bigSize++;
                    return true;
                } else {
                    return false;
                }
            case 2:
                if (checkAvailbiity(medSize, MAX_MED)) {
                    medSize++;
                    return true;
                } else {
                    return false;
                }
            case 3:
                if (checkAvailbiity(smallSize, MAX_SMALL)) {
                    smallSize++;
                    return true;
                } else {
                    return false;
                }
            default: throw new RuntimeException("Invalid slot");
        }
    }

    private boolean checkAvailbiity(int currSize, int max_size) {
        return currSize < max_size;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "ParkingSystem,addCar,addCar,addCar,addCar|1,1,0;1;2;3;1|null,true,true,false,false"
    })
    void test(String operationStr, String argStr, String expStr){
        String[] operations = operationStr.split(",");
        String[] arguments = argStr.split(";");
        String[] expected = expStr.split(",");
        P1609DesignParkingSystem p1609DesignParkingSystem = new P1609DesignParkingSystem();
        for (int i = 0; i < operations.length; i++) {
            String operation = operations[i];
            switch (operation){
                case "ParkingSystem": {
                    String[] sizes = arguments[i].split(",");
                    p1609DesignParkingSystem.parkingSystem(Integer.parseInt(sizes[0]), Integer.parseInt(sizes[1]), Integer.parseInt(sizes[2]));
                    break;
                }
                case "addCar" : {
                    Assert.assertEquals(Boolean.valueOf(expected[i]), p1609DesignParkingSystem.addCar(Integer.parseInt(arguments[i])));
                    break;}
                default: throw new RuntimeException("Invalid operation");
            }
        }
    }
}

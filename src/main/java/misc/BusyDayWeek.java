package misc;

/*
You are provided with a list of guest check-in and check-out dates for a certain hotel referred to the next week:

log = [
    { 'user_id': 1, 'check_in': 3, 'check_out': 4 },
    { 'user_id': 2, 'check_in': 2, 'check_out': 6 },
    { 'user_id': 3, 'check_in': 1, 'check_out': 4 },
    { 'user_id': 3, 'check_in': 5, 'check_out': 6 },
];

Provide the week day with maximum number of guests.
For the example above:

       [1]      [2]       [3]       [4]       [5]       [6]    [7]
   |----+--------+---------+---------+---------+---------+------+-|
 1 |                       x=========x                            |
 2 |             x=======================================x        |
 3 |    x============================x         x=========x        |
   |----+--------+---------+---------+---------+---------+------+-|
Two sets of numbers
1. List of intervals
2. list of dates
sol1 outer loop : noOfDates
        busyDayMaxBooking
        Inner loop :ranges
            calForCurrentDay
        if(busyDayMaxBooking > calForCurrentDay)
            busyDayMaxBooking = calForCurrentDate
     outer loop ends
 */
//@TODO
public class BusyDayWeek {

}

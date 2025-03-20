As part of your solution:
- List any assumptions that you made in order to solve this problem
  * Trips are only calculated based on tap records of the same busId and the same pan.
  * Completed/cancelled(grouped, non-single) trips are only calculated based on the most adjacent tap records in term of tap time.
  * To make tap records organised, I would group them by a compound key first (busId and pan), and then sort them by ascending tap time.
  * An ON tap would be considered as a single ON tap if it is not followed by another OFF tap directly. A single ON tap would be counted for a incomplete trip.
  * An OFF tap would be considered as a single OFF tap if it doesn't follow another ON tap directly. A single OFF tap would not be counted for any trip.

- Provide instructions on how to run the application

  * Please run the following method signature in SpringBoot's BusTripAggregatorApplication.class.
      public static void main(String[] args)
    
  * By default - our application would aggregate the example tap csv file provided by Littlepay.
    
  * Please replace "csvs/TapRecords.csv" in TripAggregator.class with other resource names to test my other mocked taps csv files.
    E.g "csvs/BulkTapRecords.csv", "csvs/UnorderedTapRecords.csv" or "csvs/UnorderedBulkTapRecords.csv"
    List<TapRecord> tapRecordList = tapRecordCSVMapper.buildTapRecordList("csvs/TapRecords.csv");

  * We could find trips.csv file get updated right after we run the main method of BusTripAggregatorApplication.class. That's the csv file with calculated trip fare information.
  
- Provide a test harness to validate your solution
  * We could either run end-to-end integration tests following the instruction under "Provide instructions on how to run the application".
 
  * We could run unit tests under test directory.

- Provide the output file for the example input file
  * Please check trips.csv.

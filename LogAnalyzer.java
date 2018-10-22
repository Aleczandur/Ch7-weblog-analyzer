/**
 * Read web server data and analyse hourly access patterns.
 * 
 * @author Alex Floyd
 * @version    10.22.2018
 */
public class LogAnalyzer
{
    // Where to calculate the hourly access counts.
    private int[] hourCounts;
    // Use a LogfileReader to access the data.
    private LogfileReader reader;

    /**
     * Create an object to analyze hourly web accesses.
     */
    public LogAnalyzer(String names)
    { 
        // Create the array object to hold the hourly
        // access counts.
        hourCounts = new int[24];
        // Create the reader to obtain the data.
        reader = new LogfileReader(names);
    }
    
    public  int numAccess(){
    int access = 0;
    for( int check = 0; check < hourCounts.length; ++check){
        access += hourCounts[check];
    }
    return access;
    }
    
    public int busiestHour(){
        int maxHour, maxCounts = 0;
        for( int hour = 0; hour < hourCounts.length; ++ hour){
            if(hourCounts[hour] > maxCounts)
            {
               maxCounts = hourCounts[hour];
               maxHour = hour;
            }
        }
        return maxCounts;
    }
    
    public int quietestHour(){
        int minHour, minCounts = 0;
        for( int hour = 0; hour < hourCounts.length; ++ hour){
            if (minCounts < hourCounts.length){
               minCounts = hourCounts[hour];
               minHour = hour;
            }
            else if (minCounts > hourCounts[hour] || minCounts == hourCounts[hour]){
                minCounts = hourCounts[hour];
            }
        }
        return minCounts;
    }
    
    public int busiestTwoHour(){
        int[] two_hours = new int[2];
        int count, bigTime = 0;
        for(count = 0; count < hourCounts.length; ++count){
            if (bigTime > hourCounts[count]){
                bigTime = bigTime;
            }
            else if (bigTime < hourCounts[count]){
                bigTime = hourCounts[count];
                two_hours[0] = bigTime;
                if(count == 0){
                    if (hourCounts[count] > hourCounts[count+1]){
                        two_hours[0] = hourCounts[count];
                    }
                    else if (hourCounts[count] < hourCounts[count+1]){
                        two_hours[0] = hourCounts[count+1];
                    }
                }
                else{
                    if(hourCounts[count+1] > hourCounts[count-1]){
                        two_hours[1] = hourCounts[count-1];
                    }
                    else if (hourCounts[count+1] < hourCounts[count-1]){
                        two_hours[0] = hourCounts[count-1];
                        two_hours[1] = bigTime;
                    }
                }
            }
        }
        return two_hours[0];
    }
                
    
    
    
    
    /**
     * Analyze the hourly access data from the log file.
     */
    public void analyzeHourlyData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int hour = entry.getHour();
            hourCounts[hour]++;
        }
    }
    
    
    
    
    
    
    
    /**
     * Print the hourly counts.
     * These should have been set with a prior
     * call to analyzeHourlyData.
     */
    public void printHourlyCounts()
    {
        System.out.println("Hr: Count");
        for(int hour = 0; hour < hourCounts.length; hour++) {
            System.out.println(hour + ": " + hourCounts[hour]);
        }
    }
    
    /**
     * Print the lines of data read by the LogfileReader
     */
    public void printData()
    {
        reader.printData();
    }
}

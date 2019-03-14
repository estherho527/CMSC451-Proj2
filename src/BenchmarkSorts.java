/*
 * Name: Esther Ho
 * CMSC 451 Project 2
 * Due: October 14, 2018
 * File: BenchmarkSorts.java
 * 
 * Description:
 * 
 * 
 * 
 * 
 * 
 * Required Files:
 * -
 * 
 */

import java.util.Random;

public class BenchmarkSorts {
	
	// INSTANCE VARIABLES
	
	private final int NUM_TRIALS; // # trials per data size
	private static final int INT_MIN = 0; // min # for randomly generated arrays to sort
	private static final int INT_MAX = 100000; // max # for randomly generated arrays to sort
	
	// integer array related
	private final int[] dataSizes; // sizes of data arrays to create. ex 10, 20....
	private final int numCases; // # of data sizes to test
	private final int[][][] data; // trial data arrays for each data size
	
	// sort and analysis related
	private final int[][] iterCounts; // counts for iterative sort
	private final long[][] iterTimes; // times for iterative sort
	private final int[][] recurCounts; // counts for recursive sort
	private final long[][] recurTimes; // times for recursive sort
	private final double[][] calcs; // calculations for data
	
	HeapSort sorter; 
	
		//FINISH
	
	// constructor
	public BenchmarkSorts(int[] sizes, int trials) {

		//FINISH
		NUM_TRIALS = trials;
		dataSizes = sizes;
		numCases = dataSizes.length;
		data = new int[numCases][NUM_TRIALS][];
		iterCounts = new int[numCases][NUM_TRIALS];
		iterTimes = new long[numCases][NUM_TRIALS];
		recurCounts = new int[numCases][NUM_TRIALS];
		recurTimes = new long[numCases][NUM_TRIALS];
		calcs = new double[numCases][8]; 
		
		sorter = new HeapSort();
		
		// create data set
		genAllIntArr();
		
	}// end constructor

	/*
	 * runSorts()
	 */
	public void runSorts() throws UnsortedException{
		
		for(int i = 0; i < numCases; i++){
            for(int j = 0; j < NUM_TRIALS; j++){
                
                //Iterative:
                HeapSort sort = new HeapSort();
                int[] caseData = (int[]) data[i][j].clone();
                sort.iterativeSort(caseData);
                iterCounts[i][j] = sort.getCount();
                iterTimes[i][j] = sort.getTime();
                try {
                    sort.isSorted(caseData);
                } catch (UnsortedException ex) {}
               
                //Recursive:
                sort = new HeapSort();
                caseData = (int[]) data[i][j].clone();
                sort.recursiveSort(caseData);
                recurCounts[i][j] = sort.getCount();
                recurTimes[i][j] = sort.getTime();
                try{
                    sort.isSorted(caseData);
                } catch(UnsortedException ex) {}
            }
             System.out.println("Test Case " + (i+1) + " finished [size " + dataSizes[i] + "]" );
        }
	}// end runSorts()
	
	/*
	 * genIntArr()
	 * -generates all integer array of values [INT_MIN,INT_MAX] for all data sizes given
	 * - (NUM_TRIALS of arrays for every data size)
	 * -there may be duplicates
	 * 
	 * -called in BenchmarkSorts constructor
	 */
	private void genAllIntArr(){
		
		Random rand = new Random();
		int arrSize; 
		
		// go through every data size
		for (int i = 0; i < numCases; i++){
			
			// make NUM_TRIALS of integer arrays 
			for (int j = 0; j < NUM_TRIALS; j++){
				
				arrSize = dataSizes[i];
				
				// declare new int array and fill it with random integers
				data[i][j] = new int[arrSize];
				
				for (int k = INT_MIN; k < arrSize; k++){
					data[i][j][k] = rand.nextInt(INT_MAX);
				}
			}
			
		}
	}// end genIntArr()
	
	/*
	 * doAllCalcs()
	 * -
	 */
	private void doAllCalcs(){
		
		for(int i = 0; i < numCases; i++){
			
			calcs[i][0] = mean(iterCounts[i]);
			calcs[i][1] = coeffVar(iterCounts[i], calcs[i][0]);
			calcs[i][2] = mean(iterTimes[i]); // / 1000000000; // convert ns to s
			calcs[i][3] = coeffVar(iterTimes[i], calcs[i][2]);

			calcs[i][4] = mean(recurCounts[i]);
			calcs[i][5] = coeffVar(recurCounts[i], calcs[i][4]);
			calcs[i][6] = mean(recurTimes[i]); // / 1000000000; // convert ns to s
			calcs[i][7] = coeffVar(recurTimes[i], calcs[i][6]);
		}
	}// end doAllCalcs()
	
	/*
	 * displayReports()
	 */
	public void displayReport(){

		System.out.println("\nBenchmark Results for Heap Sort:");
		doAllCalcs();
		
		System.out.print("+");
		for(int i = 0; i < 119; i++) 
			System.out.print("-");
		System.out.println("+");
		
		System.out.printf("|%-7s|%27s%28s|%27s%28s|%n", "Size N", "Iterative", "", "Recursive", "");
		
		System.out.print("+");
		for(int i = 0; i < 119; i++) 
			System.out.print("-");
		System.out.println("+");
		
		System.out.printf("|%7s|", "");
		for(int i = 0; i < 2; i++) {
			System.out.printf("%15s|%11s|%15s|%11s|", "Average", "Coefficient", "Average", "Coefficient");
		}
		System.out.printf("%n|%7s|", "");
		for(int i = 0; i < 2; i++) {
			System.out.printf("%15s|%11s|%15s|%11s|", "Critical", "of", "Execution", "of");
		}
		System.out.printf("%n|%7s|", "");
		for(int i = 0; i < 2; i++) {
			System.out.printf("%15s|%11s|%15s|%11s|", "Operation", "Variance", "Time", "Variance");
		}
		System.out.printf("%n|%7s|", "");
		for(int i = 0; i < 2; i++) {
			System.out.printf("%15s|%11s|%15s|%11s|", "Count", "Count", "(ns)", "Time (ns)");
		}
		
		System.out.print("\n+");
		for(int i = 0; i < 119; i++) 
			System.out.print("-");
		System.out.print("+");

		for(int i = 0; i < dataSizes.length; i++) {
			System.out.printf("%n|%7d|", dataSizes[i]);
			System.out.printf("%15.2f|%11.4f|%15.2f|%11.4f|%15.2f|%11.4f|%15.2f|%11.4f|", 
					calcs[i][0], calcs[i][1], calcs[i][2], calcs[i][3], 
					calcs[i][4], calcs[i][5], calcs[i][6], calcs[i][7]);
			
		}

		System.out.print("\n+");

		for(int i = 0; i < 119; i++) 
			System.out.print("-");

		System.out.println("+");
	}// end displayReport()
	
	// UTILITY METHODS
	
	/*
	 * mean()
	 * -REQUIRED
	 * = sum(all arr elements)/ arr.length
	 */
	public static double mean(int[] arr){
		
		double mean = 0;
		double sum = 0;
		int length = arr.length;
		
		for(int i = 0; i < length; i++)
			sum += arr[i];
		
		mean = sum/length;
		return mean;
	}// end mean()
	
	/*
	 * mean()
	 * -REQUIRED
	 * = sum(all arr elements)/ arr.length
	 */
	public static double mean(long[] arr){
		
		double mean = 0;
		double sum = 0;
		int length = arr.length;
		
		for(int i = 0; i < length; i++)
			sum += arr[i];
		
		mean = sum/length;
		return mean;
	}// end mean()
	
	/*
	 * var()
	 * -variance (avg of squared differences from the mean)
	 * =sum of all squared differences from the mean)/n
	 */
	public static double var(int[] arr, double mean){
		
		double sumSqDiff = 0;
		double variance = 0;
		int length = arr.length;
		
		for (int i = 0; i < length; i ++)
			sumSqDiff += Math.pow(arr[i] - mean, 2);
		
		variance = sumSqDiff/length;
		
		return variance;
	}// end var()
	
	/*
	 * var()
	 * -variance (avg of squared differences from the mean)
	 * =sum of all squared differences from the mean)/n
	 */
	public static double var(long[] arr, double mean){
		
		double sumSqDiff = 0;
		double variance = 0;
		int length = arr.length;
		
		for (int i = 0; i < length; i ++)
			sumSqDiff += Math.pow(arr[i] - mean, 2);
		
		variance = sumSqDiff/length;
		
		return variance;
	}// end var()
	
	/*
	 * stdDev()
	 * -standard deviation
	 * =sqrt(variance)
	 * =sqrt((sum of all squared differences from the mean)/n)
	 * -where n = # elements of arr
	 */
	public static double stdDev(int[] arr, double mean){
		
		return Math.sqrt(var(arr, mean));
	}// end stdDev()
	
	/*
	 * stdDev()
	 * -standard deviation
	 * = sqrt(variance)
	 */
	public static double stdDev(long[] arr, double mean){
		
		return Math.sqrt(var(arr, mean));
	}// end stdDev()
	
	/*
	 * coeffVar()
	 * -coefficient of variation (REQUIRED)
	 * = (SD/MEAN) *100
	 */
	public static double coeffVar(int[] list, double mean){
		
		return (stdDev(list, mean)/mean) * 100;
	}// end coeffVar()
	
	/*
	 * coeffVar()
	 * -coefficient of variation (REQUIRED)
	 * = (SD/MEAN) *100
	 */
	public static double coeffVar(long[] list, double mean){
		
		return (stdDev(list, mean)/mean) * 100;
	}// end coeffVar()

} // end BenchmarkSorts class

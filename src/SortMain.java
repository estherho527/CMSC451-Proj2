/*
 * Name: Esther Ho
 * CMSC 451 Project 2
 * Due: October 14, 2018
 * File: SortMain.java
 * 
 * Description:
 *    Main driver used to run benchmarking of Heap Sort, and prints out results
 * 
 * Required Files:
 * -ManualClassLoader.java
 * -HeapSort.java
 * -SortInterface.java
 * -BenchmarkSorts.java
 * -UnsortedException.java
 * 
 */
public class SortMain {

	public static void main(String[] args) throws UnsortedException{
		
		int[] sizes = {10, 20, 50, 100, 200, 500, 1000, 10000, 50000, 100000}; 
			// 10 different data set sizes, as required
		int numTrials = 50; // rubric: 50 trials per data set
		
		//Code from http://www.baeldung.com/java-jvm-warmup to properly warmup the JVM
		System.out.println("Performing JVM Warmup to ensure the test's accuracy.");
        long start = System.nanoTime();
        ManualClassLoader.load();
        long end = System.nanoTime();
        System.out.println("Warm Up time : " + (end - start) + " ns\n");
		
        // After warmup, run the actual benchmark 
		System.out.println("Starting benchmarking of heap sort.");
		BenchmarkSorts benchmarkRun = new BenchmarkSorts(sizes, numTrials);
		benchmarkRun.runSorts();
		benchmarkRun.displayReport();
		
	} // end main

}// end SortMain class

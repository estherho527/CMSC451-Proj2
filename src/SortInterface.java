/*
 * Name: Esther Ho
 * CMSC 451 Project 2
 * Due: October 14, 2018
 * File: SortInterface.java
 * 
 * Description:
 *    Required SortInterface as detailed by Project 1 rubric.  
 * 
 * 
 * Intended to be implemented by:
 * -HeapSort.java
 * 
 */
public interface SortInterface {

	int[] recursiveSort (int[] list) throws UnsortedException;
	int[] iterativeSort(int[] list) throws UnsortedException;
	int getCount();
	long getTime();
	
} // end SortInterface interface 
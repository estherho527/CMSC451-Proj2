/*
 * Name: Esther Ho
 * CMSC 451 Project 2
 * Due: October 14, 2018
 * File: UnsortedException.java
 * 
 * Description:
 *  	-required file for Project 1
 *  
 *  	- an UnsortedException is thrown when an array is not properly sorted
 * 	
 * 
 * 
 * Required Files:
 * -
 * 
 */
public class UnsortedException extends Exception {

	public UnsortedException() {
		super();
	}

	public UnsortedException(String message) {
		super(message);
	}

	public UnsortedException(Throwable cause) {
		super(cause);
	}

	public UnsortedException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnsortedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

} // end UnsortedException class

/*
 * Name: Esther Ho
 * CMSC 451 Project 2
 * Due: October 14, 2018
 * File: BenchmarkSorts.java
 * 
 * Description:
 *    Chosen sort for this project: Heap Sort
 *    
 *    -A HeapSort object keeps track of critical operations and also the time (in ns) 
 *    it takes for the sort to be completed.  Both iterative and recursive implementations
 *    are included
 *    
 *    -iterative heap sort critical operation: calls to swap()
 *    -recursive heap sort critical operation: calls to maxHeapify()
 * 
 * 
 * 
 * 
 * Required Files:
 * -SortInterface.java
 * -UnsortedException.java
 * 
 * Intended to be used with:
 * -SortMain.java
 * -BenchmarkSorts.java
 * -ManualClassLoader.java
 * 
 */
public class HeapSort implements SortInterface{

	private int count; // count of critical operation
					   // iterative: # swap calls
					   // recursive: # heapify calls
	private long time; // total time it takes for the sort to run
	
	// constructor
	public HeapSort() {

		count = 0;
		time = 0;
	} // end constructor

	/*
	 * iterativeSort()
	 * @see SortInterface#iterativeSort(int[])
	 * 
	 * -modified from 
	 * source: https://en.wikibooks.org/wiki/Algorithm_Implementation/Sorting/Heapsort#Java_2
	 * 
	 * - critical operation: swaps, i.e. call to swap()
	 */
	public int[] iterativeSort(int[] list) throws UnsortedException {
		
		long start = System.nanoTime();
		//System.out.println("\nStarting iterative heap sort");
		
		count = 0; // # of swaps made; i.e. calls to swap()
		//System.out.println("count reset:" + getCount());
		
		/* This method performs an in-place heapsort. Starting
		 * from the beginning of the array, the array is swapped
		 * into a binary max heap.  Then elements are removed
		 * from the heap, and added to the front of the sorted
		 * section of the array. */

		/* Insertion onto heap */
		for (int heapsize = 0; heapsize < list.length; heapsize++) {
			
			/* Step one in adding an element to the heap in the
			 * place that element at the end of the heap array-
			 * in this case, the element is already there. */
			
			int n = heapsize; // the index of the inserted int
			
			while (n > 0) { // until we reach the root of the heap
				
				int p = (n-1)/2; // the index of the parent of n
				
				if (list[n] > list[p]) { // child is larger than parent
					
					swap(list, n, p); // swap child with parent
					count++;
					n = p; // check parent
				}
				
				else // parent is larger than child
					break; // all is good in the heap
			}
		}

		/* Removal from heap */
		int heapsize = list.length;
		while (heapsize  > 0) {
			
			swap(list, 0, --heapsize); // swap root with the last heap element
			count++;
			
			int n = 0; // index of the element being moved down the tree
			
			while (true) {
				int left = (n*2)+1;
				
				if (left >= heapsize) // node has no left child
					break; // reached the bottom; heap is heapified
				
				int right = left+1;
				
				if (right >= heapsize) { // node has a left child, but no right child
					
					if (list[left] > list[n]){ // if left child is greater than node
						swap(list, left, n); // swap left child with node
						count++;
					}
					break; // heap is heapified
				}
				
				if (list[left] > list[n]) { // (left > n)
					
					if (list[left] > list[right]) { // (left > right) & (left > n)
						
						swap(list, left, n);
						count++;
						n = left; continue; // continue recursion on left child
					} 
					else { // (right > left > n)
						
						swap(list, right, n);
						count++;
						n = right; continue; // continue recursion on right child
					}
				} 
				
				else { // (n > left)
					
					if (list[right] > list[n]) { // (right > n > left)
						
						swap(list, right, n);
						count++;
						n = right; continue; // continue recursion on right child
					} 
					
					else { // (n > left) & (n > right)
						break; // node is greater than both children, so it's heapified
					}
				}
			}
		} // done sorting
		
		time = System.nanoTime() - start;

		
		return list;
	} // end iterativeSort()
	
	/*
	 * recursiveSort()
	 * @see SortInterface#recursiveSort(int[])
	 * - starts time now, to ensure accuracy
	 * 
	 * -modified from source: https://www.geeksforgeeks.org/heap-sort/
	 * 
	 * -critical operation: # of heapify calls
	 */
	public int[] recursiveSort(int[] list) throws UnsortedException {
		
		//START time
		long start = System.nanoTime();
		//System.out.println("\nStarting recursive heap sort");
		
		int size = list.length;
		count = 0; // # times maxHeapify() called
		//System.out.println("count reset:" + getCount());
		
		// build max heap
		buildMaxHeap(list, size);
 
        // One by one extract an element from heap
        for (int i=size-1; i>=0; i--)
        {
            // Move current root to end
            int temp = list[0];
            list[0] = list[i];
            list[i] = temp;
 
            // call max heapify on the reduced heap
            maxHeapify(list, i, 0);
        }
        
        // done sorting
		time = System.nanoTime() - start;
		
		return list;
	} // end recursiveSort()

	/*
	 * buildMaxHeap()
	 * -private inner function 
	 * -list : integer array to sort
	 * -n: num of elements in array
	 */
	private void buildMaxHeap(int[] list, int n){
		
		for (int i = n / 2 - 1; i >= 0; i--)
            maxHeapify(list, n, i);
		
	} // end private maxHeapify()
	
	/*
	 * maxHeapify()
	 * -private inner function 
	 * -list : integer array to sort
	 * -n: num of elements in array
	 */
	private void maxHeapify(int[] list, int n, int i){
		
		count++;
		int largest = i; // Initialize largest as root
        int l = 2*i + 1; // left = 2*i + 1
        int r = 2*i + 2; // right = 2*i + 2
 
        // If left child is larger than root
        if (l < n && list[l] > list[largest])
            largest = l;
 
        // If right child is larger than largest so far
        if (r < n && list[r] > list[largest])
            largest = r;
 
        // If largest is not root
        if (largest != i)
        {
            int swap = list[i];
            list[i] = list[largest];
            list[largest] = swap;
 
            // Recursively heapify the affected sub-tree
            maxHeapify(list, n, largest);
        }
	} // end private maxHeapify()
	
	/*
	 * getCount()
	 */
	public int getCount(){
		return count;
	}

	/*
	 * getTime()
	 */
	public long getTime() {
		return time;
	}
	
	/*
	 * swap()
	 * - pre-condition: x and y are valid indices
	 *    i.e : x < size && y < size
	 * - utility function
	 */
	private static void swap(int[] a, int x, int y){
		
		int tmp = a[x];
		a[x] = a[y];
		a[y] = tmp;

	} // end swap
	
	/*
	 *  isSorted()
	 *  -Throws an UnsortedeException if the array was not sorted properly.
	 *  -called from BenchmarkSorts in runSorts
	 *  
	 */
    protected void isSorted(int[] arr) throws UnsortedException{
        for(int i = 0; i < arr.length - 1; i++){
        	
            if(arr[i] > arr[i + 1])
                throw new UnsortedException("HEAP SORT UNSUCCESSFUL!");
            
        }
    } // end isSorted()
	
	/*
	 * printArr()
	 * -prints out array "{1 3 4}"
	 * -static utility method
	 */
	public static void printArr(int[] a){
		
		System.out.print("{");
		for (int i = 0; i < a.length; i++){
			
			System.out.print(a[i]);
			
			if(i < a.length -1)
				System.out.print(" ");
		}
		System.out.println("}");
	}
	
	/*
	 * main to test heap sort
	 */
	public static void main(String[] args) throws UnsortedException{
		
		HeapSort sort = new HeapSort();
		int[] original = {1, 10, 2, 4, 3};
		int[] test = original;
		
		// print original array
		System.out.print("original");
		HeapSort.printArr(original);
		
		/*
		// TEST for swap
		// print
		System.out.print("before: {");
		for (int i = 0; i < original.length; i++){
			System.out.print(original[i] + " ");
		}
		System.out.println("}");
		
		swap(original, 1, 2);
		
		// print
		System.out.print("after: {");
		for (int i = 0; i < original.length; i++){
			System.out.print(original[i] + " ");
		}
		System.out.println("}");
		*/
		
		// TEST for iterative sort
		test = sort.iterativeSort(original);
		
		System.out.print("\nAfter iterative:");
		HeapSort.printArr(test);
		System.out.println("swap count:" + sort.getCount());
		System.out.println("time:" + sort.getTime());
		
		// TEST for recursive sort
		test = sort.recursiveSort(original);
		
		System.out.print("\nAfter recursive:");
		HeapSort.printArr(test);
		System.out.println("heapify count:" + sort.getCount());
		System.out.println("time:" + sort.getTime());
		
	} // end main

} // end HeapSort class

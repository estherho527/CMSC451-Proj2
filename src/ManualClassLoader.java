/*
 * Name: Esther Ho
 * CMSC 451 Project 2
 * Due: October 14, 2018
 * File: ManualClassLoader.java
 * Purpose:  used to warm up the JVM before performing the benchmark.
 * 
 * Code Source: http://www.baeldung.com/java-jvm-warmup
 * 
 * Intended to be used with:
 * -SortMain.java
 * -BenchmarkSorts.java
 * -HeapSort.java
 */

public class ManualClassLoader {
    protected static void load() {
        for (int i = 0; i < 100000; i++) {
            Dummy dummy = new Dummy();
            dummy.m();
        }
    }
}

class Dummy {
    public void m() {}
}
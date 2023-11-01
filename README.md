# 📊 Benchmarking Project (Computer Organization Laboratory)

This project is a faculty project implemented in Java that includes several types of benchmarks. A benchmark is a standardized test used to evaluate the performance of a system, such as a CPU or a computer program. Benchmarks are important because they provide a quantitative measure of performance, which can be used to compare different systems or to identify performance bottlenecks.

## 📚 Project Framework

The project is implemented using a benchmarking framework that includes several packages:

### Logging

The logging package is responsible for logging the results of the benchmarks. 

### Timing

The timing package provides a way to measure the execution time of a particular piece of code. It includes utilities for converting between different time units.

### Testbench

The testbench package provides a way to run a test for a particular benchmark.

### Bench

The bench package provides the algorithms for benchmarks.

## ⚙️ Types of Benchmarks

The following types of tests are performed in this project:

## 💻 *CPU Testing*

- ### Generating the PI digits with Gauss Legendre

This benchmark measures the performance of the CPU in terms of floating point arithmetic and computational efficiency.

- ### Fixed Point Arithmetic

The Fixed Point Arithmetic benchmark measures the performance of integer arithmetic, arrays, and branching. It calculates the number of millions of operations per second (MOPS) that the CPU can perform.

- ### Recursion and Caching Testing 

The Recursion and Caching Testing benchmark uses the loop unrolling technique to improve performance.

- ### Threaded Roots using Newton's Formula

The Threaded Roots benchmark measures the performance of threaded programming. It uses Newton's formula to calculate the square roots of a number.

- ### Dynamic Workload with Threads

The Dynamic Workload benchmark measures the performance of dynamic workload using threads. It uses brute force attack to decipher a given hash.

## 💾 *HDD Testing*
 
- ### HDD Writing Speed 

The HDD Writing Speed Benchmark provides two variants for measuring the writing speed of a Hard Disk Drive (HDD): one with a fixed file size and another with a fixed buffer size.

- ### HDD Random Access 

The HDD Random Access benchmark provides methods for performing random access operations on a hard disk drive (HDD) file. It allows reading and writing of fixed-size data or performing operations for a fixed time.

## 🧠 *RAM Testing*

- ### Virtual Memory

The Virtual Memory Benchmark maps a large file into RAM, thereby triggering the virtual memory mechanism. It performs read and write operations on the file, allowing measurement of access speeds.

## 💡 Conclusion

This project offers a comprehensive benchmarking framework designed to evaluate the performance of various algorithms and data structures across different components, including the CPU, RAM, and HDD.
The benchmarks enable users to analyze and compare the performance under various scenarios, providing valuable insights into their efficiency and suitability for specific applications.

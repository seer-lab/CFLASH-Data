# <p align="center">ConMAn</p>

<br>The ConMAn (Concurrency Mutation Analysis) operators were developed for mutating concurrent source code written in Java (J2SE 5.0) . There are 24 ConMAn operators, each of which was based on real concurrency bug patterns. Each operator is implemented in [TXL](http://www.txl.ca) - a source code transformation language. 



The ConMAn operators can be  used in the comparison of different test suites and testing strategies for concurrent Java as well as different fault detection techniques for concurrency. Although ConMAn has been used previously as a comparative metric for different fault detection tools we believe that these operators can also serve a role similar to method and class level mutation operators as both comparative metrics and coverage criteria. 



## Usage

Each ConMAn Operator can be run separately using the following command:

```bash
$ txl <infilename> <operator>.Txl - -outfile <outfilename> -outdir <outputpath> 
```
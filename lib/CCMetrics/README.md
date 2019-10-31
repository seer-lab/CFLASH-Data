# CCMetrics -- Concurrency Code Metrics

**Jeremy S. Bradbury, Oct. 27, 2006**

**Copyright 2006 J.S. Bradbury**

**License: FreeBSD**



This program generates the following static metrics for concurrent Java 8 programs:

	- \# of classes
	- \# of methods
	- \# of statements
		- \# of critical regions
		- \# of sychronized blocks
		- \# of synchronized methods
		- \# of statements inside synchronized methods
		- \# of statements inside synchronized blocks
	- \# of semaphores
	- \# of locks
	- \# of latches
	- \# of cyclicbarriers
	- \# of exchangers

<br><br>

---

[Note: Program based on ccount.Txl program written by J. Cordy (1993)]